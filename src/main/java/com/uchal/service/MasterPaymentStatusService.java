package com.uchal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uchal.entity.MasterPaymentStatus;
import com.uchal.repository.MasterPaymentStatusRepository;

import java.util.List;

@Service
public class MasterPaymentStatusService {

    private final MasterPaymentStatusRepository paymentStatusRepository;

    @Autowired
    public MasterPaymentStatusService(MasterPaymentStatusRepository paymentStatusRepository) {
        this.paymentStatusRepository = paymentStatusRepository;
    }

    public MasterPaymentStatus savePaymentStatus(MasterPaymentStatus paymentStatus) {
        return paymentStatusRepository.save(paymentStatus);
    }

    public List<MasterPaymentStatus> getAllPaymentStatuses() {
        return paymentStatusRepository.findAll();
    }

    public MasterPaymentStatus getPaymentStatusById(int id) {
    	System.out.println(id);
        return paymentStatusRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid payment status ID"));
    }

    public void deletePaymentStatus(int id) {
        paymentStatusRepository.deleteById(id);
    }
}
