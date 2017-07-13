package com.gani.services;

import com.gani.domain.Customer;
import com.gani.domain.DomainObject;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Gani on 7/7/17.
 */


@Service
@Profile(value = "map")
public class CustomerServiceImpl extends AbstractMapService implements CustomerService {

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
        super.delete(id);
    }

    @Override
    public Customer createOrUpdate(Customer customer) {
       return (Customer) super.createOrUpdate(customer);
    }

    @Override
    public Integer getNextKey() {
        return super.getNextKey();
    }

}
