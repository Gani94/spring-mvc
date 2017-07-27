package com.gani.services.repoServices;

import com.gani.commands.CustomerForm;
import com.gani.converters.CustomerFormToCustomer;
import com.gani.converters.CustomerToCustomerForm;
import com.gani.domain.Customer;
import com.gani.repositories.CustomerRepository;
import com.gani.services.CustomerService;
import com.gani.services.security.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gani on 7/24/17.
 */
@Service
@Profile({"springdatajpa"})
public class CustomerServiceRepoImpl implements CustomerService {



    private CustomerRepository customerRepository;
    private EncryptionService encryptionService;
    private CustomerFormToCustomer customerFormToCustomer;
    private CustomerToCustomerForm customerToCustomerForm;

    @Autowired
    public void setCustomerToCustomerForm(CustomerToCustomerForm customerToCustomerForm) {
        this.customerToCustomerForm = customerToCustomerForm;
    }

    @Autowired
    public void setCustomerFormToCustomer(CustomerFormToCustomer customerFormToCustomer) {
        this.customerFormToCustomer = customerFormToCustomer;
    }

    @Autowired
    public void setEncryptionService(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    @Autowired
    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Override
    public List<Customer> listAll() {
        List<Customer> customers = new ArrayList<>();
        customerRepository.findAll().forEach(customers :: add);
        return customers;
    }

    @Override
    public Customer getById(Integer id) {


        return customerRepository.findOne(id);
    }

    @Override
    public Customer createOrUpdate(Customer domainObject) {

        if(domainObject.getUser()!=null && domainObject.getUser().getPassword()!=null){
            domainObject.getUser().setEncryptedPassword(
                    encryptionService.encryptString(domainObject.getUser().getPassword()));
        }
        return customerRepository.save(domainObject);
    }

    @Override
    public void delete(Integer id) {

        customerRepository.delete(id);
    }

    @Override
    public Customer createOrUpdateCustomerForm(CustomerForm customerForm) {

        Customer newCustomer =customerFormToCustomer.convert(customerForm);

        if(newCustomer.getUser().getId()!=null){
            Customer existingCustomer = getById(newCustomer.getId());
            newCustomer.getUser().setEnabled(existingCustomer.getUser().isEnabled());
        }

        return createOrUpdate(newCustomer);
    }

    @Override
    public CustomerForm convertToCustomerForm(Customer customer) {
        return customerToCustomerForm.convert(customer);
    }
}
