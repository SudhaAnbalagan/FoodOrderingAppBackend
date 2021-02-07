package com.upgrad.FoodOrderingApp.service.entity;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@SuppressWarnings("ALL")
@Entity
@Table(name = "order_item")
@NamedQueries({
        @NamedQuery(name = "orderItemsByOrder", query = "SELECT x FROM OrderItemEntity x WHERE x.order = :order  ORDER BY x.item ASC")

})
public class OrderItemEntity {


  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "order_id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  @NotNull
  private OrderEntity orderId;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "item_id")
  @NotNull
  private ItemEntity  itemId;

  @Column(name = "quantity")
  @NotNull
  private Integer quantity;

  @Column(name = "price")
  @NotNull
  private Integer price;


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public OrderEntity getOrderId() {
    return orderId;
  }

  public void setOrderId(OrderEntity orderId) {
    this.orderId = orderId;
  }


  public ItemEntity  getItemId() {
    return itemId;
  }

  public void setItemId(ItemEntity  itemId) {
    this.itemId = itemId;
  }


  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }


  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

}
