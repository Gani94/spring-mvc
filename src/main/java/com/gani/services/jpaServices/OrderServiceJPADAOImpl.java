package com.gani.services.jpaServices;

import com.gani.domain.Order;
import com.gani.services.OrderService;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Gani on 7/17/17.
 */
public class OrderServiceJPADAOImpl extends AbstractJPADAOService implements OrderService {

    @Override
    public List<Order> listAll() {

        EntityManager em = emf.createEntityManager();
        return em.createQuery("find Order",Order.class).getResultList();
    }

    @Override
    public Order getById(Integer id) {
        EntityManager em = emf.createEntityManager();

        return em.find(Order.class,id);
    }

    @Override
    public Order createOrUpdate(Order domainObject) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        Order savedOrder = em.merge(domainObject);
        em.getTransaction().commit();
        return savedOrder;
    }

    @Override
    public void delete(Integer id) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.remove(em.find(Order.class,id));
        em.getTransaction().commit();
    }
}
