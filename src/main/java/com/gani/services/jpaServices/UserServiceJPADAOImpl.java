package com.gani.services.jpaServices;

import com.gani.domain.User;
import com.gani.services.UserService;
import com.gani.services.security.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Gani on 7/13/17.
 */

@Service
@Profile("jpadao")
public class UserServiceJPADAOImpl extends AbstractJPADAOService implements UserService{

    private EncryptionService encryptionService;

    @Autowired
    public void setEncryptionService(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    @Override
    public List<User> listAll() {
        EntityManager em = emf.createEntityManager();

        return em.createQuery("from user",User.class).getResultList();

    }

    @Override
    public User getById(Integer id) {
        EntityManager em = emf.createEntityManager();


        return em.find(User.class,id);
    }

    @Override
    public User createOrUpdate(User domainObject) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        if(domainObject.getPassword()!=null){
            domainObject.setEncryptedPassword(encryptionService.encryptString(domainObject.getPassword()));
        }

        User savedUser = em.merge(domainObject);
        em.getTransaction().commit();

        return savedUser;
    }

    @Override
    public void delete(Integer id) {



        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.find(User.class,id));
        em.getTransaction().commit();
    }
}
