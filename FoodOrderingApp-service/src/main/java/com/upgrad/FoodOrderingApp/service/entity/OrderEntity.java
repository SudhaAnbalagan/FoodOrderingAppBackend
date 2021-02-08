package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.security.Timestamp;
import java.util.Date;

@SuppressWarnings("ALL")
@Entity
@Table(name = "orders", schema = "public", catalog = "restaurantdb")
@NamedQueries({
        @NamedQuery(name = "getOrdersByCouponId", query = "SELECT x FROM OrderEntity x WHERE x.couponId = :couponId"),
        @NamedQuery(name = "getAllOrders", query = "SELECT x FROM OrderEntity x"),
        @NamedQuery(name = "getOrdersByCustomers", query = "SELECT o FROM OrderEntity o WHERE o.customerId = :customer ORDER BY o.date DESC"),
        @NamedQuery(name = "getOrdersByAddress", query = "SELECT x FROM OrderEntity x WHERE x.addressId = :address")
})
public class OrderEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid")
    @Size(max = 200)
    @NotNull
    private String uuid;

    @Column(name = "bill")
    @NotNull
    private double bill;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "coupon_id")
    private CouponEntity couponId;

    @Column(name = "discount")
    private double discount;

    @Column(name = "date")
    @NotNull
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "payment_id")
    private PaymentEntity paymentId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    @NotNull
    private CustomerEntity customerId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    @NotNull
    private AddressEntity addressId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id")
    @NotNull
    private RestaurantEntity restaurantId;

    public OrderEntity() {
    }

    public OrderEntity(String uuid, Double bill, CouponEntity couponId, Double discount, Date date, PaymentEntity paymentId, CustomerEntity customerId, AddressEntity addressId, RestaurantEntity restaurantId) {
        this.uuid = uuid;
        this.bill = bill;
        this.couponId = couponId;
        this.discount = discount;
        this.date = date;
        this.paymentId = paymentId;
        this.customerId = customerId;
        this.addressId = addressId;
        this.restaurantId = restaurantId;

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


    public double getBill() {
        return bill;
    }

    public void setBill(double bill) {
        this.bill = bill;
    }


    public CouponEntity getCouponId() {
        return couponId;
    }

    public void setCouponId(CouponEntity couponId) {
        this.couponId = couponId;
    }


    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public PaymentEntity getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(PaymentEntity paymentId) {
        this.paymentId = paymentId;
    }


    public CustomerEntity getCustomerId() {
        return customerId;
    }

    public void setCustomerId(CustomerEntity customerId) {
        this.customerId = customerId;
    }


    public AddressEntity getAddressId() {
        return addressId;
    }

    public void setAddressId(AddressEntity addressId) {
        this.addressId = addressId;
    }


    public RestaurantEntity getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(RestaurantEntity restaurantId) {
        this.restaurantId = restaurantId;
    }

}
