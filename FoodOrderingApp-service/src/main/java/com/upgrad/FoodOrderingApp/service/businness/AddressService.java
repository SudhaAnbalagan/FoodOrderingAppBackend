package com.upgrad.FoodOrderingApp.service.businness;


import com.upgrad.FoodOrderingApp.service.dao.AddressDao;
import com.upgrad.FoodOrderingApp.service.dao.CustomerAddressDao;
import com.upgrad.FoodOrderingApp.service.dao.OrderDao;
import com.upgrad.FoodOrderingApp.service.dao.StateDao;
import com.upgrad.FoodOrderingApp.service.entity.*;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SaveAddressException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
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

    @Autowired
    private OrderDao orderDao;


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

    public List<AddressEntity> getAllAddress(CustomerEntity customerEntity) {

        //Creating List of AddressEntities.
        List<AddressEntity> addressEntities = new LinkedList<>();

        //Calls Method of customerAddressDao,getAllCustomerAddressByCustomer and returns AddressList.
        List<CustomerAddressEntity> customerAddressEntities  = customerAddressDao.getAllCustomerAddressByCustomer(customerEntity);
        if(customerAddressEntities != null) { //Checking if CustomerAddressEntity is null else extracting address and adding to the addressEntites list.
            customerAddressEntities.forEach(customerAddressEntity -> {
                addressEntities.add(customerAddressEntity.getAddressId());
            });
        }

        return addressEntities;

    }

    public AddressEntity getAddressByUUID(String addressUuid,CustomerEntity customerEntity)throws AuthorizationFailedException,AddressNotFoundException{
        if(addressUuid == null){//Check for Address UUID not being empty
            throw new AddressNotFoundException("ANF-005","Address id can not be empty");
        }

        //Calls getAddressByUuid method of addressDao to get addressEntity
        AddressEntity addressEntity = addressDao.getAddressByUuid(addressUuid);
        if (addressEntity == null){//Checking if null throws corresponding exception.
            throw new AddressNotFoundException("ANF-003","No address by this id");
        }

        //Getting CustomerAddressEntity by address
        CustomerAddressEntity customerAddressEntity = customerAddressDao.getCustomerAddressByAddress(addressEntity);

        //Checking if the address belong to the customer requested.If no throws corresponding exception.
        if(customerAddressEntity.getCustomerId().getUuid() == customerEntity.getUuid()){
            return addressEntity;
        }else{
            throw new AuthorizationFailedException("ATHR-004","You are not authorized to view/update/delete any one else's address");
        }

    }


    /*This method is to deleteAddress of the customerEntity.This method returns Address Entity.
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public AddressEntity deleteAddress(AddressEntity addressEntity) {

        //Calls getOrdersByAddress of orderDao to orders with corresponding address.
        List<OrderEntity> ordersEntities = orderDao.getOrdersByAddress(addressEntity);

        if(ordersEntities == null||ordersEntities.isEmpty()) { //Checking if no orders are present with this address.
            //Calls deleteAddress of addressDao to delete the corresponding address.
            AddressEntity deletedAddressEntity = addressDao.deleteAddress(addressEntity);
            return deletedAddressEntity;
        }else{
            //Updating the active status
            addressEntity.setActive(0);

            //Calls updateAddressActiveStatus method of addressDao to update address active status.
            AddressEntity updatedAddressActiveStatus =  addressDao.updateAddressActiveStatus(addressEntity);
            return updatedAddressActiveStatus;
        }
    }









    //To Validate the Pincode
    public boolean isPincodeValid(String pincode){
        Pattern p = Pattern.compile("\\d{6}\\b");
        Matcher m = p.matcher(pincode);
        return (m.find() && m.group().equals(pincode));
    }



}
