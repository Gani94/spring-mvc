package com.gani.bootstrap;

import com.gani.domain.*;
import com.gani.enums.OrderStatus;
import com.gani.services.ProductService;
import com.gani.services.RoleService;
import com.gani.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Gani on 7/11/17.
 */

@Component
public class SpringJPABootstrap implements ApplicationListener<ContextRefreshedEvent>{

    private ProductService productService;
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setProductService (ProductService productService){
        this.productService=productService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        loadUsersAndCustomers();
        loadProducts();
        loadCarts();
        loadOrderHistory();
        loadRoles();
        assignUsersToDefaultRole();
    }

    private void assignUsersToDefaultRole() {
        List<User> users = (List<User>) userService.listAll();
        List<Role> roles = (List<Role>) roleService.listAll();


            roles.forEach(role -> {
                if(role.getRole().equalsIgnoreCase("customer")){
                    users.forEach(user ->{
                        user.addRole(role);
                        userService.createOrUpdate(user);
                    });
                }
        });
    }

    private void loadRoles() {
        Role role = new Role();
        role.setRole("Customer");
        roleService.createOrUpdate(role);


    }

    private void loadOrderHistory() {
        List<User> users = (List<User>) userService.listAll();
        List<Product> products = (List<Product>) productService.listAll();

        users.forEach(user -> {
            Order order1 = new Order();
            order1.setCustomer(user.getCustomer());
            order1.setOrderStatus(OrderStatus.SHIPPED);

            products.forEach(product -> {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setProduct(product);
                orderDetail.setQuantity(8);

                order1.addOrderDetail(orderDetail);
            });


        });
    }

    private void loadCarts() {

        List<User> users = (List<User>) userService.listAll();
        List<Product> products = (List<Product>) productService.listAll();

        users.forEach(user -> {
            user.setCart(new Cart());
            CartDetail cartDetail = new CartDetail();
            cartDetail.setProduct(products.get(2));
            cartDetail.setQuantity(2);
            user.getCart().addCarDetail(cartDetail);
            userService.createOrUpdate(user);
        });

    }

    private void loadUsersAndCustomers() {


        Customer customer1 = new Customer();
        customer1.setFirstName("Teja");
        customer1.setLastName("Tummuri");
        customer1.setEmail("tejaganesh94@gmail.com");
        customer1.setPhoneNumber("9726038221");

        Address billingAddress1 = new Address();

        billingAddress1.setAddressLine1("3477 Lily Way");
        billingAddress1.setAddressLine2("Apt 226");
        billingAddress1.setCity("San Jose");
        billingAddress1.setState("CA");
        billingAddress1.setZipCode("95134");


        customer1.setBillingAddress(billingAddress1);

        User user1 = new User();
        user1.setUserName("gani94");
        user1.setPassword("abc123");
        user1.setCustomer(customer1);


        userService.createOrUpdate(user1);

        Customer customer2 = new Customer();
        customer2.setFirstName("Peddakapu");
        customer2.setLastName("Tummuri");
        customer2.setEmail("tummuri1967@gmail.com");
        customer2.setPhoneNumber("9848520512");

        Address billingAddress2 = new Address();
        billingAddress2.setAddressLine1("H.No.29 Lahari Jade Residenza");
        billingAddress2.setAddressLine2("Bhanoor Village");
        billingAddress2.setCity("Hyderabad");
        billingAddress2.setState("TG");
        billingAddress2.setZipCode("502305");

        customer2.setBillingAddress(billingAddress2);

        User user2 = new User();
        user2.setUserName("tsnaidu");
        user2.setPassword("abc1234");
        user2.setCustomer(customer2);

        userService.createOrUpdate(user2);
    }

    private void loadProducts() {

        Product product1 = new Product();

        product1.setDescription("Product 1");
        product1.setPrice(new BigDecimal("12.99"));
        product1.setImageUrl("http://example.com/product1");
        productService.createOrUpdate(product1);

        Product product2 = new Product();
        product2.setDescription("Product 2");
        product2.setPrice(new BigDecimal("14.99"));
        product2.setImageUrl("http://example.com/product2");
        productService.createOrUpdate(product2);

        Product product3 = new Product();
        product3.setDescription("Product 3");
        product3.setPrice(new BigDecimal("34.99"));
        product3.setImageUrl("http://example.com/product3");
        productService.createOrUpdate(product3);


        Product product4 = new Product();
        product4.setDescription("Product 4");
        product4.setPrice(new BigDecimal("44.99"));
        product4.setImageUrl("http://example.com/product4");
        productService.createOrUpdate(product4);

        Product product5 = new Product();
        product5.setDescription("Product 2");
        product5.setPrice(new BigDecimal("25.99"));
        product5.setImageUrl("http://example.com/product5");
        productService.createOrUpdate(product5);

    }

}
