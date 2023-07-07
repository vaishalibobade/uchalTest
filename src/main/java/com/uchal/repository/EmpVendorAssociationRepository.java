package com.uchal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uchal.entity.EmpVendorAssociation;
@Repository
public interface EmpVendorAssociationRepository  extends JpaRepository<EmpVendorAssociation, Integer> {

	
	public List<EmpVendorAssociation> findAllByVendorId(int vendorId);

}
