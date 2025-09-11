package com.example.f1app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "drivers")
public class Driver {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name="first_name", nullable=false)
  private String firstName;

  @Column(name="last_name", nullable=false)
  private String lastName;

  private String code;
  private String nationality;

  @Column(unique = true)
  private Integer number;

  @Column(name="team_name")
  private String teamName;

  // NEW
  @Column(name="image_url")
  private String imageUrl;

  public Driver(){}

  public Driver(String firstName, String lastName, Integer number, String teamName) {
    this.firstName = firstName; this.lastName = lastName; this.number = number; this.teamName = teamName;
  }

  // getters/setters
  public Long getId(){ return id; }
  public void setId(Long id){ this.id = id; }

  public String getFirstName(){ return firstName; }
  public void setFirstName(String firstName){ this.firstName = firstName; }

  public String getLastName(){ return lastName; }
  public void setLastName(String lastName){ this.lastName = lastName; }

  public String getCode(){ return code; }
  public void setCode(String code){ this.code = code; }

  public String getNationality(){ return nationality; }
  public void setNationality(String nationality){ this.nationality = nationality; }

  public Integer getNumber(){ return number; }
  public void setNumber(Integer number){ this.number = number; }

  public String getTeamName(){ return teamName; }
  public void setTeamName(String teamName){ this.teamName = teamName; }

  public String getImageUrl(){ return imageUrl; }
  public void setImageUrl(String imageUrl){ this.imageUrl = imageUrl; }
}
