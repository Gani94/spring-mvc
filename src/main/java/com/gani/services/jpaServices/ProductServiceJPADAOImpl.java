package com.gani.services.jpaServices;

import com.gani.domain.Product;
import com.gani.services.ProductService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;

/**
 * Created by Gani on 7/11/17.
 */

@Service
@Profile(value = "jpadao")
public class ProductServiceJPADAOImpl extends AbstractJPADAOService implements ProductService {

    @Override
    public List<Product> listAll() {

        EntityManager em = emf.createEntityManager();
        return em.createQuery("from Product", Product.class).getResultList();
    }

    @Override
    public Product getById(Integer id) {
        EntityManager em = emf.createEntityManager();

        return em.find(Product.class,id);
    }

    @Override
    public Product createOrUpdate(Product domainObject) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        Product savedProduct = em.merge(domainObject);
        em.getTransaction().commit();

        return savedProduct;
    }

    @Override
    public void delete(Integer id) {

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.remove(em.find(Product.class,id));
        em.getTransaction().commit();

    }
}
