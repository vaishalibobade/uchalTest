package com.uchal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.uchal.entity.BlockRequestEntity;
import com.uchal.entity.FactoryRateEntity;
import com.uchal.model.ApiResponse;
import com.uchal.service.FactoryRateService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/factoryRates")
public class FactoryRateController {

    private final FactoryRateService factoryRateService;

    @Autowired
    public FactoryRateController(FactoryRateService factoryRateService) {
        this.factoryRateService = factoryRateService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<FactoryRateEntity>>> getAllFactoryRates() {
        List<FactoryRateEntity> factoryRates = factoryRateService.getAllFactoryRates();
        return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponse<>(HttpStatus.OK, "Data Found", factoryRates, null));    }

//    @GetMapping("/{rateId}")
//    public ResponseEntity<FactoryRateEntity> getFactoryRateById(@PathVariable int rateId) {
//        Optional<FactoryRateEntity> factoryRate = factoryRateService.getFactoryRateById(rateId);
//
//        return factoryRate.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
//                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }
//
//    @PostMapping
//    public ResponseEntity<FactoryRateEntity> createFactoryRate(@RequestBody FactoryRateEntity factoryRate) {
//        FactoryRateEntity createdFactoryRate = factoryRateService.createFactoryRate(factoryRate);
//        return new ResponseEntity<>(createdFactoryRate, HttpStatus.CREATED);
//    }
//
//    @PutMapping("/{rateId}")
//    public ResponseEntity<FactoryRateEntity> updateFactoryRate(
//            @PathVariable int rateId,
//            @RequestBody FactoryRateEntity updatedFactoryRate
//    ) {
//        FactoryRateEntity updatedRate = factoryRateService.updateFactoryRate(rateId, updatedFactoryRate);
//
//        return Optional.ofNullable(updatedRate)
//                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
//                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }
//
//    @DeleteMapping("/{rateId}")
//    public ResponseEntity<Void> deleteFactoryRate(@PathVariable int rateId) {
//        factoryRateService.deleteFactoryRate(rateId);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
}

