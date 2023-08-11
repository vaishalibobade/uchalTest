package com.uchal;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uchal.entity.FIRDetails;
import com.uchal.model.UserDetailsModel;

public class FIRUploader {
	  public static void main(String[] args) throws IOException {
	        String url = "http://localhost:8090/uploadFIR"; // Replace with your actual API endpoint URL

	        // Create the RestTemplate
	        RestTemplate restTemplate = new RestTemplate();

	        // Prepare headers
	        HttpHeaders headers = new HttpHeaders();
	        headers.setBearerAuth("ab335db1-6c93-4588-8516-f1539b59cd1e");
//	        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
	        headers.setContentType(MediaType.APPLICATION_JSON);


	        // Prepare the form data
	        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
	      
	     String jsonString = "{\n" +
	               "\"employeeId\":\"8\"\n" +
	             "}";
	     ObjectMapper objectMapper = new ObjectMapper();
	     try {
	    	 FIRDetails userDetailsModel = objectMapper.readValue(jsonString, FIRDetails.class);
			userDetailsModel.setFirImage(convertImageToByteArray ("D:/image2.png"));
	         jsonString = objectMapper.writeValueAsString(userDetailsModel);
	         body.add("data", jsonString);

	         // Create the request entity
	         HttpEntity<FIRDetails> requestEntity = new HttpEntity<>(userDetailsModel, headers);
	         // Send the POST request
	         ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
	         String responseBody = response.getBody();
	         System.out.println(responseBody);
	         // Handle the response
	         if (response.getStatusCode().is2xxSuccessful()) {
	             System.out.println("Image uploaded successfully!");
	         } else {
	             System.out.println("Failed to upload image.");
	             System.out.println("Response: " + response.getBody());
	         }
	         
	         
//	         restTemplate.put(url, requestEntity, String.class);

		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     
	      
	       
	    }
	  
	  
	  public static byte[] convertImageToByteArray(String imagePath) throws IOException {
		    Path path = Paths.get(imagePath);
		    return Files.readAllBytes(path);
		}

}
