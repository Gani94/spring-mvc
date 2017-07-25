package com.gani.repositories;

import com.gani.domain.Product;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Gani on 7/21/17.
 */
public interface ProductRepository extends CrudRepository<Product, Integer> {
}
