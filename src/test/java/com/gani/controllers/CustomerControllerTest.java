package com.gani.controllers;

import com.gani.domain.Customer;
import com.gani.services.CustomerService;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Created by Gani on 7/11/17.
 */
public class CustomerControllerTest {


    private MockMvc mockMvc;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();

    }


    @Test
    public void testList() throws Exception{

        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer());
        customers.add(new Customer());

        when(customerService.listAll()).thenReturn((List) customers);

        mockMvc.perform(get("/customer/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/list"))
                .andExpect(model().attribute("customers", hasSize(2)));
    }


    @Test
    public void testShowCustomer() throws Exception{

        Customer customer = new Customer();
        customer.setId(1);

        when(customerService.getById(1)).thenReturn(customer);

        mockMvc.perform(get("/customer/show/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/show"))
                .andExpect(model().attribute("customer", instanceOf(Customer.class)));
    }

    @Test
    public void testNewCustomer() throws Exception{

        verifyZeroInteractions(customerService);

        mockMvc.perform(get("/customer/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/customerform"))
                .andExpect(model().attribute("customer",instanceOf(Customer.class)));

    }


    @Test
    public void testEditCustomer() throws Exception{

        Customer customer = new Customer();
        customer.setId(1);
        when(customerService.getById(1)).thenReturn(customer);

        mockMvc.perform(get("/customer/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/customerform"))
                .andExpect(model().attribute("customer",instanceOf(Customer.class)));
    }

    @Test
    public void testDeleteCustomer() throws Exception{

        Integer id = 1;

        mockMvc.perform(get("/customer/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/customer/list"));

        verify(customerService, times(1)).delete(id);
    }

    @Test
    public void testCreateOrUpdate() throws Exception{

        Integer id = 1;
        String firstName = "Teja";
        String lastName = "Tummuri";
        String email = "tejaganesh94@gmail.com";
        String phoneNumber = "9726038221";
        String addressLine1 = "3477 Lily Way";
        String addressLine2 = "Apt 226";
        String city = "San Jose";
        String state = "CA";
        String zipCode = "95134";

        Customer customer = new Customer();

        customer.setId(1);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setPhoneNumber(phoneNumber);
        customer.setAddressLine1(addressLine1);
        customer.setAddressLine2(addressLine2);
        customer.setCity(city);
        customer.setState(state);
        customer.setZipCode(zipCode);

        when(customerService.createOrUpdate(Matchers.<Customer>any())).thenReturn(customer);

        mockMvc.perform(post("/customer")
                .param("id","1")
                .param("firstName",firstName)
                .param("lastName",lastName)
                .param("email",email)
                .param("phoneNumber",phoneNumber)
                .param("addressLine1",addressLine1)
                .param("addressLine2",addressLine2)
                .param("city",city)
                .param("state",state)
                .param("zipCode",zipCode))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/customer/show/"+id))
                .andExpect(model().attribute("customer",instanceOf(Customer.class)))
                .andExpect(model().attribute("customer",hasProperty("id",is(id))))
                .andExpect(model().attribute("customer",hasProperty("firstName",is(firstName))))
                .andExpect(model().attribute("customer",hasProperty("lastName",is(lastName))))
                .andExpect(model().attribute("customer",hasProperty("email",is(email))))
                .andExpect(model().attribute("customer",hasProperty("phoneNumber",is(phoneNumber))))
                .andExpect(model().attribute("customer",hasProperty("addressLine1",is(addressLine1))))
                .andExpect(model().attribute("customer",hasProperty("addressLine2",is(addressLine2))))
                .andExpect(model().attribute("customer",hasProperty("city",is(city))))
                .andExpect(model().attribute("customer",hasProperty("state",is(state))))
                .andExpect(model().attribute("customer",hasProperty("zipCode",is(zipCode))));

        ArgumentCaptor<Customer> boundCustomer = ArgumentCaptor.forClass(Customer.class);
        verify(customerService).createOrUpdate(boundCustomer.capture());

        assertEquals(id, boundCustomer.getValue().getId());
        assertEquals(firstName, boundCustomer.getValue().getFirstName());
        assertEquals(lastName, boundCustomer.getValue().getLastName());
        assertEquals(email, boundCustomer.getValue().getEmail());
        assertEquals(phoneNumber, boundCustomer.getValue().getPhoneNumber());
        assertEquals(addressLine1, boundCustomer.getValue().getAddressLine1());
        assertEquals(addressLine2, boundCustomer.getValue().getAddressLine2());
        assertEquals(city, boundCustomer.getValue().getCity());
        assertEquals(state, boundCustomer.getValue().getState());
        assertEquals(zipCode, boundCustomer.getValue().getZipCode());


    }
}
