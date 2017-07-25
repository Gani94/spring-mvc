package com.gani.services.repoServices;

import com.gani.domain.Product;
import com.gani.repositories.ProductRepository;
import com.gani.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gani on 7/21/17.
 */

@Service
@Profile({"springdatajpa"})
public class ProductServiceRepoImpl implements ProductService {

    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> listAll() {
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach(products :: add);
        return products;
    }

    @Override
    public Product getById(Integer id) {
        return productRepository.findOne(id);
    }

    @Override
    public Product createOrUpdate(Product domainObject) {
        return productRepository.save(domainObject);
    }

    @Override
    public void delete(Integer id) {
            productRepository.delete(id);
    }
}
