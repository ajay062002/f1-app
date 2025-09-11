package com.example.f1app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "circuits", indexes = {
  @Index(name = "idx_circuit_name", columnList = "name")
})
public class Circuit {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true) private String name;
  private String city;
  private String country;
  private Integer laps;
  private Double lapLengthKm;
  private Double raceDistanceKm;
  private Integer firstGrandPrixYear;
  private String imageUrl;

  @Lob @Column(columnDefinition = "TEXT")
  private String svgPath; // SVG <path d=""> data for animation

  public Circuit() {}
  public Circuit(String name, String city, String country, Integer laps, Double lapLengthKm,
                 Double raceDistanceKm, Integer firstGpYear, String imageUrl, String svgPath) {
    this.name = name; this.city = city; this.country = country; this.laps = laps;
    this.lapLengthKm = lapLengthKm; this.raceDistanceKm = raceDistanceKm;
    this.firstGrandPrixYear = firstGpYear; this.imageUrl = imageUrl; this.svgPath = svgPath;
  }
  // getters/setters …
  public Long getId(){return id;} public void setId(Long id){this.id=id;}
  public String getName(){return name;} public void setName(String v){this.name=v;}
  public String getCity(){return city;} public void setCity(String v){this.city=v;}
  public String getCountry(){return country;} public void setCountry(String v){this.country=v;}
  public Integer getLaps(){return laps;} public void setLaps(Integer v){this.laps=v;}
  public Double getLapLengthKm(){return lapLengthKm;} public void setLapLengthKm(Double v){this.lapLengthKm=v;}
  public Double getRaceDistanceKm(){return raceDistanceKm;} public void setRaceDistanceKm(Double v){this.raceDistanceKm=v;}
  public Integer getFirstGrandPrixYear(){return firstGrandPrixYear;} public void setFirstGrandPrixYear(Integer v){this.firstGrandPrixYear=v;}
  public String getImageUrl(){return imageUrl;} public void setImageUrl(String v){this.imageUrl=v;}
  public String getSvgPath(){return svgPath;} public void setSvgPath(String v){this.svgPath=v;}
}
