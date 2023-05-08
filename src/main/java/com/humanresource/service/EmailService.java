package com.humanresource.service;

import org.springframework.mail.SimpleMailMessage;

import com.humanresource.domain.Customer;
import com.humanresource.domain.OrderJob;

public interface EmailService {

	void sendOrderConfirmationEmail(OrderJob obj);
	
	void sendEmail(SimpleMailMessage msg);
	
	void sendNewPasswordEmail(Customer customer, String newPass);
}
