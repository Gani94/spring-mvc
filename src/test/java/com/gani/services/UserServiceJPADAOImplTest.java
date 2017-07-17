package com.gani.services;

import com.gani.config.JPAIntegrationConfig;
import com.gani.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by Gani on 7/13/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(JPAIntegrationConfig.class)
@ActiveProfiles({"jpadao"})
public class UserServiceJPADAOImplTest {

    private UserService userService;
    private ProductService productService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Test
    public void testSaveOfUser() throws Exception{

        User user = new User();

        user.setUserName("some username");
        user.setPassword("some password");

        User savedUser = userService.createOrUpdate(user);

        assert savedUser.getId()!=null;
        assert savedUser.getEncryptedPassword()!=null;

        System.out.println(savedUser.getEncryptedPassword());
    }


    @Test
    public  void testSaveOfUserWithCustomer() throws Exception{

        User user = new User();

        user.setUserName("some username");
        user.setPassword("some password");

        Customer customer = new Customer();
        customer.setFirstName("Teja");
        customer.setLastName("Tummuri");

        user.setCustomer(customer);

        User savedUser = userService.createOrUpdate(user);

        assert savedUser.getId()!=null;
        assert savedUser.getEncryptedPassword()!=null;
        assert savedUser.getCustomer()!=null;
    }

    @Test
    public void testAddCartToUser() throws Exception{

        User user = new User();

        user.setUserName("some username");
        user.setPassword("some password");

        user.setCart(new Cart());

        User savedUser = userService.createOrUpdate(user);

        assert savedUser.getId()!=null;
        assert savedUser.getCart()!=null;
        assert savedUser.getCart().getId()!=null;
    }

    @Test
    public void testAddCartToUserWithCartDetails() throws Exception{
        User user = new User();

        user.setUserName("some username");
        user.setPassword("some password");

        List<Product> products = (List<Product>) productService.listAll();


        CartDetail cartItem1 = new CartDetail();
        cartItem1.setProduct(products.get(0));

        CartDetail cartItem2 = new CartDetail();
        cartItem2.setProduct(products.get(1));

        Cart cart = new Cart();
        cart.addCarDetail(cartItem1);
        cart.addCarDetail(cartItem2);

        user.setCart(cart);

        User savedUser = userService.createOrUpdate(user);

        assert savedUser.getId()!=null;
        assert savedUser.getCart()!=null;
        assert savedUser.getCart().getId()!=null;
        assert savedUser.getCart().getCartDetails()!=null;
        assert savedUser.getCart().getCartDetails().size()==2;
    }

    @Test
    public void testAddAndRemoveCartDetailFromUserWithCartDetails() throws Exception{

        User user = new User();

        user.setUserName("some username");
        user.setPassword("some password");

        List<Product> products = (List<Product>) productService.listAll();


        CartDetail cartItem1 = new CartDetail();
        cartItem1.setProduct(products.get(0));

        CartDetail cartItem2 = new CartDetail();
        cartItem2.setProduct(products.get(1));

        Cart cart = new Cart();
        cart.addCarDetail(cartItem1);
        cart.addCarDetail(cartItem2);

        user.setCart(cart);

        User savedUser = userService.createOrUpdate(user);
        assert savedUser.getCart().getCartDetails().size()==2;

        savedUser.getCart().removeCartDetail(savedUser.getCart().getCartDetails().get(0));

        User modifiedSavedUser = userService.createOrUpdate(savedUser);

        assert savedUser.getCart().getCartDetails().size()==1;
        assert modifiedSavedUser.getCart().getCartDetails().size()==1;
    }
}
