package com.uchal.service;

import org.springframework.stereotype.Service;

import com.uchal.entity.FactoryRateEntity;
import com.uchal.repository.FactoryRateRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FactoryRateService {

    private final FactoryRateRepository factoryRateRepository;

    @Autowired
    public FactoryRateService(FactoryRateRepository factoryRateRepository) {
        this.factoryRateRepository = factoryRateRepository;
    }

    public List<FactoryRateEntity> getAllFactoryRates() {
        return factoryRateRepository.findAll();
    }

    public Optional<FactoryRateEntity> getFactoryRateById(int rateId) {
        return factoryRateRepository.findById(rateId);
    }

    public FactoryRateEntity createFactoryRate(FactoryRateEntity factoryRate) {
        return factoryRateRepository.save(factoryRate);
    }

    public FactoryRateEntity updateFactoryRate(int rateId, FactoryRateEntity updatedFactoryRate) {
        Optional<FactoryRateEntity> existingFactoryRateOptional = factoryRateRepository.findById(rateId);

        if (existingFactoryRateOptional.isPresent()) {
            FactoryRateEntity existingFactoryRate = existingFactoryRateOptional.get();
            existingFactoryRate.setRate(updatedFactoryRate.getRate());
            existingFactoryRate.setFactoryName(updatedFactoryRate.getFactoryName());

            return factoryRateRepository.save(existingFactoryRate);
        }

        return null; // or throw an exception indicating that the factory rate with the given ID was not found
    }

    public void deleteFactoryRate(int rateId) {
        factoryRateRepository.deleteById(rateId);
    }
}

