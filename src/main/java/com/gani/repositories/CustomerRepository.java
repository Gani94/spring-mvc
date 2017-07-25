package com.gani.repositories;

import com.gani.domain.Customer;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Gani on 7/24/17.
 */
public interface CustomerRepository extends CrudRepository<Customer,Integer>{
}
