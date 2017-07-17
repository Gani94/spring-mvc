package com.gani.domain;

import com.gani.enums.OrderStatus;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Gani on 7/17/17.
 */

@Entity
@Table(name = "Order_Table")
public class Order extends AbstractDomainClass {

    @OneToOne
    private Customer customer;

    @OneToMany (cascade = {CascadeType.ALL}, mappedBy = "order", orphanRemoval = true)
    private List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();

    private OrderStatus orderStatus;

    @Embedded
    private Address shippingAddress;

    @Temporal(TemporalType.DATE)
    private Date dateShipped;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Date getDateShipped() {
        return dateShipped;
    }

    public void setDateShipped(Date dateShipped) {
        this.dateShipped = dateShipped;
    }

    public void addOrderDetail(OrderDetail orderDetail){
        orderDetail.setOrder(this);
        this.orderDetails.add(orderDetail);
    }

    public void removeOrderDetail(OrderDetail orderDetail){
        this.orderDetails.remove(orderDetail);
        orderDetail.setOrder(null);
    }
}
