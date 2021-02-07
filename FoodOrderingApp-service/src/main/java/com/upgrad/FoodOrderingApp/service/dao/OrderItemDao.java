package com.upgrad.FoodOrderingApp.service.dao;


import com.upgrad.FoodOrderingApp.service.entity.OrderEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderItemEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class OrderItemDao {

    @PersistenceContext
    private EntityManager entityManager;

    /* To get List of order items from the DB, corresponding to a particular order
     * */
    public List<OrderItemEntity> getOrderItemsByOrder(OrderEntity order) {
        try {
            List<OrderItemEntity> orderItemEntities = entityManager.createNamedQuery("orderItemsByOrder", OrderItemEntity.class).setParameter("order", order).getResultList();
            return orderItemEntities;
        } catch (NoResultException nre) {
            return null;
        }
    }

    /* To save order items in  the DB
     * */
    public OrderItemEntity saveOrderItem(OrderItemEntity orderItemEntity){
        entityManager.persist(orderItemEntity);
        return orderItemEntity;
    }


}
