package com.salle.server.domain.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PARENT_CATEGORY")
public class ParentCategory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String name;

  @OneToMany(mappedBy = "parentCategory")
  private List<Category> categories = new ArrayList<>();
}
