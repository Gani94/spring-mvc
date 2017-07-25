package com.gani.repositories;

import com.gani.domain.Order;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Gani on 7/24/17.
 */
public interface OrderRepository extends CrudRepository<Order,Integer> {
}
