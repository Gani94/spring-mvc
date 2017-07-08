package com.gani.services;

import com.gani.domain.Product;

import java.util.List;

/**
 * Created by Gani on 7/7/17.
 */
public interface CRUDService<T> {

    List<?> listAll();

    T getById(Integer id);

    T createOrUpdate(T domainObject);

    void delete(Integer id);
}
