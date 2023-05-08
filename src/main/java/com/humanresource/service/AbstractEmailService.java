package com.humanresource.service;


import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.humanresource.domain.Customer;
import com.humanresource.domain.OrderJob;

public abstract class AbstractEmailService implements EmailService {
	
	@Value("${default.sender}")
	private String sender;
	
	@Override
	public void sendOrderConfirmationEmail(OrderJob obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(OrderJob obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCustomer().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Pedido confirmado! Código: " + obj.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.toString());
		return sm;
	}
	
	@Override
	public void sendNewPasswordEmail(Customer customer, String newPass) {
		SimpleMailMessage sm = prepareNewPasswordEmail(customer, newPass);
		sendEmail(sm);
	}
	
	protected SimpleMailMessage prepareNewPasswordEmail(Customer cliente, String newPass) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(cliente.getEmail());
		sm.setFrom(sender);
		sm.setSubject("Solicitação de nova senha");
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText("Nova senha: " + newPass);
		return sm;
	}
}

