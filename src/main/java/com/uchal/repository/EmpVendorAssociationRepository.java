package com.uchal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uchal.entity.EmpVendorAssociation;
import com.uchal.entity.LoginDetails;
import com.uchal.service.EmpVendorAssociationService;

public interface EmpVendorAssociationRepository  extends JpaRepository<EmpVendorAssociation, Integer> {


}
