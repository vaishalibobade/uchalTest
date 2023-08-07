package com.uchal.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.availablephonenumbercountry.Local;
import com.twilio.type.PhoneNumber;
import com.uchal.model.ApiException;

@Service
public class OtpService {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String twilioPhoneNumber;
    
    private static final int OTP_LENGTH = 6;
    private static final long OTP_EXPIRATION_DURATION = 60 * 1000; // 1 minute in milliseconds

    private Map<String, OtpData> phoneNumberToOtpMap = new HashMap<>();

    public void sendOtp(String phoneNumber, String otp) {
    	try {
        String messageBody = "Your OTP is: " + otp;
        Twilio.init(accountSid, authToken);
        Message message = Message.creator(
                new PhoneNumber("+91"+phoneNumber),
                new PhoneNumber(twilioPhoneNumber),
                messageBody
        ).create();

        // Optional: You can log or handle the response from Twilio here
        System.out.println(message.getSid());
    }
    	catch(Exception e)
    	{
    		System.out.println(e);
				throw new ApiException(e.getMessage(), 405);

    	}
    }
    
    
        public void generateOtp(String phoneNumber) {
            String otp = generateRandomOtp(OTP_LENGTH);
            long expirationTime = System.currentTimeMillis() + OTP_EXPIRATION_DURATION;
            phoneNumberToOtpMap.put(phoneNumber, new OtpData(otp, expirationTime));
//            phoneNumberToOtpMap.put(phoneNumber, otp);
            // Here, you should use a messaging service provider (e.g., Twilio) to send the OTP to the user's phone.
            // For this example, we'll skip the actual OTP sending part.
            sendOtp(phoneNumber,otp);//send otp
            System.out.println("OTP for " + phoneNumber + ": " + otp);
        }

        public boolean verifyOtp(String phoneNumber, String otp) {
        	
        	
        	 OtpData otpData = phoneNumberToOtpMap.get(phoneNumber);
             if (otpData == null) {
 				throw new ApiException("OTP not found for the phone number !!", 400);

//                 return false; // OTP not found for the phone number
             }
             long currentTime = System.currentTimeMillis();
             if (currentTime <= otpData.getExpirationTime())
             {
            	 phoneNumberToOtpMap.remove(phoneNumber);
  				throw new ApiException("OTP Expired !!!! ", 401);

//                 return false;

             }
             if (currentTime <= otpData.getExpirationTime() && otp.equals(otpData.getOtp())) {
                 phoneNumberToOtpMap.remove(phoneNumber); // Remove the OTP after successful verification

                 return true;
             }
             
				throw new ApiException("Invalid OTP !!!", 402);

//             return false;
        	
        }

        private String generateRandomOtp(int length) {
        	 Random random = new Random();
             return Integer.toString(100000 + random.nextInt(900000));
 
            // Implement logic to generate a random OTP of the given length
            // For example, using Random or SecureRandom in Java.
            // For simplicity, we'll use a static OTP for demonstration purposes.
//            return "123456";
        }
    

    
    
        private static class OtpData {
            private final String otp;
            private final long expirationTime;

            public OtpData(String otp, long expirationTime) {
                this.otp = otp;
                this.expirationTime = expirationTime;
            }

            public String getOtp() {
                return otp;
            }

            public long getExpirationTime() {
                return expirationTime;
            }
        }
    
    
    
}

