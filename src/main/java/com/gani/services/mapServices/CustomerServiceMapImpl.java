package com.gani.services.mapServices;

import com.gani.commands.CustomerForm;
import com.gani.converters.CustomerFormToCustomer;
import com.gani.domain.Customer;
import com.gani.domain.DomainObject;
import com.gani.services.CustomerService;
import com.gani.services.mapServices.AbstractMapService;
import com.gani.services.security.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Gani on 7/7/17.
 */


@Service
@Profile(value = "mapService")
public class CustomerServiceMapImpl extends AbstractMapService implements CustomerService {

//    @Override
//    public void loadDomainObjects() {
//
//        domainObjectMap = new HashMap<>();
//
//        Customer customer1 = new Customer();
//
//        customer1.setId(1);
//        customer1.setFirstName("Teja");
//        customer1.setLastName("Tummuri");
//        customer1.setEmail("tejaganesh94@gmail.com");
//        customer1.setPhoneNumber("9726038221");
//        customer1.setAddressLine1("3477 Lily Way");
//        customer1.setAddressLine2("Apt 226");
//        customer1.setCity("San Jose");
//        customer1.setState("CA");
//        customer1.setZipCode("95134");
//
//        domainObjectMap.put(1,customer1);
//
//        Customer customer2 = new Customer();
//
//        customer2.setId(2);
//        customer2.setFirstName("Peddakapu");
//        customer2.setLastName("Tummuri");
//        customer2.setEmail("tummuri1967@gmail.com");
//        customer2.setPhoneNumber("9848520512");
//        customer2.setAddressLine1("H.No.29 Lahari Jade Residenza");
//        customer2.setAddressLine2("Bhanoor Village");
//        customer2.setCity("Hyderabad");
//        customer2.setState("TG");
//        customer2.setZipCode("502305");
//
//        domainObjectMap.put(2,customer2);
//
//    }

    private EncryptionService encryptionService;
    private CustomerFormToCustomer customerFormToCustomer;

    @Autowired
    public void setCustomerFormToCustomer(CustomerFormToCustomer customerFormToCustomer) {
        this.customerFormToCustomer = customerFormToCustomer;
    }

    @Autowired
    public void setEncryptionService(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    @Override
    public List<DomainObject> listAll() {

        return super.listAll();
    }

    @Override
    public Customer getById(Integer id) {
        return (Customer) super.getById(id);
    }

    @Override
    public void delete(Integer id) {
        Customer u = (Customer) domainObjectMap.get(id);
        u.getUser().setCustomer(null);
        super.delete(id);
    }

    @Override
    public Customer createOrUpdate(Customer customer) {

        if(customer.getUser()!=null && customer.getUser().getPassword()!=null){
            customer.getUser().setEncryptedPassword(
                    encryptionService.encryptString(customer.getUser().getPassword()));
        }

       return (Customer) super.createOrUpdate(customer);
    }

    @Override
    public Integer getNextKey() {
        return super.getNextKey();
    }

    @Override
    public Customer createOrUpdateCustomerForm(CustomerForm customerForm) {

        Customer newCustomer = customerFormToCustomer.convert(customerForm);

        if(newCustomer.getUser().getId()!=null){
            Customer existingCustomer = getById(newCustomer.getId());

            newCustomer.getUser().setEnabled(existingCustomer.getUser().isEnabled());
        }

        return createOrUpdate(newCustomer);
    }
}
