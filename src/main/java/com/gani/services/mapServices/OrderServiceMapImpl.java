package com.gani.services.mapServices;

import com.gani.domain.DomainObject;
import com.gani.domain.Order;
import com.gani.services.OrderService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Gani on 7/17/17.
 */

@Service
@Profile("mapService")
public class OrderServiceMapImpl extends AbstractMapService implements OrderService{

    @Override
    public Order createOrUpdate(Order domainObject) {
        return (Order) super.createOrUpdate(domainObject);
    }

    @Override
    public List<DomainObject> listAll() {
        return super.listAll();
    }

    @Override
    public Order getById(Integer id) {
        return (Order) super.getById(id);
    }


    @Override
    public void delete(Integer id) {
        super.delete(id);
    }

    @Override
    public Integer getNextKey() {
        return super.getNextKey();
    }
}
