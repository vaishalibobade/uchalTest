package com.uchal.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uchal.controllers.LoginController;
import com.uchal.entity.MasterPaymentStatus;
import com.uchal.entity.MasterUserStatus;
import com.uchal.repository.MasterUserStatusRepository;

@Service
 public class MasterUserStatusService {
	
	
	private static final Logger logger = LogManager.getLogger(LoginController.class);
private final MasterUserStatusRepository  masterUserStatusRepository;


@Autowired
public MasterUserStatusService(MasterUserStatusRepository  masterUserStatusRepository) {
    this.masterUserStatusRepository = masterUserStatusRepository;
}


public List<MasterUserStatus> getAllMasterUserStatuses() {
    return masterUserStatusRepository.findAll();
}
public MasterUserStatus getMasterUserStatusById(int id) {
    return masterUserStatusRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid user status ID"));
}

public MasterUserStatus createMasterUserStatus(MasterUserStatus masterUserStatus) {
    return masterUserStatusRepository.save(masterUserStatus);
}

public void deleteMasterUserStatus(int id) {
    masterUserStatusRepository.deleteById(id);
}







}
