package com.uchal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uchal.entity.EmpVendorAssociation;
import com.uchal.model.DashboardStatusCount;
@Repository
public interface EmpVendorAssociationRepository  extends JpaRepository<EmpVendorAssociation, Integer> {

	
	public List<EmpVendorAssociation> findAllByVendorId(int vendorId);
	
	@Query("SELECT COUNT(a.vendorId) FROM EmpVendorAssociation a WHERE a.employeeId = :employeeId")	
	public long countByID(@Param ("employeeId") int employeeId);
	
	@Query("SELECT COUNT(a.employeeId) FROM EmpVendorAssociation a WHERE a.vendorId = :vendorId")	
	public long getEmployeeCountByVendorId(@Param ("vendorId") int vendorId);
	
	@Query("SELECT a.userStatus.status, COUNT(a.userStatus) FROM EmpVendorAssociation a WHERE a.employeeId = :employeeId GROUP BY a.userStatus.status")
	List<Object[]> countByStatus(@Param("employeeId") int employeeId);

	@Query("SELECT a.userStatus.status, COUNT(a.userStatus) FROM EmpVendorAssociation a WHERE a.vendorId = :vendorId GROUP BY a.userStatus.status")
	List<Object[]> getEmployeeStatusCountByVendorId(@Param("vendorId") int vendorId);
	
	@Query("SELECT a.userStatus.status, COUNT(a.userStatus) FROM EmpVendorAssociation a  GROUP BY a.userStatus.status")
	List<Object[]> getEmployeeStatusCountAllVendor();
	
	@Query("SELECT COUNT(a) FROM EmpVendorAssociation a WHERE a.employeeId = :employeeId AND a.userStatus.status = 'Completed'")
	Long getCompletedStatusCountByEmployeeId(@Param("employeeId") int employeeId);

	@Query("SELECT COUNT(a) FROM EmpVendorAssociation a WHERE  a.userStatus.status = 'Completed'")
	Long getAllCompletedStatusCount();
	
	@Query("SELECT COUNT(a) FROM EmpVendorAssociation a WHERE a.employeeId = :employeeId")
	Long getTotalStatusCountByEmployeeId(@Param("employeeId") int employeeId);

	
	
	@Query("SELECT COUNT(a) FROM EmpVendorAssociation a")
	Long getTotalStatusCount();

	
//	@Query("SELECT SUM(a.amount_paid) FROM EmpVendorAssociation a WHERE a.vendorId = :vendorId GROUP BY a.employeeId")	
//	@Query("SELECT ud.firstName, ud.lastName, ud.middleName, ud.mobileNumber, ud.userType, (SELECT SUM(eva.amountPaid) FROM EmpVendorAssociation eva WHERE eva.employeeId = ud.userId) "
//		       + "FROM UserDetails ud "
//		       + "WHERE ud.userId IN (SELECT DISTINCT eva.employeeId FROM EmpVendorAssociation eva WHERE eva.vendorId = :vendorId)")
//		List<Object[]> getUserDetailsWithSumAmountPaidByVendor(@Param("vendorId") int vendorId);


}
