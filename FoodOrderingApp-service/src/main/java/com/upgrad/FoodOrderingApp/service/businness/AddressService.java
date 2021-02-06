package com.upgrad.FoodOrderingApp.service.businness;


import com.upgrad.FoodOrderingApp.service.dao.AddressDao;
import com.upgrad.FoodOrderingApp.service.dao.CustomerAddressDao;
import com.upgrad.FoodOrderingApp.service.dao.StateDao;
import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SaveAddressException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AddressService {

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private StateDao stateDao;

    @Autowired
    private CustomerAddressDao customerAddressDao;


    @Transactional(propagation = Propagation.REQUIRED)
    public AddressEntity saveAddress(AddressEntity addressEntity, StateEntity stateEntity)throws SaveAddressException {
        //Checking if any field is empty in the address entity.
        if (addressEntity.getCity() == null || addressEntity.getFlatBuilNumber() == null || addressEntity.getPincode() == null || addressEntity.getLocality() == null){
            throw new SaveAddressException("SAR-001","No field can be empty");
        }

        if(isPincodeValid(addressEntity.getPincode())){
            throw new SaveAddressException("SAR-002","Invalid pincode");
        }

        //Setting state to the address
        addressEntity.setStateEntityId(stateEntity);

        //Passing the addressEntity to addressDao saveAddress method which returns saved address.
        AddressEntity savedAddress = addressDao.saveAddress(addressEntity);

        //returning SavedAddress
        return savedAddress;
    }

    public StateEntity getStateByUUID (String uuid)throws AddressNotFoundException {
        //Calls getStateByUuid od StateDao to get all the State details.
        StateEntity stateEntity = stateDao.getStateByUuid(uuid);
        if(stateEntity == null) {//Checking if its null to return error message.
            throw new AddressNotFoundException("ANF-002", "No state by this id");
        }
        return  stateEntity;
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









    //To Validate the Pincode
    public boolean isPincodeValid(String pincode){
        Pattern p = Pattern.compile("\\d{6}\\b");
        Matcher m = p.matcher(pincode);
        return (m.find() && m.group().equals(pincode));
    }



}
