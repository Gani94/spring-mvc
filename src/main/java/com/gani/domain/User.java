package com.gani.domain;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable
    // ~ defaults to @JoinTable(name = "USER_ROLE", joinColumns = @JoinColumn(name = "user_id"),
    //     inverseJoinColumns = @joinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();

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
        if(customer!=null)
        customer.setUser(this);
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
        cart.setUser(this);
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role){
        if(!this.getRoles().contains(role))
            this.getRoles().add(role);

        if(!role.getUsers().contains(this))
            role.getUsers().add(this);
    }

    public void removeRole(Role role){
        this.getRoles().remove(role);
        role.getUsers().remove(this);
    }
}
