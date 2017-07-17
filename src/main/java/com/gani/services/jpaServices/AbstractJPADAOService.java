package com.gani.services.jpaServices;

import com.gani.services.CRUDService;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;

/**
 * Created by Gani on 7/13/17.
 */
public class AbstractJPADAOService {

    protected EntityManagerFactory emf;

    @PersistenceUnit
    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }

}
