package com.upgrad.FoodOrderingApp.service.entity;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;

@SuppressWarnings("ALL")
@Entity
@Table(name = "customer_auth", schema = "public",catalog = "restaurantdb")
@NamedQueries(
        {
                @NamedQuery( name = "customerAuthByToken", query = "select c from CustomerAuthEntity c where c.accessToken=:accessToken")
        }
)
public class CustomerAuthEntity implements Serializable {


  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "uuid")
  @Size (max = 200)
  @NotNull
  private String uuid;

  @ManyToOne()
  @JoinColumn(name = "customer_id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private CustomerEntity  customerId;

  @Column(name = "access_token")
  @Size(max = 500)
  private String accessToken;

  @Column(name = "login_at")
  private ZonedDateTime  loginAt;

  @Column(name = "logout_at")
  private ZonedDateTime logoutAt;

  @Column(name = "expires_at")
  private ZonedDateTime  expiresAt;


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


  public CustomerEntity getCustomerId() {
    return customerId;
  }

  public void setCustomerId(CustomerEntity customerId) {
    this.customerId = customerId;
  }


  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }


  public ZonedDateTime getLoginAt() {
    return loginAt;
  }

  public void setLoginAt(ZonedDateTime loginAt) {
    this.loginAt = loginAt;
  }


  public ZonedDateTime getLogoutAt() {
    return logoutAt;
  }

  public void setLogoutAt(ZonedDateTime logoutAt) {
    this.logoutAt = logoutAt;
  }


  public ZonedDateTime getExpiresAt() {
    return expiresAt;
  }

  public void setExpiresAt(ZonedDateTime expiresAt) {
    this.expiresAt = expiresAt;
  }

}
