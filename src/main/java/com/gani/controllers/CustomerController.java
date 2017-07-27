package com.gani.controllers;

import com.gani.commands.CustomerForm;
import com.gani.converters.CustomerFormToCustomer;
import com.gani.converters.CustomerToCustomerForm;
import com.gani.domain.Customer;
import com.gani.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.xml.ws.RequestWrapper;

/**
 * Created by Gani on 7/7/17.
 */

@RequestMapping(value="/customer")
@Controller
public class CustomerController {

    private CustomerService customerService;

    @Autowired
    public void setCustomerService(CustomerService customerService){
        this.customerService = customerService;
    }

    @RequestMapping({"/list","/",""})
    public String listCustomers(Model model){

        model.addAttribute("customers",customerService.listAll());
        return "customer/list";
    }

    @RequestMapping(value="/show/{id}")
    public String getCustomer(@PathVariable int id, Model model){
        model.addAttribute("customer",customerService.getById(id));
        return "customer/show";
    }

    @RequestMapping(value="/new")
    public String newCustomer(Model model){
        model.addAttribute("customer",new CustomerForm());
        return "customer/customerform";
    }

    @RequestMapping(method= RequestMethod.POST)
    public String createOrUpdateCustomer(CustomerForm customerform){

        Customer savedCustomer = customerService.createOrUpdateCustomerForm(customerform);
        return "redirect:/customer/show/"+savedCustomer.getId();
    }

    @RequestMapping(value="/edit/{id}")
    public String editCustomer(@PathVariable int id, Model model){

        Customer customer = customerService.getById(id);
        CustomerForm customerForm = customerService.convertToCustomerForm(customer);

//        customerForm.setCustomerId(customer.getId());
//        customerForm.setCustomerVersion(customer.getVersion());
//        customerForm.setEmail(customer.getEmail());
//        customerForm.setFirstName(customer.getFirstName());
//        customerForm.setLastName(customer.getLastName());
//        customerForm.setPhoneNumber(customer.getPhoneNumber());
//        if(customer.getUser()!=null) {
//            customerForm.setUserId(customer.getUser().getId());
//            customerForm.setUserName(customer.getUser().getUserName());
//            customerForm.setUserVersion(customer.getUser().getVersion());
//        }

        model.addAttribute("customer",customerForm);
        return "customer/customerform";
    }

    @RequestMapping(value="/delete/{id}")
    public String deleteCustomer(@PathVariable int id){
        customerService.delete(id);
        return "redirect:/customer/list";
    }

}
