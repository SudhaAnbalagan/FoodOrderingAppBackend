package com.upgrad.FoodOrderingApp.service.entity;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "category", schema = "public",catalog = "restaurantdb")
public class CategoryEntity implements Serializable {


  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "uuid")
  @NotNull
  @Size(max = 200)
  private String uuid;

  @Column(name = "categoryName")
  @NotNull
  @Size(max = 255)
  private String categoryName;

//  @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//  private List<RestaurantCategoryEntity> Restaurant_Category;
//
//  @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//  private List<CategoryItemEntity> Category_Item;

  @ManyToMany
  @JoinTable(name = "category_item", joinColumns = @JoinColumn(name = "category_id"),
          inverseJoinColumns = @JoinColumn(name = "item_id"))
  private List<ItemEntity> items = new ArrayList<>();

//  @ManyToMany
//  @JoinTable(name = "restaurant_category", joinColumns = @JoinColumn(name = "category_id"),
//          inverseJoinColumns = @JoinColumn(name = "restaurant_id"))
//  private List<RestaurantEntity> Restaurant_Category = new ArrayList<>();

//  public List<RestaurantCategoryEntity> getRestaurant_Category() {
//    return Restaurant_Category;
//  }
//
//  public void setRestaurant_Category(List<RestaurantCategoryEntity> restaurant_Category) {
//    Restaurant_Category = restaurant_Category;
//  }


  public List<ItemEntity> getItems() {
    return items;
  }

  public void setItems(List<ItemEntity> items) {
    this.items = items;
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


  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

}
