package com.humanresource.service;


import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.humanresource.domain.City;
import com.humanresource.domain.Customer;
import com.humanresource.domain.Address;
import com.humanresource.domain.enums.Role;
import com.humanresource.domain.enums.TypeCustomer;
import com.humanresource.dto.CustomerDTO;
import com.humanresource.dto.CustomerNewDTO;
import com.humanresource.repositories.CustomerRepository;
import com.humanresource.repositories.EducationRepository;
import com.humanresource.repositories.ExperienceRepository;
import com.humanresource.repositories.AddressRepository;
import com.humanresource.security.UserSS;
import com.humanresource.service.exceptions.AuthorizationException;
import com.humanresource.service.exceptions.DataIntegrityException;
import com.humanresource.service.exceptions.ObjectNotFoundException;
@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository repo;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private EducationRepository educationRepository;
	
	@Autowired
	private ExperienceRepository experienceRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private S3Service s3Service;
	
	@Autowired
	private ImageService imageService;
		
	@Value("${img.prefix.client.profile}")
	private String prefix;
	
	@Value("${img.profile.size}")
	private Integer size;
	
	public Customer find(Integer id) {
		
		UserSS user = UserService.authenticated();
		if (user==null || !user.hasRole(Role.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		
		Optional<Customer> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Customer.class.getName()));
	}
	
	@Transactional
	public Customer insert(Customer obj) {
		obj.setId(null);
		obj = repo.save(obj);
		experienceRepository.saveAll(obj.getExperiencies());
		educationRepository.saveAll(obj.getEducations());
		addressRepository.saveAll(obj.getAddresses());
		return obj;
	}
	
	public Customer update(Customer obj) {
		Customer newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionados");
		}
	}
	
	public List<Customer> findAll() {
		return repo.findAll();
	}
	
	public Customer findByEmail(String email) {
		UserSS user = UserService.authenticated();
		if (user == null || !user.hasRole(Role.ADMIN) && !email.equals(user.getUsername())) {
			throw new AuthorizationException("Acesso negado");
		}
	
		Customer obj = repo.findByEmail(email);
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + user.getId() + ", Tipo: " + Customer.class.getName());
		}
		return obj;
	}
	
	public Page<Customer> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Customer fromDTO(CustomerDTO objDto) {
		return new Customer(objDto.getId(), objDto.getName(), objDto.getEmail(), null, null, null);
	}
	
	public Customer fromDTO(CustomerNewDTO objDto) {
		Customer cli = new Customer(null, objDto.getName(), objDto.getEmail(), objDto.getCpfOuCnpj(), TypeCustomer.toEnum(objDto.getType()), pe.encode(objDto.getPassword()));
		City cid = new City(objDto.getCityId(), null, null);
		Address end = new Address(null, objDto.getPlace(), objDto.getNumber(), objDto.getComplement(), objDto.getDistrict(), objDto.getZipCode(), cli, cid);
		cli.getAddresses().add(end);
		cli.getPhones().add(objDto.getPhone1());
		if (objDto.getPhone2()!=null) {
			cli.getPhones().add(objDto.getPhone2());
		}
		if (objDto.getphone3()!=null) {
			cli.getPhones().add(objDto.getphone3());
		}
		return cli;
	}
	
	private void updateData(Customer newObj, Customer obj) {
		newObj.setName(obj.getName());
		newObj.setEmail(obj.getEmail());
	}
	
	public URI uploadProfilePicture(MultipartFile multipartFile) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		
		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
		jpgImage = imageService.cropSquare(jpgImage);
		jpgImage = imageService.resize(jpgImage, size);
		
		String fileName = prefix + user.getId() + ".jpg";
		
		return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
	}
}
