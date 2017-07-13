package com.gani.services;

import com.gani.config.JPAIntegrationConfig;
import com.gani.domain.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by Gani on 7/12/17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(JPAIntegrationConfig.class)
@ActiveProfiles({"jpadao"})
public class ProductServiceJPADAOImplTest {

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Test
    public void testListMethod() throws Exception {

        List<Product> products = (List<Product>) productService.listAll();

        assert products.size() == 5;

    }

    @Test
    public void testGetByIdMethod() throws Exception{

        Integer id=1;
        Product returnedProduct = productService.getById(id);

        assert returnedProduct.getId() == id;
    }

    @Test
    public void testDeleteMethod() throws Exception{

        Integer id =1;
        productService.delete(id);

        assert productService.listAll().size() == 4;
    }


}
