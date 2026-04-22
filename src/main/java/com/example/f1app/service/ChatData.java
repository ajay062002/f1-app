package com.example.f1app.service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Static F1 reference data used by ChatService.
 * Separated so ChatService stays focused on answer routing logic.
 */
public final class ChatData {

    private ChatData() {}

    // ── Teams ────────────────────────────────────────────────────────────────

    public static final Map<String, List<String>> TEAM_TO_DRIVERS = Map.ofEntries(
        Map.entry("ferrari",      List.of("Charles Leclerc", "Carlos Sainz")),
        Map.entry("red bull",     List.of("Max Verstappen", "Sergio Pérez")),
        Map.entry("mercedes",     List.of("Lewis Hamilton", "George Russell")),
        Map.entry("mclaren",      List.of("Lando Norris", "Oscar Piastri")),
        Map.entry("aston martin", List.of("Fernando Alonso", "Lance Stroll")),
        Map.entry("rb",           List.of("Yuki Tsunoda", "Liam Lawson")),
        Map.entry("haas",         List.of("Nico Hülkenberg", "Kevin Magnussen")),
        Map.entry("sauber",       List.of("Valtteri Bottas", "Zhou Guanyu")),
        Map.entry("williams",     List.of("Alex Albon", "Logan Sargeant")),
        Map.entry("alpine",       List.of("Esteban Ocon", "Pierre Gasly"))
    );

    public static final List<String> TEAMS = TEAM_TO_DRIVERS.keySet().stream()
        .sorted().collect(Collectors.toList());

    // ── Team stats ───────────────────────────────────────────────────────────

    public static final Map<String, Map<String, Object>> TEAM_STATS = Map.of(
        "ferrari",   Map.of("constructor", "Ferrari",        "world_titles", 16, "notable_driver", "Michael Schumacher"),
        "red bull",  Map.of("constructor", "Red Bull Racing","world_titles",  6, "notable_driver", "Max Verstappen"),
        "mercedes",  Map.of("constructor", "Mercedes",       "world_titles",  8, "notable_driver", "Lewis Hamilton")
    );

    // ── Driver stats ─────────────────────────────────────────────────────────

    public static final Map<String, Map<String, Object>> DRIVER_STATS = buildDriverStats();

    private static Map<String, Map<String, Object>> buildDriverStats() {
        Map<String, Map<String, Object>> m = new LinkedHashMap<>();
        add(m, "Max Verstappen",   "Red Bull",      1,  25, 8, 12, 6, 4, 16, "Netherlands",    1);
        add(m, "Sergio Pérez",     "Red Bull",     11,  18, 2,  8, 1, 2, 16, "Mexico",         2);
        add(m, "Charles Leclerc",  "Ferrari",      16,  20, 3, 10, 5, 2, 16, "Monaco",         3);
        add(m, "Carlos Sainz",     "Ferrari",      55,  17, 2,  7, 0, 1, 16, "Spain",          4);
        add(m, "Lando Norris",     "McLaren",       4,  19, 2, 11, 1, 2, 16, "United Kingdom", 5);
        add(m, "Oscar Piastri",    "McLaren",      81,  14, 1,  6, 0, 1, 16, "Australia",      6);
        add(m, "Lewis Hamilton",   "Mercedes",     44,  12, 1,  5, 1, 1, 16, "United Kingdom", 7);
        add(m, "George Russell",   "Mercedes",     63,  11, 0,  4, 0, 1, 16, "United Kingdom", 8);
        add(m, "Fernando Alonso",  "Aston Martin", 14,  10, 0,  3, 0, 0, 16, "Spain",          9);
        add(m, "Lance Stroll",     "Aston Martin", 18,   6, 0,  1, 0, 0, 16, "Canada",        12);
        add(m, "Yuki Tsunoda",     "RB",           22,   7, 0,  2, 0, 0, 16, "Japan",         11);
        add(m, "Liam Lawson",      "RB",           30,   4, 0,  0, 0, 0, 12, "New Zealand",   14);
        add(m, "Nico Hülkenberg",  "Haas",         27,   5, 0,  1, 0, 0, 16, "Germany",       13);
        add(m, "Kevin Magnussen",  "Haas",         20,   3, 0,  0, 0, 0, 16, "Denmark",       15);
        add(m, "Valtteri Bottas",  "Sauber",       77,   2, 0,  0, 0, 0, 16, "Finland",       16);
        add(m, "Zhou Guanyu",      "Sauber",       24,   1, 0,  0, 0, 0, 16, "China",         17);
        add(m, "Alex Albon",       "Williams",     23,   3, 0,  0, 0, 0, 16, "Thailand",      15);
        add(m, "Logan Sargeant",   "Williams",      2,   0, 0,  0, 0, 0, 16, "USA",           20);
        add(m, "Esteban Ocon",     "Alpine",       31,   4, 0,  1, 0, 0, 16, "France",        13);
        add(m, "Pierre Gasly",     "Alpine",       10,   4, 0,  0, 0, 0, 16, "France",        14);
        return m;
    }

    private static void add(Map<String, Map<String, Object>> m,
                             String name, String team, int carNo, int points,
                             int wins, int podiums, int poles, int fastestLaps,
                             int starts, String nationality, int position) {
        Map<String, Object> row = new LinkedHashMap<>();
        row.put("name", name);           row.put("team", team);
        row.put("car_no", carNo);        row.put("nationality", nationality);
        row.put("position", position);   row.put("points", points);
        row.put("wins", wins);           row.put("podiums", podiums);
        row.put("poles", poles);         row.put("fastest_laps", fastestLaps);
        row.put("starts", starts);
        m.put(name.toLowerCase(), row);
    }
}
