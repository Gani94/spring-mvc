package com.gani.domain;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Gani on 7/7/17.
 */


//Create a Customer model object.
//The object will have first name, last name, email, phone number, address line one, address line two, city, state, zip code.
//        All properties are strings. All should be private and have getters and setters.

@Entity
public class Customer extends AbstractDomainClass{


    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    @Embedded
    private Address billingAddress;

    @Embedded
    private Address shippingAddress;


    @OneToOne (cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private User user;



    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
