package com.example.f1app.model;

import jakarta.persistence.*;

@Entity @Table(name="driver_stats",
  indexes = {@Index(name="idx_driver_season", columnList="driver_id, season")})
public class DriverStat {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

  @ManyToOne(optional=false) @JoinColumn(name="driver_id")
  private Driver driver;

  @Column(nullable=false) private Integer season;
  private String team;
  private Integer races;
  private Integer wins;
  private Integer poles;
  private Integer podiums;
  private Double points;
  private Integer position; // championship finishing position

  // getters/setters
  public Long getId(){return id;} public void setId(Long id){this.id=id;}
  public Driver getDriver(){return driver;} public void setDriver(Driver d){this.driver=d;}
  public Integer getSeason(){return season;} public void setSeason(Integer s){this.season=s;}
  public String getTeam(){return team;} public void setTeam(String t){this.team=t;}
  public Integer getRaces(){return races;} public void setRaces(Integer v){this.races=v;}
  public Integer getWins(){return wins;} public void setWins(Integer v){this.wins=v;}
  public Integer getPoles(){return poles;} public void setPoles(Integer v){this.poles=v;}
  public Integer getPodiums(){return podiums;} public void setPodiums(Integer v){this.podiums=v;}
  public Double getPoints(){return points;} public void setPoints(Double v){this.points=v;}
  public Integer getPosition(){return position;} public void setPosition(Integer v){this.position=v;}
}
