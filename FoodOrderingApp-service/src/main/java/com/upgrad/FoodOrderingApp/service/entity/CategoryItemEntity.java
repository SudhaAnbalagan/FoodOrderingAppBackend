package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "category_item")
public class CategoryItemEntity {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "item_id")
  @NotNull
  private ItemEntity itemId;

  @ManyToOne
  @JoinColumn(name = "category_id")
  @NotNull
  private CategoryEntity categoryId;


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public ItemEntity getItemId() {
    return itemId;
  }

  public void setItemId(ItemEntity itemId) {
    this.itemId = itemId;
  }


  public CategoryEntity getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(CategoryEntity categoryId) {
    this.categoryId = categoryId;
  }

}
