package com.ordermanagement.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.ordermanagement.model.Order;

@Component
public class EmailUtil {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOrderConfirmation(Order order) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("customer@example.com"); // Replace with actual customer email
        message.setSubject("Order Confirmation");
        message.setText("Your order has been placed successfully. Order ID: " + order.getId());
        mailSender.send(message);
    }
}
