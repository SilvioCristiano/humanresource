package com.humanresource.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;



public class EmailDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "Email is mandatory")
	@Email
	private String email;
	
	public EmailDTO() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
