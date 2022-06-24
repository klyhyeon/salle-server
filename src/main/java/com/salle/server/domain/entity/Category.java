package com.salle.server.domain.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "CATEGORY")
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  private ParentCategory parentCategory;

  @OneToMany(mappedBy = "category")
  private List<Product> products;

}