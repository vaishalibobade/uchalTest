package com.uchal.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uchal.entity.EmpVendorAssociation;
import com.uchal.entity.MasterPaymentStatus;
import com.uchal.entity.MasterUserStatus;
import com.uchal.model.EmpVendorAssociationModel;
import com.uchal.service.MasterPaymentStatusService;
import com.uchal.service.MasterUserStatusService;

@Component
public class EmpVendorAssociationMapper {
	private final MasterPaymentStatusService masterPaymentStatusService;
	private final MasterUserStatusService masterUserStatusService;

	@Autowired
	public EmpVendorAssociationMapper(MasterPaymentStatusService masterPaymentStatusService,
			MasterUserStatusService masterUserStatusService) {
		this.masterPaymentStatusService = masterPaymentStatusService;
		this.masterUserStatusService = masterUserStatusService;
	}

	public EmpVendorAssociationModel mapToModel(EmpVendorAssociation empVendorAssociation)

	{
		EmpVendorAssociationModel empVendorAssociationModel = new EmpVendorAssociationModel();
		empVendorAssociationModel.setId(empVendorAssociation.getId());
		empVendorAssociationModel.setAmountPaid(empVendorAssociation.getAmountPaid());
		empVendorAssociationModel.setDurationDays(empVendorAssociation.getDuration_days());
		empVendorAssociationModel.setEndDate(empVendorAssociation.getEnd_date());
		empVendorAssociationModel.setPaymentstatus(empVendorAssociation.getPaymentStatus().getId());
		empVendorAssociationModel.setStartDate(empVendorAssociation.getStart_date());
		empVendorAssociationModel.setUserId(empVendorAssociation.getEmployeeId());
		empVendorAssociationModel.setUserStatus(empVendorAssociation.getUserStatus().getId());
		empVendorAssociationModel.setVendorId(empVendorAssociation.getVendorId());

		return empVendorAssociationModel;

	}

	public EmpVendorAssociation mapToEntity(EmpVendorAssociationModel empVendorAssociationModel) {
		EmpVendorAssociation empVendorAssociation = new EmpVendorAssociation();
		empVendorAssociation.setId(empVendorAssociationModel.getId());
		empVendorAssociation.setAmount_paid(empVendorAssociationModel.getAmountPaid());
		empVendorAssociation.setDuration_days(empVendorAssociationModel.getDurationDays());
		empVendorAssociation.setEnd_date(empVendorAssociationModel.getEndDate());
		MasterPaymentStatus paymentstatus = masterPaymentStatusService
				.getPaymentStatusById(empVendorAssociationModel.getPaymentstatus());
//		paymentstatus.setId(empVendorAssociationModel.getPaymentstatus());
		empVendorAssociation.setPaymentStatus(paymentstatus);
		empVendorAssociation.setStart_date(empVendorAssociationModel.getStartDate());
		empVendorAssociation.setEmployeeId(empVendorAssociationModel.getUserId());
		MasterUserStatus userStatus = masterUserStatusService
				.getMasterUserStatusById(empVendorAssociationModel.getUserStatus());
//		userStatus.setId(empVendorAssociationModel.getUserStatus());
		empVendorAssociation.setUserStatus(userStatus);
		empVendorAssociation.setVendorId(empVendorAssociationModel.getVendorId());
		System.out.println(empVendorAssociation);

		return empVendorAssociation;
	}

}
