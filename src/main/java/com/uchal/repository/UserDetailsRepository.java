package com.uchal.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uchal.entity.EmpVendorAssociation;
import com.uchal.entity.UserDetails;
import com.uchal.model.UserDetailsWithSumModel;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Integer> {
	// You can add custom query methods or use the default methods provided by
	// JpaRepository
	// Example: findByFirstName(String firstName);
	List<UserDetails> findAllByAdharNumber(long adharNumber);

	List<UserDetails> findAllByMobileNumber(long adharNumber);

	@Query("SELECT u FROM UserDetails u WHERE u.userId != :userId AND u.adharNumber = :adhar")
	List<UserDetails> findByAdharIdNotEqual(@Param("userId") int id, @Param("adhar") long adhar);

	@Query("SELECT u FROM UserDetails u WHERE u.userId != :userId AND u.mobileNumber= :mobile")
	List<UserDetails> findByMobileIdNotEqual(@Param("userId") int id, @Param("mobile") long mobile);

//	UserDetails findByUsername(String username);
	UserDetails findByFirstName(String username);

	@Query("SELECT u FROM UserDetails u WHERE u.userType = :userType")
	List<UserDetails> findByUserType(@Param("userType") String userType);

//	@Query("SELECT ud, eva FROM UserDetails ud JOIN EmpVendorAssociation eva ON ud.userId = eva.employeeId WHERE eva.vendorId = :id")
//	List<Object[]> getEmployeeDetailsByVendorId(@Param("id") int vendorId);
	
	
//	@Query("SELECT eva, ud.firstName, ud.lastName, ud.middleName, ud.mobileNumber, ud.userType, mus.status  FROM UserDetails ud JOIN EmpVendorAssociation eva ON ud.userId = eva.employeeId LEFT JOIN MasterUserStatus mus ON ud.currentStatusId = mus.id WHERE eva.employeeId = :employeeId")
//    List<Object[]> getEmployeeDetailsByEmployeeId(@Param("id") int employeeId);

	
	// Update the parameter name to match the named parameter in the query
    @Query("SELECT eva, ud.firstName, ud.lastName, ud.middleName, ud.mobileNumber, ud.userType, mus.status " +
           "FROM UserDetails ud " +
           "JOIN EmpVendorAssociation eva ON ud.userId = eva.employeeId " +
           "LEFT JOIN MasterUserStatus mus ON ud.currentStatusId = mus.id " +
           "WHERE eva.employeeId = :employeeId")
    List<Object[]> getEmployeeDetailsByEmployeeId(@Param("employeeId") int employeeId);

	
	
    @Query("SELECT ud.userId, ud.firstName, ud.middleName, ud.lastName,  ud.userType FROM UserDetails ud")
    List<Object[]> getAllUserList();
    @Query("SELECT ud.userId, ud.firstName, ud.middleName, ud.lastName,  ud.userType FROM UserDetails ud WHERE ud.userType = :userType")
    List<Object[]> getAllUserListbyType(@Param("userType") String userType );
    @Query("SELECT ud.userId, ud.firstName, ud.middleName, ud.lastName,  ud.userType FROM UserDetails ud WHERE ud.userType = 'S' OR ud.userType = 'E' ")
    List<Object[]> getAllVendorSubVendor();
    
////	@Query("SELECT mus.status, ud.firstName, ud.lastName, ud.middleName, ud.mobileNumber, ud.userType FROM UserDetails ud JOIN MasterUserStatus mus ON ud.currentStatusId = mus.id  WHERE ud.mobileNumber = :mobileNumber")
//
//	@Query("SELECT mus.status, ud.firstName, ud.middleName, ud.lastName,  ud.userType, ud.mobileNumber ,ud.userId FROM UserDetails ud JOIN MasterUserStatus mus ON ud.currentStatusId = mus.id WHERE ud.mobileNumber = :mobileNumber OR ud.firstName LIKE %:Name% OR ud.middleName LIKE %:Name% OR ud.lastName LIKE %:Name%")
//	List<Object[]> getDataWithPartialMatch(@Param("mobileNumber") long mobileNumber,
//	                                       @Param("Name") String Name );
	
	@Query("SELECT mus.status, ud.firstName, ud.middleName, ud.lastName, mut.userType, ud.mobileNumber, ud.userId, ud.createdBy "
		       + "FROM UserDetails ud "
		       + "JOIN MasterUserStatus mus ON ud.currentStatusId = mus.id "
		       + "LEFT JOIN MasterUserType mut ON ud.userType = mut.abreviation "
		       + "WHERE ud.adharNumber = :adharNumber OR ud.firstName LIKE %:Name% OR ud.middleName LIKE %:Name% OR ud.lastName LIKE %:Name%")
		List<Object[]> getDataWithPartialMatch(@Param("adharNumber") long adharNumber,
		                                                  @Param("Name") String name);
		
//		@Query("SELECT mus.status, ud.firstName, ud.middleName, ud.lastName, mut.userType, ud.mobileNumber, ud.userId, ud.createdBy "
//			       + "FROM UserDetails ud "
//			       + "JOIN MasterUserStatus mus ON ud.currentStatusId = mus.id "
//			       + "LEFT JOIN MasterUserType mut ON ud.userType = mut.abreviation "
//			       + "WHERE (ud.adharNumber = :adharNumber OR ud.firstName LIKE %:Name% OR ud.middleName LIKE %:Name% OR ud.lastName LIKE %:Name%) "
//			       + "AND ud.userType = :userType")
//			List<Object[]> getDataWithPartialMatchAndUserType(@Param("adharNumber") long adharNumber,
//			                                                  @Param("Name") String name,
//			                                                   @Param("userType") String userType);
		@Query("SELECT mus.status, ud.firstName, ud.middleName, ud.lastName, mut.userType, ud.mobileNumber, ud.userId, ud.createdBy "
		        + "FROM UserDetails ud "
		        + "JOIN MasterUserStatus mus ON ud.currentStatusId = mus.id "
		        + "LEFT JOIN MasterUserType mut ON ud.userType = mut.abreviation "
		        + "WHERE (ud.adharNumber = :adharNumber OR ud.firstName LIKE %:name% OR ud.middleName LIKE %:name% OR ud.lastName LIKE %:name%) "
		        + "AND ud.userType = :userType")
		List<Object[]> getDataWithPartialMatchAndUserType(@Param("adharNumber") long adharNumber,
		                                                  @Param("name") String name,
		                                                  @Param("userType") String userType);

		
		


			@Query("SELECT mus.status, ud.firstName, ud.middleName, ud.lastName, mut.userType, ud.mobileNumber, ud.userId, ud.createdBy "
				       + "FROM UserDetails ud "
				       + "JOIN MasterUserStatus mus ON ud.currentStatusId = mus.id "
				       + "LEFT JOIN MasterUserType mut ON ud.userType = mut.abreviation "
				       + "WHERE (ud.adharNumber = :adharNumber OR ud.firstName LIKE CONCAT('%', :Name, '%') OR ud.middleName LIKE CONCAT('%', :Name, '%') OR ud.lastName LIKE CONCAT('%', :Name, '%')) "
				       + "AND (ud.userType = 'S' OR ud.userType = 'E')")
				List<Object[]> getDataWithPartialMatchAndMultipleUserType(@Param("adharNumber") long adharNumber,
				                                                          @Param("Name") String Name);

			
			@Query("SELECT ud, mus.status, mut.userType "
				       + "FROM UserDetails ud "
				       + "JOIN MasterUserStatus mus ON ud.currentStatusId = mus.id "
				       + "LEFT JOIN MasterUserType mut ON ud.userType = mut.abreviation "
				       + "WHERE ud.userId = :userId")
				List<Object[]> getUserDetailsById(@Param("userId") int userId);

			
//				@Query("SELECT ud "
//			       + "FROM UserDetails ud "
//			       + "WHERE ud.userId IN (SELECT DISTINCT eva.employeeId FROM EmpVendorAssociation eva WHERE eva.vendorId = :vendorId)")
//			List<UserDetails[]> getUserDetailsWithSumAmountPaidByVendor(@Param("vendorId") int vendorId);

//			@Query("SELECT new com.uchal.model.UserDetailsWithSumModel(ud, (SELECT COALESCE(SUM(eva.amountPaid), 0) FROM EmpVendorAssociation eva WHERE eva.employeeId = ud.userId AND eva.vendorId = :vendorId)) "
//			           + "FROM UserDetails ud "
//			           + "WHERE EXISTS (SELECT 1 FROM EmpVendorAssociation eva WHERE eva.employeeId = ud.userId AND eva.vendorId = :vendorId)")
//			List<UserDetailsWithSumModel> getUserDetailsWithSumAmountPaidByVendor(@Param("vendorId") int vendorId);

			
			
			

				    // Retrieve UserDetails entities associated with the specified vendor
				    @Query("SELECT ud , mut.userType FROM UserDetails ud JOIN MasterUserType mut ON ud.userType = mut.abreviation  " +
				           "WHERE EXISTS (SELECT 1 FROM EmpVendorAssociation eva WHERE eva.employeeId = ud.userId AND eva.vendorId = :vendorId)")
				    List<Object[]> getUserDetailsByVendor(@Param("vendorId") int vendorId);

				    
				


}
			


