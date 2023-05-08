package com.humanresource.domain;


import java.util.Date;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.humanresource.domain.enums.StatePayment;


@Entity
@JsonTypeName("pagamentoComBoleto")
public class PaymentComBoleto extends Payment {
	private static final long serialVersionUID = 1L;

	@JsonFormat(pattern="dd/MM/yyyy")
	private Date dataVencimento;

	@JsonFormat(pattern="dd/MM/yyyy")
	private Date dataPagamento;

	public PaymentComBoleto() {
	}

	public PaymentComBoleto(Integer id, StatePayment state, OrderJob orderjob, Date dataVencimento, Date dataPagamento) {
		super(id, state, orderjob);
		this.dataPagamento = dataPagamento;
		this.dataVencimento = dataVencimento;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}	
	
}
