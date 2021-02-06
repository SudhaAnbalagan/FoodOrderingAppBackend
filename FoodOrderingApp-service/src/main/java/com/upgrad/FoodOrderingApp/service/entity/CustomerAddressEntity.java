package com.upgrad.FoodOrderingApp.service.entity;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;

@SuppressWarnings("ALL")
@Entity
@Table(name = "customer_address", schema = "public",catalog = "restaurantdb")
public class CustomerAddressEntity implements Serializable {


  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer  id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "customer_id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private CustomerEntity customerId;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "address_id")
  @OnDelete(action = OnDeleteAction.NO_ACTION)
  private AddressEntity addressId;


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
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

}
