package com.gani.services;

import com.gani.commands.CustomerForm;
import com.gani.domain.Customer;

import java.util.List;

/**
 * Created by Gani on 7/7/17.
 */
public interface CustomerService extends CRUDService<Customer>{

    Customer createOrUpdateCustomerForm(CustomerForm customerForm);
    CustomerForm convertToCustomerForm(Customer customer);
}
