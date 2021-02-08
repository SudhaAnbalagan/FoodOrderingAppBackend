package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.*;
import com.upgrad.FoodOrderingApp.service.entity.*;
import com.upgrad.FoodOrderingApp.service.exception.CouponNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class OrderService {

    @Autowired
    private CouponDao couponDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private OrderItemDao orderItemDao;


    /* This method is to save Orders . Takes the orderEntity as paramter and returns the saved OrderEntity*/
    @Transactional(propagation = Propagation.REQUIRED)
    public OrderEntity saveOrder(OrderEntity orderEntity) {
        OrderEntity updatedEntity = orderDao.saveOrder(orderEntity);
        return updatedEntity;
    }


    /* This method is to get Coupon By Coupon's name .Takes  Coupon's name  and returns the Coupon .*/
    public CouponEntity getCouponByCouponName(String couponName) throws CouponNotFoundException {
        if (couponName == null || couponName == "") {
            throw new CouponNotFoundException("CPF-002", "Coupon name field should not be empty");
        }
        CouponEntity couponEntity = couponDao.getCouponByCouponName(couponName);
        if (couponEntity == null) {
            throw new CouponNotFoundException("CPF-001", "No coupon by this name");
        }
        return couponEntity;
    }

    /* This method is to get Orders By Customers.Takes the customerUuid  and returns the list of OrdersEntity.*/
    public List<OrderEntity> getOrdersByCustomers(String customerUuid) {
        CustomerEntity customer = customerDao.getCustomerByCustomerUuid(customerUuid);
        return orderDao.getOrdersByCustomers(customer);

    }

    /* This method is to get Coupon By Coupon's uuid .Takes  Coupon's uuid   and returns the Coupon .*/
    public CouponEntity getCouponByCouponId(String couponId) throws CouponNotFoundException {
        CouponEntity couponEntity = couponDao.getCouponByCouponId(couponId);
        if (couponEntity == null) {
            throw new CouponNotFoundException("CPF-002", "No coupon by this id");
        }
        return couponEntity;

    }

    /* This method is to save Orders Items. Takes the orderItemEntity as paramter and returns the saved OrderItemEntity*/
    @Transactional(propagation = Propagation.REQUIRED)
    public OrderItemEntity saveOrderItem(OrderItemEntity orderItemEntity) {
        OrderItemEntity updatedEntity = orderItemDao.saveOrderItem(orderItemEntity);
        return updatedEntity;
    }

    /* This method is to get Item by Item uuid .Takes item's Uuid  and returns the  item.*/
    public ItemEntity getItemByItemId(Integer itemId) {
        return itemDao.getItemByItemId(itemId);
    }

    /* This method is to get Orders Items By order .Takes the order entity  and returns the list of OrdersItemEntity.*/
    public List<OrderItemEntity> getOrderItemsByOrder(OrderEntity e) {
        return orderItemDao.getOrderItemsByOrder(e);
    }
}
