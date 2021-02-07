package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;


@Repository
public class CouponDao {

    @PersistenceContext
    private EntityManager entityManager;

    /* To get coupon from the db using coupon's name
     * */
    public CouponEntity getCouponByCouponName(String couponName){
        try {
            return entityManager.createNamedQuery("getCouponByCouponName",CouponEntity.class).setParameter("coupon_name",couponName).getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }
}
