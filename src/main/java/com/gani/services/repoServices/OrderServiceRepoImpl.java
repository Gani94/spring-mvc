package com.gani.services.repoServices;

import com.gani.domain.Order;
import com.gani.repositories.OrderRepository;
import com.gani.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gani on 7/24/17.
 */
@Service
@Profile("springdatajpa")
public class OrderServiceRepoImpl implements OrderService {

    private OrderRepository orderRepository;

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> listAll() {
        List<Order> orders = new ArrayList<>();
        orderRepository.findAll().forEach(orders::add);
        return orders;
    }

    @Override
    public Order getById(Integer id) {
        return orderRepository.findOne(id);
    }

    @Override
    public Order createOrUpdate(Order domainObject) {
        return orderRepository.save(domainObject);
    }

    @Override
    public void delete(Integer id) {
        orderRepository.delete(id);
    }
}
