package com.humanresource.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import com.humanresource.domain.Customer;
import com.humanresource.service.validation.CustomerUpdate;


@CustomerUpdate
public class CustomerDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	
	@NotBlank(message = "Name is mandatory")
	@Min(5)
	@Max(120)
	//@Length(min=5, max=120, message="O tamanho deve ser entre 5 e 120 caracteres")
	private String name;
	
	@NotBlank(message = "Email is mandatory")
	@Email
	private String email;
	
	public CustomerDTO() {
	}

	public CustomerDTO(Customer obj) {
		id = obj.getId();
		name = obj.getName();
		email = obj.getEmail();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
