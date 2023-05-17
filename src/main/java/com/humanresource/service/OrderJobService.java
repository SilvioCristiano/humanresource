package com.humanresource.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.humanresource.domain.Customer;
import com.humanresource.domain.ItemOrder;
import com.humanresource.domain.PaymentComBoleto;
import com.humanresource.domain.OrderJob;
import com.humanresource.domain.enums.Role;
import com.humanresource.domain.enums.StatePayment;
import com.humanresource.repositories.ItemOrderRepository;
import com.humanresource.repositories.PaymentRepository;
import com.humanresource.repositories.OrderJobRepository;
import com.humanresource.security.UserSS;
import com.humanresource.service.exceptions.AuthorizationException;
import com.humanresource.service.exceptions.ObjectNotFoundException;

@Service
public class OrderJobService {
	
	@Autowired
	private OrderJobRepository repo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private ItemOrderRepository itemOrderRepository;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private EmailService emailService;
	
	public OrderJob find(Integer id) {
		Optional<OrderJob> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + OrderJob.class.getName()));
	}
	

	
	public OrderJob insert(OrderJob obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCustomer(customerService.find(obj.getCustomer().getId()));
		obj.getPayment().setState(StatePayment.PENDING);
		obj.getPayment().setOrder(obj);
		if (obj.getPayment() instanceof PaymentComBoleto) {
			PaymentComBoleto pagto = (PaymentComBoleto) obj.getPayment();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repo.save(obj);
		paymentRepository.save(obj.getPayment());
		for (ItemOrder ip : obj.getItens()) {
			ip.setDiscount(0.0);
			ip.setProduct(productService.find(ip.getProduct().getId()));
			ip.setPrice(ip.getProduct().getPrice());
			ip.setOrder(obj);
		}
		itemOrderRepository.saveAll(obj.getItens());
		emailService.sendOrderConfirmationEmail(obj);
		return obj;
	}
	
	public Page<OrderJob> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Customer customer =  customerService.find(user.getId());
		return repo.findByCustomer(customer, pageRequest);
	}
}
