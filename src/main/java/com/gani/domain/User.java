package com.gani.domain;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Gani on 7/13/17.
 */

@Entity
public class User extends AbstractDomainClass {

    private String userName;

    @Transient
    private String password;

    private String encryptedPassword;

    private boolean enabled = true;//by default

    @OneToOne (cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private Customer customer;

    @OneToOne (cascade = CascadeType.ALL, orphanRemoval = true)
    private Cart cart;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {

        this.customer = customer;
        customer.setUser(this);
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }


}
