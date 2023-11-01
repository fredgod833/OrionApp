package com.openclassrooms.mddapi.models;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "topics")
@Getter
@Setter
public class Topic {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;
  @Column(name = "description")
  private String description;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private Date createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private Date updatedAt;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "topic", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
  private Set<Subscription> subscribers = new HashSet<>();


}
