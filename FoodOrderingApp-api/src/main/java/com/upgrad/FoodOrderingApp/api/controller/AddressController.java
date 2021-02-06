package com.upgrad.FoodOrderingApp.api.controller;


import com.upgrad.FoodOrderingApp.api.model.SaveAddressRequest;
import com.upgrad.FoodOrderingApp.api.model.SaveAddressResponse;
import com.upgrad.FoodOrderingApp.service.businness.AddressService;
import com.upgrad.FoodOrderingApp.service.businness.CustomerService;
import com.upgrad.FoodOrderingApp.service.dao.CustomerAddressDao;
import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SaveAddressException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin
@RestController
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerAddressDao customerAddressDao;


    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST,
            path = "/address",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SaveAddressResponse> saveAddress(@RequestHeader("authorization") final String authorization, @RequestBody(required = false) SaveAddressRequest saveAddressRequest)
            throws AuthorizationFailedException, AddressNotFoundException, SaveAddressException {
        //Access the accessToken from the request Header
        String accessToken = authorization.split("Bearer ")[1];

        //Calls customerService getCustomer Method to check the validity of the customer.this methods returns the customerEntity  to be updated  with address.
        CustomerEntity customerEntity = customerService.getCustomer(accessToken);
        //Creating addressEntity from SaveAddressRequest data.
        AddressEntity addressEntity = new AddressEntity();

        addressEntity.setFlatBuilNumber(saveAddressRequest.getFlatBuildingName());
        addressEntity.setCity(saveAddressRequest.getCity());
        addressEntity.setLocality(saveAddressRequest.getLocality());
        addressEntity.setPincode(saveAddressRequest.getPincode());
        addressEntity.setUuid(UUID.randomUUID().toString());

        //Getting stateEntity using address service method getStateByUUID.
        StateEntity stateEntity = addressService.getStateByUUID(saveAddressRequest.getStateUuid());

        //Calls saveAddress method to save the new addressEntity Created.
        AddressEntity savedAddress = addressService.saveAddress(addressEntity,stateEntity);

        //Calls saveCustomerAddressEntity method to save to save foreign Keys of the newly created address and the Customer.
        CustomerAddressEntity customerAddressEntity = addressService.saveCustomerAddressEntity(customerEntity,savedAddress);

        //Creating SaveAddressResponse response
        SaveAddressResponse saveAddressResponse = new SaveAddressResponse()
                .id(savedAddress.getUuid())
                .status("ADDRESS SUCCESSFULLY REGISTERED");
        return new ResponseEntity<SaveAddressResponse>(saveAddressResponse, HttpStatus.CREATED);

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerAddressEntity saveCustomerAddressEntity(CustomerEntity customerEntity,AddressEntity addressEntity){

        //Creating new CustomerAddressEntity and setting the data.
        CustomerAddressEntity customerAddressEntity = new CustomerAddressEntity();
        customerAddressEntity.setCustomerId(customerEntity);
        customerAddressEntity.setAddressId(addressEntity);

        //Saving the newly Created CustomerAddressEntity in the DB.
        CustomerAddressEntity createdCustomerAddressEntity = customerAddressDao.saveCustomerAddress(customerAddressEntity);
        return createdCustomerAddressEntity;

    }

}
