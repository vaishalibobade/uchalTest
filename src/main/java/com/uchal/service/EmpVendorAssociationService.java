package com.uchal.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uchal.controllers.LoginController;
import com.uchal.entity.EmpVendorAssociation;
import com.uchal.mapper.EmpVendorAssociationMapper;
import com.uchal.model.ApiException;
import com.uchal.model.EmpVendorAssociationModel;
import com.uchal.model.PaymentDetailsModel;
import com.uchal.repository.EmpVendorAssociationRepository;

@Service

public class EmpVendorAssociationService {
	private final EmpVendorAssociationRepository empVendorAssociationRepository;
	private final EmpVendorAssociationMapper empVendorAssociationMapper;
	private final UserDetailsService userDetailsService;
	private static final Logger logger = LogManager.getLogger(EmpVendorAssociationService.class);

	@Autowired
	public EmpVendorAssociationService(UserDetailsService userDetailsService,
			EmpVendorAssociationRepository empVendorAssociationRepository,
			EmpVendorAssociationMapper empVendorAssociationMapper) {
		this.empVendorAssociationRepository = empVendorAssociationRepository;
		this.empVendorAssociationMapper = empVendorAssociationMapper;
		this.userDetailsService = userDetailsService;
	}

	public EmpVendorAssociation getEmpVendorAssociationById(int id) {
		return empVendorAssociationRepository.findById(id).orElse(null);
	}

	public List<EmpVendorAssociation> getAllEmpVendorAssociations() {
		return empVendorAssociationRepository.findAll();
	}

	public void deleteEmpVendorAssociation(int id) {
		empVendorAssociationRepository.deleteById(id);
	}

	public EmpVendorAssociation saveEmpVendorAssociationDetails(EmpVendorAssociation empVendorAssociationDetails) {
		return empVendorAssociationRepository.save(empVendorAssociationDetails);

	}

	public boolean checkPaymentValidation(int employeeId, int vendorId) {
		String payeeType = userDetailsService.getUserDetailsById(vendorId).getUserType();
		String employeeType = userDetailsService.getUserDetailsById(employeeId).getUserType();
		System.out.println(payeeType);
		System.out.println(employeeType);
		if (payeeType.equals("A") && employeeType.equals("V"))
			return true;
		if (payeeType.equals("V") && employeeType.equals("S"))
			return true;
		if (payeeType.equals("V") && employeeType.equals("E"))
			return true;
		if (payeeType.equals("S") && employeeType.equals("E"))
			return true;

		return false;

	}

	public EmpVendorAssociationModel savePaymentDetails(EmpVendorAssociationModel empVendorAssociationModel) {
//	    	EmpVendorAssociationMapper empVendorAssociationMapper=new EmpVendorAssociationMapper();
		if (!checkPaymentValidation(empVendorAssociationModel.getUserId(), empVendorAssociationModel.getVendorId()))
		{
			System.out.println(empVendorAssociationModel.getUserId());
			System.out.println(empVendorAssociationModel.getVendorId());
			throw new ApiException(
					"Admin can make payment to Vendor ,Vendor can make payment to Subvendor/Employee , Subvendor can make payment to Employee",
					404);
		}
		EmpVendorAssociation empVendorAssociation = empVendorAssociationRepository
				.save(empVendorAssociationMapper.mapToEntity(empVendorAssociationModel));

//	    	
		if (empVendorAssociation == null) {
			throw new ApiException("Error while saving Details", 404);
		}
		return empVendorAssociationMapper.mapToModel(empVendorAssociation);

	}

	public List<PaymentDetailsModel> getPaymentDetailsByEmployeeId(int employeeId) {
//		 return userDetailsService.getAllEmployeeDetailsWithAssociation(loggedId);
		List<Object[]> result = userDetailsService.getAllEmployeeDetailsWithAssociation(employeeId);
		List<PaymentDetailsModel> models = new ArrayList<>();
		for (Object[] objects : result) {
//		     UserDetails userDetails = (UserDetails) objects[0];
//		     EmpVendorAssociation empVendorAssociation = (EmpVendorAssociation) objects[1];
			PaymentDetailsModel records = new PaymentDetailsModel();
			records.setPaymentDetails((EmpVendorAssociation) objects[0]);

			records.setFirstName((String) objects[1]+" "+(String) objects[2]+" "+(String) objects[3]);
//			records.setLastName((String) objects[2]);
//			records.setMiddleName((String) objects[3]);
			records.setMobileNumber((Long) objects[4]);
			records.setUserType((String) objects[5]);
			records.setUserStatus((String)objects[6]);
			models.add(records);
		}
		return models;

	}

	public long getVendorCount(int id) {
		return empVendorAssociationRepository.countByID(id);

	}

	public long getEmployeeCount(int vendorId) {
		return empVendorAssociationRepository.getEmployeeCountByVendorId(vendorId);

	}

	public List<Object[]> getStatusCount(int id) {
		List<Object[]> count = empVendorAssociationRepository.countByStatus(id);
		if (count == null)
			throw new ApiException("No record found !!", 404);
		return count;

	}

	public double getTaskCompletionPercentage(int employeeId) {
		Long completedCount = empVendorAssociationRepository.getCompletedStatusCountByEmployeeId(employeeId);
		Long totalCount = empVendorAssociationRepository.getTotalStatusCountByEmployeeId(employeeId);
		System.out.println(completedCount);
		System.out.println(totalCount);
		double percentage = (completedCount.doubleValue() / totalCount.doubleValue()) * 100;
		return percentage;

	}

	public double getTaskCompletionPercentagefrAllEmployee() {
		Long completedCount = empVendorAssociationRepository.getAllCompletedStatusCount();
		Long totalCount = empVendorAssociationRepository.getTotalStatusCount();

		double percentage = (completedCount.doubleValue() / totalCount.doubleValue()) * 100;
		return percentage;

	}

	public List<Object[]> getStatusCountbyVendor(int vendorId) {
		List<Object[]> count = empVendorAssociationRepository.getEmployeeStatusCountByVendorId(vendorId);
		if (count == null)
			throw new ApiException("No record found !!", 404);

		return count;

	}

	public List<Object[]> getStatusCountbyAllVendor() {
		List<Object[]> count = empVendorAssociationRepository.getEmployeeStatusCountAllVendor();
		if (count == null)
			throw new ApiException("No record found !!", 404);

		return count;

	}
	
	
	
	public List<Object[]> getPaidEmployeeList(int vendorId)
	{
	return	null;//empVendorAssociationRepository.getUserDetailsWithSumAmountPaidByVendor(vendorId);
	}

}
