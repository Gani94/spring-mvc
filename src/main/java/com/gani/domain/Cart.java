package com.gani.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Gani on 7/14/17.
 */

@Entity
public class Cart extends AbstractDomainClass{



    @OneToOne
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cart", orphanRemoval = true)
    private List<CartDetail> cartDetails = new ArrayList<CartDetail>();



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartDetail> getCartDetails() {
        return cartDetails;
    }

    public void setCartDetails(List<CartDetail> cartDetails) {
        this.cartDetails = cartDetails;
    }

    public void addCarDetail(CartDetail cartDetail){
        cartDetails.add(cartDetail);
        cartDetail.setCart(this);
    }

    public void removeCartDetail(CartDetail cartDetail){
        cartDetails.remove(cartDetail);
        cartDetail.setCart(null);
    }

}
