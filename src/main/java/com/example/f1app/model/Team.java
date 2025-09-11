package com.example.f1app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "teams")
public class Team {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable=false, unique = true)
  private String name;

  private String principal;
  private String base;

  @Column(name="power_unit")
  private String powerUnit;

  // NEW
  @Column(name="image_url")
  private String imageUrl;

  public Team(){}

  public Team(String name){ this.name = name; }

  // getters/setters
  public Long getId(){ return id; }
  public void setId(Long id){ this.id = id; }

  public String getName(){ return name; }
  public void setName(String name){ this.name = name; }

  public String getPrincipal(){ return principal; }
  public void setPrincipal(String principal){ this.principal = principal; }

  public String getBase(){ return base; }
  public void setBase(String base){ this.base = base; }

  public String getPowerUnit(){ return powerUnit; }
  public void setPowerUnit(String powerUnit){ this.powerUnit = powerUnit; }

  public String getImageUrl(){ return imageUrl; }
  public void setImageUrl(String imageUrl){ this.imageUrl = imageUrl; }
}
