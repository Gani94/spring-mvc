package com.gani.services;

import com.gani.config.JPAIntegrationConfig;
import com.gani.domain.Customer;
import com.gani.domain.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by Gani on 7/12/17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(JPAIntegrationConfig.class)
@ActiveProfiles({"jpadao"})
public class CustomerServiceJPADAOImplTest {

    private CustomerService customerService;

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }


    @Test
    public void testListMethod() throws Exception {

        List<Customer> customers = (List<Customer>) customerService.listAll();

        assert customers.size() == 2;

    }

    @Test
    public void testGetByIdMethod() throws Exception{

        Integer id=1;
        Customer returnedCustomer = customerService.getById(id);

        assert returnedCustomer.getId() == id;
    }

    @Test
    public void testDeleteMethod() throws Exception{

        Integer id =1;
        customerService.delete(id);

        assert customerService.listAll().size() == 1;
    }
}
