package com.humanresource.domain;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.humanresource.domain.enums.StatePayment;

@Entity
@JsonTypeName("pagamentoComCartao")
public class PaymentComCartao extends Payment {
	private static final long serialVersionUID = 1L;

	private Integer numeroDeParcelas;
	
	public PaymentComCartao() {
	}

	public PaymentComCartao(Integer id, StatePayment state, OrderJob orderjob, Integer numeroDeParcelas) {
		super(id, state, orderjob);
		this.numeroDeParcelas = numeroDeParcelas;
	}

	public Integer getNumeroDeParcelas() {
		return numeroDeParcelas;
	}

	public void setNumeroDeParcelas(Integer numeroDeParcelas) {
		this.numeroDeParcelas = numeroDeParcelas;
	}
	
	
		
}