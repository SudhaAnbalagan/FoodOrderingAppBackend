package com.upgrad.FoodOrderingApp.service.businness;


import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CustomerService {


    @Autowired
    private PasswordCryptographyProvider passwordCryptographyProvider;

    @Autowired
    private CustomerDao customerDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerEntity saveCustomer(CustomerEntity customerEntity) throws SignUpRestrictedException {

        /*Validation for required fields if any field other than lastname is empty then it throws
        SignUpRestrictedException exception*/

        if(!customerEntity.getFirstname().isEmpty()
           && !customerEntity.getEmail().isEmpty()
           && !customerEntity.getContactNumber().isEmpty()
           && !customerEntity.getPassword().isEmpty()){

            // if contactNumber is already registered throws exception with code SGR-001
            if (isContactNumberInUse(customerEntity.getContactNumber())) {
                throw new SignUpRestrictedException(
                        "SGR-001", "This contact number is already registered! Try other contact number.");
            }
            // checks the email entered by user is valid or not
            if (!isValidEmail(customerEntity.getEmail())) {
                throw new SignUpRestrictedException("SGR-002", "Invalid email-id format!");
            }
            // checks the contact number entered by user is valid or not
            if (!isValidContactNumber(customerEntity.getContactNumber())) {
                throw new SignUpRestrictedException("SGR-003", "Invalid contact number!");
            }
            // checks the password entered by user is valid or not
            if (!isValidPassword(customerEntity.getPassword())) {
                throw new SignUpRestrictedException("SGR-004", "Weak password!");
            }

            customerEntity.setUuid(UUID.randomUUID().toString());
            // encrypts salt and password
            String[] encryptedText = passwordCryptographyProvider.encrypt(customerEntity.getPassword());
            customerEntity.setSalt(encryptedText[0]);
            customerEntity.setPassword(encryptedText[1]);
            return customerDao.saveCustomer(customerEntity);


        }

    else {
        throw new SignUpRestrictedException(
                "SGR-005", "Except last name all fields should be filled");
    }

    }

















   //Common util methods to check validity of contact number, email and password etc..


    // method checks for given contact number is already registered or not
    private boolean isContactNumberInUse(final String contactNumber) {
        return customerDao.getCustomerByContactNumber(contactNumber) != null;
    }

    // method checks for format of the email is correct or not using EmailValidator
    private boolean isValidEmail(final String emailAddress) {
        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(emailAddress);
    }

    // method checks for given contact number is valid or not
    private boolean isValidContactNumber(final String contactNumber) {
        if (contactNumber.length() != 10) {
            return false;
        }
        for (int i = 0; i < contactNumber.length(); i++) {
            if (!Character.isDigit(contactNumber.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidPassword(final String password) {
        return password.matches("^(?=.*?[A-Z])(?=.*?[0-9])(?=.*?[#@$%&*!^]).{8,}$");
    }


}
