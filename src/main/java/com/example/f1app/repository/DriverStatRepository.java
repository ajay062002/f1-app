package com.example.f1app.repository;

import com.example.f1app.model.DriverStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface DriverStatRepository extends JpaRepository<DriverStat, Long> {
  List<DriverStat> findByDriverIdOrderBySeasonAsc(Long driverId);
  List<DriverStat> findBySeasonOrderByPointsDesc(Integer season);

  @Query("select distinct s.season from DriverStat s order by s.season asc")
  List<Integer> seasons();

  @Query("select max(s.season) from DriverStat s")
  Integer latestSeason();
}
