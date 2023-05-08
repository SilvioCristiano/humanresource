package com.humanresource.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;


import com.humanresource.service.validation.CustomerInsert;



@CustomerInsert
public class CustomerNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	@NotBlank(message = "Name is mandatory")
	@Min(5)
	@Max(120)
	private String name;

	@NotBlank(message = "Email is mandatory")
	@Email
	private String email;

	@NotEmpty(message="Preenchimento obrigatório")
	private String cpfOuCnpj;

	private Integer type;
	
	@NotEmpty(message="Preenchimento obrigatório")
	private String password;
	
	@NotEmpty(message="Preenchimento obrigatório")
	private String place;

	@NotEmpty(message="Preenchimento obrigatório")
	private String number;

	private String complement;

	private String district;

	@NotEmpty(message="Preenchimento obrigatório")
	private String zipcode;
	
	@NotEmpty(message="Preenchimento obrigatório")
	private String phone1;

	private String phone2;
	
	private String phone3;

	private Integer cityId;
	
	public CustomerNewDTO() {
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

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

	public Integer getType() {
		return type;
	}

	public void setTipo(Integer type) {
		this.type = type;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getZipCode() {
		return zipcode;
	}

	public void setZipCode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getphone3() {
		return phone3;
	}

	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}
