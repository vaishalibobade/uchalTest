package com.uchal;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uchal.model.UserDetailsModel;

import java.io.File;
import java.io.IOException;

public class ImageUploader {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        String url = "http://localhost:8090/user/register"; // Replace with your actual API endpoint URL

        // Create the RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Prepare headers
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth("288bd47f-1a9d-4b51-b039-1723b7a356b5");
//        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setContentType(MediaType.APPLICATION_JSON);


        // Prepare the form data
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
      
     String jsonString = "{\n" +
             "     \"userId\":\"\",\n" +
             "     \"firstName\":\"Parth\",\n" +
             "     \"lastName\": \"Surve\",\n" +
             "     \"middleName\":\"mahadeva\",\n" +
             "     \"city\":\"Punee\",\n" +
             "     \"state\": \"Maharashtraa\",\n" +
             "     \"country\": \"IN\",\n" +
             "     \"mobileNumber\": \"5999784807\",\n" +
             "     \"adharNumber\": \"445034970288\",\n" +
             "     \"bloodgroup\": \"A\",\n" +
             "     \"userType\": \"V\",\n" +
             "     \"createdBy\": \"2\",\n" +
             "     \"createdOn\": null,\n" +
             "     \"updatedBy\": \"1\",\n" +
             "     \"updatedOn\": null,\n" +
             "     \"username\": \"Parth\",\n" +
             "     \"password\": \"Password\",\n" +
             "     \"currentStatusId\": \"2\",\n" +
             "     \"streetDetail\":\"near Spine Road ,Tata motors\"\n" +
             "}";
     ObjectMapper objectMapper = new ObjectMapper();
     try {
		UserDetailsModel userDetailsModel = objectMapper.readValue(jsonString, UserDetailsModel.class);
		userDetailsModel.setAdharImage(new FileSystemResource(new File("D:/image2.png")).getContentAsByteArray());
         jsonString = objectMapper.writeValueAsString(userDetailsModel);
         body.add("data", jsonString);

         // Create the request entity
         HttpEntity<UserDetailsModel> requestEntity = new HttpEntity<>(userDetailsModel, headers);
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
         
         
//         restTemplate.put(url, requestEntity, String.class);

	} catch (JsonMappingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (JsonProcessingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
     
      
       
    }
}

