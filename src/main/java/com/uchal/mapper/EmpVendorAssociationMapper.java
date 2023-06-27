package com.uchal.mapper;

import com.uchal.entity.EmpVendorAssociation;
import com.uchal.model.EmpVendorAssociationModel;

public class EmpVendorAssociationMapper {
	
	
	
	public EmpVendorAssociationModel mapToModel( EmpVendorAssociation empVendorAssociation)
	
	{
		EmpVendorAssociationModel empVendorAssociationModel=new EmpVendorAssociationModel();
		
		empVendorAssociationModel.setAmountPaid(empVendorAssociation.getAmount_paid());
		empVendorAssociationModel.setDurationDays(empVendorAssociation.getDuration_days());
		empVendorAssociationModel.setEndDate(empVendorAssociation.getEnd_date());
//		empVendorAssociationModel.setPaymentstatus(empVendorAssociation.getPaymentStatus());
		empVendorAssociationModel.setStartDate(empVendorAssociation.getStart_date());
		empVendorAssociationModel.setUserId(empVendorAssociation.getEmployeeId());
//		empVendorAssociationModel.setUserStatus(empVendorAssociation.getUserStatus());
		
		return empVendorAssociationModel;
		
		
		
	}
	
	public EmpVendorAssociation  mapToEntity(EmpVendorAssociationModel empVendorAssociationModel)
	{
		EmpVendorAssociation empVendorAssociation =new EmpVendorAssociation();
		
		empVendorAssociation.setAmount_paid(empVendorAssociationModel.getAmountPaid());
		empVendorAssociation.setDuration_days(empVendorAssociationModel.getDurationDays());
		empVendorAssociation.setEnd_date(empVendorAssociationModel.getEndDate());
//		empVendorAssociation.setPaymentstatus(empVendorAssociationModel.getPaymentStatus());
		empVendorAssociation.setStart_date(empVendorAssociationModel.getStartDate());
		empVendorAssociation.setEmployeeId(empVendorAssociationModel.getUserId());
//		empVendorAssociation.setUserStatus(empVendorAssociationModel.getUserStatus());
		
		return empVendorAssociation;
	}

}
