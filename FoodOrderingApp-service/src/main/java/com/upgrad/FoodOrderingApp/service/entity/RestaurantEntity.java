package com.upgrad.FoodOrderingApp.service.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "restaurant", schema = "public",catalog = "restaurantdb")
public class RestaurantEntity {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "uuid")
  @Size(max = 200)
  @NotNull
  private String uuid;

  @Column(name = "restaurant_name")
  @Size(max = 50)
  @NotNull
  private String restaurantName;

  @Column(name = "photo_url")
  @Size(max = 255)
  private String photoUrl;

  @Column(name = "customer_rating")
  @NotNull
  private String customerRating;

  @Column(name = "average_price_for_two")
  @NotNull
  private long averagePriceForTwo;

  @Column(name = "number_of_customers_rated")
  @NotNull
  private long numberOfCustomersRated;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "address_id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private AddressEntity addressId;


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


  public String getRestaurantName() {
    return restaurantName;
  }

  public void setRestaurantName(String restaurantName) {
    this.restaurantName = restaurantName;
  }


  public String getPhotoUrl() {
    return photoUrl;
  }

  public void setPhotoUrl(String photoUrl) {
    this.photoUrl = photoUrl;
  }


  public String getCustomerRating() {
    return customerRating;
  }

  public void setCustomerRating(String customerRating) {
    this.customerRating = customerRating;
  }


  public long getAveragePriceForTwo() {
    return averagePriceForTwo;
  }

  public void setAveragePriceForTwo(long averagePriceForTwo) {
    this.averagePriceForTwo = averagePriceForTwo;
  }


  public long getNumberOfCustomersRated() {
    return numberOfCustomersRated;
  }

  public void setNumberOfCustomersRated(long numberOfCustomersRated) {
    this.numberOfCustomersRated = numberOfCustomersRated;
  }


  public AddressEntity getAddressId() {
    return addressId;
  }

  public void setAddressId(AddressEntity addressId) {
    this.addressId = addressId;
  }

}
