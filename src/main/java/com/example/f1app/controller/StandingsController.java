package com.example.f1app.controller;

import com.example.f1app.model.DriverStat;
import com.example.f1app.repository.DriverStatRepository;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/standings")
@CrossOrigin
public class StandingsController {

  private final DriverStatRepository stats;
  public StandingsController(DriverStatRepository stats){ this.stats = stats; }

  public record DriverStanding(Long driverId, String name, String team, double points, int wins, Integer position) {}
  public record ConstructorStanding(String team, double points) {}
  public record SeasonInfo(List<Integer> seasons, Integer latest) {}

  @GetMapping("/seasons")
  public SeasonInfo seasons(){
    List<Integer> all = stats.seasons();
    Integer latest = stats.latestSeason();
    return new SeasonInfo(all, latest == null && !all.isEmpty() ? all.get(all.size()-1) : latest);
  }

  @GetMapping("/latest/drivers")
  public List<DriverStanding> latestDrivers(){
    Integer latest = stats.latestSeason();
    if(latest == null) return List.of();
    return driversFor(latest);
  }

  @GetMapping("/latest/constructors")
  public List<ConstructorStanding> latestConstructors(){
    Integer latest = stats.latestSeason();
    if(latest == null) return List.of();
    return constructorsFor(latest);
  }

  @GetMapping("/{season}/drivers")
  public List<DriverStanding> drivers(@PathVariable Integer season){
    return driversFor(season);
  }

  @GetMapping("/{season}/constructors")
  public List<ConstructorStanding> constructors(@PathVariable Integer season){
    return constructorsFor(season);
  }

  private List<DriverStanding> driversFor(Integer season){
    return stats.findBySeasonOrderByPointsDesc(season).stream()
      .map(s -> new DriverStanding(
        s.getDriver().getId(),
        s.getDriver().getFirstName() + " " + s.getDriver().getLastName(),
        s.getTeam(),
        s.getPoints()==null?0.0:s.getPoints(),
        s.getWins()==null?0:s.getWins(),
        s.getPosition()
      )).sorted(Comparator.<DriverStanding>comparingDouble(DriverStanding::points).reversed())
      .toList();
  }

  private List<ConstructorStanding> constructorsFor(Integer season){
    Map<String, Double> byTeam = new HashMap<>();
    for(DriverStat s : stats.findBySeasonOrderByPointsDesc(season)){
      String team = s.getTeam()==null? "Unknown" : s.getTeam();
      byTeam.merge(team, s.getPoints()==null?0.0:s.getPoints(), Double::sum);
    }
    return byTeam.entrySet().stream()
      .map(e -> new ConstructorStanding(e.getKey(), e.getValue()))
      .sorted(Comparator.<ConstructorStanding>comparingDouble(ConstructorStanding::points).reversed())
      .toList();
  }
}
