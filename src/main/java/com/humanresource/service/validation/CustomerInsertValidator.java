package com.humanresource.service.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.humanresource.domain.Customer;
import com.humanresource.domain.enums.TypeCustomer;
import com.humanresource.dto.CustomerNewDTO;
import com.humanresource.repositories.CustomerRepository;
import com.humanresource.resources.exception.FieldMessage;
import com.humanresource.service.validation.utils.BR;


public class CustomerInsertValidator implements ConstraintValidator<CustomerInsert, CustomerNewDTO> {


	@Autowired
	private CustomerRepository repo;
	
	@Override
	public void initialize(CustomerInsert ann) {
	}

	@Override
	public boolean isValid(CustomerNewDTO objDto, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();
		
		if (objDto.getType().equals(TypeCustomer.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		}

		if (objDto.getType().equals(TypeCustomer.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
		}

		Customer aux = repo.findByEmail(objDto.getEmail());
		if (aux != null) {
			list.add(new FieldMessage("email", "Email já existente"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}