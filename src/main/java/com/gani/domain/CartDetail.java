package com.gani.domain;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Gani on 7/14/17.
 */

@Entity
public class CartDetail extends AbstractDomainClass{

    @ManyToOne
    private Cart cart;

    @OneToOne
    private Product product;

    private Integer quantity;

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void addQuantity(Integer increment){
        this.quantity+=increment;
    }

    public void removeQuantity(Integer decrement) {
        this.quantity-=decrement;
    }


}
