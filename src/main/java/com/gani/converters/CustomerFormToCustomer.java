package com.gani.converters;

import com.gani.commands.CustomerForm;
import com.gani.domain.Address;
import com.gani.domain.Customer;
import com.gani.domain.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by Gani on 7/24/17.
 */
@Component
public class CustomerFormToCustomer implements Converter<CustomerForm,Customer>{

    @Override
    public Customer convert(CustomerForm customerForm) {

        Customer customer = new Customer();
        customer.setUser(new User());
        customer.setBillingAddress(new Address());
        customer.setShippingAddress(new Address());
        customer.setId(customerForm.getCustomerId());
        customer.getUser().setId(customerForm.getUserId());
        customer.getUser().setVersion(customerForm.getUserVersion());
        customer.getUser().setUserName(customerForm.getUserName());
        customer.getUser().setPassword(customerForm.getPasswordText());
        customer.setVersion(customerForm.getCustomerVersion());
        customer.setFirstName(customerForm.getFirstName());
        customer.setLastName(customerForm.getLastName());
        customer.setEmail(customerForm.getEmail());
        customer.setPhoneNumber(customerForm.getPhoneNumber());

        return customer;
    }
}
