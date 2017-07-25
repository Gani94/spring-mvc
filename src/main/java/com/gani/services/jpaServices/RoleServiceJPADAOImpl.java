package com.gani.services.jpaServices;

import com.gani.domain.Role;
import com.gani.services.RoleService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Gani on 7/21/17.
 */

@Service
@Profile("jpadao")
public class RoleServiceJPADAOImpl extends AbstractJPADAOService implements RoleService {
    @Override
    public List<Role> listAll() {

        EntityManager em = emf.createEntityManager();

        return em.createQuery("from Role",Role.class).getResultList();
    }

    @Override
    public Role getById(Integer id) {
        EntityManager em = emf.createEntityManager();

        return em.find(Role.class,id);
    }

    @Override
    public Role createOrUpdate(Role domainObject) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Role savedRole = em.merge(domainObject);
        em.getTransaction().commit();
        return savedRole;
    }

    @Override
    public void delete(Integer id) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.remove(getById(id));
        em.getTransaction().commit();
    }
}
