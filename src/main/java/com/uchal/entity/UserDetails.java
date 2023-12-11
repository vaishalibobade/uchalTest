package com.uchal.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
@Entity
@Table(name = "user_details")

public class UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id")
	private int userId;
	@JsonIgnore
	@OneToOne(mappedBy = "userDetails", cascade = CascadeType.ALL)
	private LoginDetails loginDetails;
	
	
	@OneToMany(mappedBy = "employeeId")
    private List<EmpVendorAssociation> empVendorAssociations;
	
    private String firstName;
    private String lastName;
    private String middleName;
    private String city;
    private String state;
    private String country;
    private long mobileNumber;
    private long adharNumber;
    @Lob
    private byte[] adharImage;
    private String bloodgroup;
    private String userType;
    private int createdBy;
    private LocalDateTime createdOn;
    private Integer updatedBy;
    private LocalDateTime  updatedOn;
    private String streetDetail;
    private String statusRemarks;
//    @OneToMany()
    private int currentStatusId;
    
    
    //New Added feilds
    private Integer  registrationUnder=0;
    private String registrationPaymentStatus;
    private Integer registrationPaymentAmount;
    
    
    
//    @OneToOne
//    @JoinColumn(name = "login_dtl_id")
//    private LoginDetails loginDetails;
//    
    public LoginDetails getLoginDetails() {
		return loginDetails;
	}

	public void setLoginDetails(LoginDetails loginDetails) {
		this.loginDetails = loginDetails;
	}
//
//	@OneToOne(mappedBy = "userDetails", cascade = CascadeType.ALL)
//    private LoginDetails loginDetails;
    
    // Getters and Setters
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getMiddleName() {
        return middleName;
    }
    
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    public long getMobileNumber() {
        return mobileNumber;
    }
    
    public void setMobileNumber(long l) {
        this.mobileNumber = l;
    }
    
    public long getAdharNumber() {
        return adharNumber;
    }
    
    public void setAdharNumber(long l) {
        this.adharNumber = l;
    }
    
    public byte[] getAdharImage() {
        return adharImage;
    }
    
    public void setAdharImage(byte[] adharImage) {
        this.adharImage = adharImage;
    }
    
    public String getBloodgroup() {
        return bloodgroup;
    }
    
    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }
    
    public String getUserType() {
        return userType;
    }
    
    public void setUserType(String userType) {
        this.userType = userType;
    }
    
    public int getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }
    
    public LocalDateTime getCreatedOn() {
        return createdOn;
    }
    
    public void setCreatedOn(LocalDateTime localDateTime) {
        this.createdOn = localDateTime;
    }
    
    public Integer getUpdatedBy() {
        return updatedBy;
    }
    
    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }
    
    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }
    
    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }
    
    public String getStreetDetail() {
        return streetDetail;
    }
    
    public void setStreetDetail(String streetDetail) {
        this.streetDetail = streetDetail;
    }

	public int getCurrentStatusId() {
		return currentStatusId;
	}

	public void setCurrentStatusId(int currentStatusId) {
		this.currentStatusId = currentStatusId;
	}

	public String getStatusRemarks() {
		return statusRemarks;
	}

	public void setStatusRemarks(String statusRemarks) {
		this.statusRemarks = statusRemarks;
	}

	public List<EmpVendorAssociation> getEmpVendorAssociations() {
		// TODO Auto-generated method stub
		return this.empVendorAssociations;
	}

	public int getRegistrationUnder() {
		return registrationUnder;
	}

	public void setRegistrationUnder(int registrationUnder) {
		this.registrationUnder = registrationUnder;
	}

	

	public int getRegistrationPaymentAmount() {
		return registrationPaymentAmount;
	}

	public void setRegistrationPaymentAmount(int registrationPaymentAmount) {
		this.registrationPaymentAmount = registrationPaymentAmount;
	}

	public String getRegistrationPaymentStatus() {
		return registrationPaymentStatus;
	}

	public void setRegistrationPaymentStatus(String registrationPaymentStatus) {
		this.registrationPaymentStatus = registrationPaymentStatus;
	}
}

