package com.uchal.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uchal.entity.RegistrationPayment;
import com.uchal.model.RegistrationPaymentModel;
import com.uchal.repository.RegistrationPaymentRepository;

import java.util.List;

@Service
public class RegistrationPaymentService {

    private RegistrationPaymentRepository repository;

    @Autowired
    public RegistrationPaymentService(RegistrationPaymentRepository repository) {
        this.repository = repository;
    }

    public RegistrationPayment addPayment(RegistrationPaymentModel payment) {
    	
        // Add business logic if needed
    	RegistrationPayment paymententity=new RegistrationPayment();
    	paymententity.setPaymentAmount(payment.getPaymentAmount());
    	paymententity.setPaymentStatus(payment.getPaymentStatus());
    	paymententity.setUserId(payment.getUserId());
    	RegistrationPayment paid=repository.save(paymententity);
    	return paid;
    }

    public List<RegistrationPayment> getAllPayments() {
        // Add business logic if needed
        return repository.findAll();
    }

    public RegistrationPayment getPaymentById(int paymentId) {
        // Add business logic if needed
        return repository.findById(paymentId).orElse(null);
    }

    public void updatePayment(RegistrationPayment payment) {
        // Add business logic if needed
        repository.save(payment);
    }

    public void deletePayment(int paymentId) {
        // Add business logic if needed
        repository.deleteById(paymentId);
    }
}
