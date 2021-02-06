package com.upgrad.FoodOrderingApp.api.controller;


import com.upgrad.FoodOrderingApp.api.model.LoginResponse;
import com.upgrad.FoodOrderingApp.api.model.LogoutResponse;
import com.upgrad.FoodOrderingApp.api.model.SignupCustomerRequest;
import com.upgrad.FoodOrderingApp.api.model.SignupCustomerResponse;
import com.upgrad.FoodOrderingApp.service.businness.CustomerService;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthenticationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/customer")
public class CustomerController {


    @Autowired
    private CustomerService customerService;

    @CrossOrigin
    @RequestMapping(
            method = RequestMethod.POST,
            path = "/signup",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SignupCustomerResponse> signUpCustomer(@RequestBody(required = false) final SignupCustomerRequest signupCustomerRequest) throws SignUpRestrictedException {

        //Creating new Customer entity from the request data
        final CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setFirstname(signupCustomerRequest.getFirstName());
        customerEntity.setLastname(signupCustomerRequest.getLastName());
        customerEntity.setEmail(signupCustomerRequest.getEmailAddress());
        customerEntity.setContactNumber(signupCustomerRequest.getContactNumber());
        customerEntity.setPassword(signupCustomerRequest.getPassword());
        customerEntity.setUuid(UUID.randomUUID().toString());

        if (customerEntity.getFirstname().isEmpty() ||
                customerEntity.getContactNumber().isEmpty() ||
                customerEntity.getEmail().isEmpty() ||
                customerEntity.getPassword().isEmpty()) {
            throw new SignUpRestrictedException(
                    "SGR-005", "Except last name all fields should be filled");
        }


        CustomerEntity createdCustomerEntity = customerService.saveCustomer(customerEntity);

        SignupCustomerResponse signupCustomerResponse =
                new SignupCustomerResponse()
                        .id(createdCustomerEntity.getUuid())
                        .status("CUSTOMER SUCCESSFULLY REGISTERED");
        return new ResponseEntity<SignupCustomerResponse>(signupCustomerResponse, HttpStatus.CREATED);
    }


    @CrossOrigin
    @RequestMapping(
            method = RequestMethod.POST,
            path = "/login",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<LoginResponse> login(@RequestHeader("authorization") final String authorization)
            throws AuthenticationFailedException {

        byte[] decode;
        String contactNumber;
        String password;

        // ArrayIndexOutOfBoundsException occurs if the username or password is left as empty or try to
        // authorize without Basic in prefix 'Basic Base64<contactNumber:password>' then it throws
        // AuthenticationFailedException with code as ATH-003
        try {
            decode = Base64.getDecoder().decode(authorization.split("Basic ")[1]);
            String decodedText = new String(decode);
            String[] decodedArray = decodedText.split(":");
            contactNumber = decodedArray[0];
            password = decodedArray[1];
        } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException exception) {
            throw new AuthenticationFailedException(
                    "ATH-003", "Incorrect format of decoded customer name and password");
        }

        CustomerAuthEntity createdCustomerAuthEntity =
                customerService.authenticate(contactNumber, password);


        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setId(createdCustomerAuthEntity.getCustomerId().getUuid());
        loginResponse.setFirstName(createdCustomerAuthEntity.getCustomerId().getFirstname());
        loginResponse.setLastName(createdCustomerAuthEntity.getCustomerId().getLastname());
        loginResponse.setContactNumber(createdCustomerAuthEntity.getCustomerId().getContactNumber());
        loginResponse.setEmailAddress(createdCustomerAuthEntity.getCustomerId().getEmail());
        loginResponse.setMessage("LOGGED IN SUCCESSFULLY");

        HttpHeaders headers = new HttpHeaders();
        headers.add("access-token", createdCustomerAuthEntity.getAccessToken());
        List<String> header = new ArrayList<>();
        header.add("access-token");
        headers.setAccessControlExposeHeaders(header);

        return new ResponseEntity<LoginResponse>(loginResponse, headers, HttpStatus.OK);


    }


    @CrossOrigin
    @RequestMapping(
            method = RequestMethod.POST,
            path = "/logout",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<LogoutResponse> logout(
            @RequestHeader("authorization") final String authorization)
            throws AuthorizationFailedException {

        String[] authParts = authorization.split("Bearer ");
        final String accessToken =  authParts[1];

        CustomerAuthEntity createdCustomerAuthEntity = customerService.logout(accessToken);

        LogoutResponse logoutResponse =
                new LogoutResponse()
                        .id(createdCustomerAuthEntity.getCustomerId().getUuid())
                        .message("LOGGED OUT SUCCESSFULLY");
        return new ResponseEntity<LogoutResponse>(logoutResponse, HttpStatus.OK);
    }



}