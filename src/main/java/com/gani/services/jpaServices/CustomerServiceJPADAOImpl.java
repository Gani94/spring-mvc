package com.gani.services.jpaServices;

import com.gani.commands.CustomerForm;
import com.gani.converters.CustomerFormToCustomer;
import com.gani.converters.CustomerToCustomerForm;
import com.gani.domain.Customer;
import com.gani.services.CustomerService;
import com.gani.services.security.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;

/**
 * Created by Gani on 7/12/17.
 */

@Service
@Profile(value = "jpadao")
public class CustomerServiceJPADAOImpl extends AbstractJPADAOService implements CustomerService {

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


    @Override
    public List<Customer> listAll() {

        EntityManager em = emf.createEntityManager();

        return em.createQuery("from Customer",Customer.class).getResultList();
    }

    @Override
    public Customer getById(Integer id) {
        EntityManager em = emf.createEntityManager();
        return em.find(Customer.class,id);
    }

    @Override
    public Customer createOrUpdate(Customer domainObject) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        if(domainObject.getUser()!=null && domainObject.getUser().getPassword()!=null){
            domainObject.getUser().setEncryptedPassword(
                    encryptionService.encryptString(domainObject.getUser().getPassword()));
        }
        Customer savedCustomer = em.merge(domainObject);
        em.getTransaction().commit();
        return savedCustomer;
    }

    @Override
    public void delete(Integer id) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        Customer deleteCustomer = em.find(Customer.class,id);
        if(deleteCustomer.getUser()!=null)
            deleteCustomer.getUser().setCustomer(null);
        deleteCustomer.setUser(null);
        em.remove(em.merge(deleteCustomer));
        em.getTransaction().commit();
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
