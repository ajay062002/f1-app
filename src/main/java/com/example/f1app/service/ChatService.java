package com.example.f1app.service;

import com.example.f1app.controller.dto.ChatResponse;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChatService {

    // ===== Teams -> Drivers =====
    private static final Map<String, List<String>> TEAM_TO_DRIVERS = Map.ofEntries(
        Map.entry("ferrari", List.of("Charles Leclerc", "Carlos Sainz")),
        Map.entry("red bull", List.of("Max Verstappen", "Sergio Pérez")),
        Map.entry("mercedes", List.of("Lewis Hamilton", "George Russell")),
        Map.entry("mclaren", List.of("Lando Norris", "Oscar Piastri")),
        Map.entry("aston martin", List.of("Fernando Alonso", "Lance Stroll")),
        Map.entry("rb", List.of("Yuki Tsunoda", "Liam Lawson")),
        Map.entry("haas", List.of("Nico Hülkenberg", "Kevin Magnussen")),
        Map.entry("sauber", List.of("Valtteri Bottas", "Zhou Guanyu")),
        Map.entry("williams", List.of("Alex Albon", "Logan Sargeant")),
        Map.entry("alpine", List.of("Esteban Ocon", "Pierre Gasly"))
    );

    // ===== Team stats (sample) =====
    private static final Map<String, Map<String,Object>> TEAM_STATS = Map.of(
        "ferrari", Map.of("constructor","Ferrari","world_titles",16,"notable_driver","Michael Schumacher"),
        "red bull", Map.of("constructor","Red Bull Racing","world_titles",6,"notable_driver","Max Verstappen"),
        "mercedes", Map.of("constructor","Mercedes","world_titles",8,"notable_driver","Lewis Hamilton")
    );

    private static final List<String> TEAMS = TEAM_TO_DRIVERS.keySet().stream()
        .sorted().collect(Collectors.toList());

    // ===== Current-season driver stats (placeholders) =====
    private static final Map<String, Map<String,Object>> DRIVER_STATS = buildDriverStats();

    private static Map<String, Map<String,Object>> buildDriverStats() {
        Map<String, Map<String,Object>> m = new LinkedHashMap<>();
        put(m,"Max Verstappen",   "Red Bull",   1, 25,  8, 12,  6,  4, 16, "Netherlands", 1);
        put(m,"Sergio Pérez",     "Red Bull",  11, 18,  2,  8,  1,  2, 16, "Mexico",       2);
        put(m,"Charles Leclerc",  "Ferrari",   16, 20,  3, 10,  5,  2, 16, "Monaco",       3);
        put(m,"Carlos Sainz",     "Ferrari",   55, 17,  2,  7,  0,  1, 16, "Spain",        4);
        put(m,"Lando Norris",     "McLaren",    4, 19,  2, 11,  1,  2, 16, "United Kingdom",5);
        put(m,"Oscar Piastri",    "McLaren",   81, 14,  1,  6,  0,  1, 16, "Australia",    6);
        put(m,"Lewis Hamilton",   "Mercedes",  44, 12,  1,  5,  1,  1, 16, "United Kingdom",7);
        put(m,"George Russell",   "Mercedes",  63, 11,  0,  4,  0,  1, 16, "United Kingdom",8);
        put(m,"Fernando Alonso",  "Aston Martin",14,10, 0,  3,  0,  0, 16, "Spain",        9);
        put(m,"Lance Stroll",     "Aston Martin",18, 6, 0,  1,  0,  0, 16, "Canada",      12);
        put(m,"Yuki Tsunoda",     "RB",         22, 7,  0,  2,  0,  0, 16, "Japan",       11);
        put(m,"Liam Lawson",      "RB",         30, 4,  0,  0,  0,  0, 12, "New Zealand", 14);
        put(m,"Nico Hülkenberg",  "Haas",       27, 5,  0,  1,  0,  0, 16, "Germany",     13);
        put(m,"Kevin Magnussen",  "Haas",       20, 3,  0,  0,  0,  0, 16, "Denmark",     15);
        put(m,"Valtteri Bottas",  "Sauber",     77, 2,  0,  0,  0,  0, 16, "Finland",     16);
        put(m,"Zhou Guanyu",      "Sauber",     24, 1,  0,  0,  0,  0, 16, "China",       17);
        put(m,"Alex Albon",       "Williams",   23, 3,  0,  0,  0,  0, 16, "Thailand",    15);
        put(m,"Logan Sargeant",   "Williams",    2, 0,  0,  0,  0,  0, 16, "USA",         20);
        put(m,"Esteban Ocon",     "Alpine",     31, 4,  0,  1,  0,  0, 16, "France",      13);
        put(m,"Pierre Gasly",     "Alpine",     10, 4,  0,  0,  0,  0, 16, "France",      14);
        return m;
    }

    // IMPORTANT: avoid Map.of(...) with >10 pairs
    private static void put(Map<String, Map<String,Object>> m, String name, String team, int carNo,
                            int points, int wins, int podiums, int poles, int fastestLaps, int starts,
                            String nationality, int position) {
        Map<String,Object> row = new LinkedHashMap<>();
        row.put("name", name);
        row.put("team", team);
        row.put("car_no", carNo);
        row.put("nationality", nationality);
        row.put("position", position);
        row.put("points", points);
        row.put("wins", wins);
        row.put("podiums", podiums);
        row.put("poles", poles);
        row.put("fastest_laps", fastestLaps);
        row.put("starts", starts);
        m.put(name.toLowerCase(), row);
    }

    public ChatResponse answer(String raw) {
        if (raw == null || raw.isBlank()) {
            return new ChatResponse("Ask about drivers, teams, Ferrari stats, DRS, qualifying, sprint points, teammate queries, or next race.");
        }
        String q = raw.toLowerCase().trim();

        // Lists
        if (q.equals("drivers") || q.contains("all drivers")) {
            List<String> drivers = TEAM_TO_DRIVERS.values().stream().flatMap(List::stream).sorted().toList();
            return new ChatResponse("There are 20 drivers (2 per team).", Map.of("count", drivers.size()), drivers);
        }
        if (q.equals("teams") || q.contains("all teams")) {
            return new ChatResponse("Here are the 10 teams on the current grid.", Map.of("count", TEAMS.size()), TEAMS);
        }

        // Knowledge
        if (q.contains("sprint points")) return new ChatResponse("Sprint points: 8-7-6-5-4-3-2-1 for P1–P8. Separate from Grand Prix points.");
        if (q.contains("drs")) return new ChatResponse("DRS opens the rear wing in set zones when within 1s of a car ahead, reducing drag to aid overtaking.");
        if (q.contains("qualifying") || q.equals("quali") || q.contains("qualifying rules"))
            return new ChatResponse("Qualifying: Q1 (20→15), Q2 (15→10), Q3 (top 10). Slowest five out each session; fastest in Q3 takes pole.");
        if (q.contains("next race"))
            return new ChatResponse("Next race info isn’t wired to a live calendar yet. Connect to your Races table/API to return date, circuit, sessions.");

        // Team stats
        if (q.contains("stats") && TEAM_STATS.keySet().stream().anyMatch(q::contains)) {
            String team = TEAM_STATS.keySet().stream().filter(q::contains).findFirst().orElse("");
            Map<String,Object> stats = TEAM_STATS.getOrDefault(team, Map.of("note","Basic stats not set yet"));
            return new ChatResponse("Stats for " + title(team) + ": " + stats, Map.of("team", title(team)), null);
        }

        // Driver stats
        Optional<String> name = extractDriverName(q);
        if (name.isPresent()) {
            Map<String,Object> s = DRIVER_STATS.get(name.get().toLowerCase());
            if (s != null) {
                String pretty = String.format(
                    "%s (#%d) • %s • Position %d, %d pts\nWins: %d | Podiums: %d | Poles: %d | Fastest laps: %d | Starts: %d",
                    s.get("name"), s.get("car_no"), s.get("team"), s.get("position"), s.get("points"),
                    s.get("wins"), s.get("podiums"), s.get("poles"), s.get("fastest_laps"), s.get("starts")
                );
                return new ChatResponse(pretty, Map.of("type","driver_stats"), List.of(s));
            } else {
                return new ChatResponse("I couldn’t find that driver. Try full names like “Max Verstappen”, “Charles Leclerc”, or “Lando Norris”.");
            }
        }

        // Teammate
        if (q.contains("teammate")) {
            String hit = TEAM_TO_DRIVERS.values().stream().flatMap(List::stream)
                .filter(n -> q.contains(n.toLowerCase())).findFirst().orElse(null);
            if (hit != null) {
                String teammate = TEAM_TO_DRIVERS.entrySet().stream()
                    .filter(e -> e.getValue().contains(hit)).map(Map.Entry::getValue)
                    .findFirst().get().stream().filter(n -> !n.equals(hit)).findFirst().orElse("Unknown");
                return new ChatResponse(hit + "’s teammate is " + teammate + ".");
            }
            return new ChatResponse("Ask like: “Who is Lando Norris’s teammate?”");
        }

        // "<team> drivers"
        Optional<String> teamHit = TEAM_TO_DRIVERS.keySet().stream().filter(q::contains).findFirst();
        if (teamHit.isPresent() && q.contains("driver")) {
            List<String> drivers = TEAM_TO_DRIVERS.get(teamHit.get());
            return new ChatResponse(title(teamHit.get()) + " drivers: " + String.join(", ", drivers),
                Map.of("team", title(teamHit.get())), drivers);
        }

        return new ChatResponse("Try: “drivers”, “teams”, “Ferrari stats”, “Stats for Charles Leclerc”, “Who is Lando’s teammate?”, “DRS”, “Qualifying”, “Sprint points”, or “Next race”.");
    }

    // --- helpers ---
    private String title(String s){ return s.isEmpty()? s : Character.toUpperCase(s.charAt(0)) + s.substring(1); }

    private Optional<String> extractDriverName(String q) {
        String r1 = q.replaceAll(".*(?:stats for|about|tell me about)\\s+([a-z\\s\\.'-]+)$", "$1").trim();
        if (!r1.equals(q)) return Optional.of(cap(r1));
        String r2 = q.replaceAll("^([a-z\\s\\.'-]+)\\s+stats.*$", "$1").trim();
        if (!r2.equals(q)) return Optional.of(cap(r2));
        for (String full : DRIVER_STATS.keySet()) if (q.contains(full)) return Optional.of(cap(full));
        return Optional.empty();
    }
    private String cap(String s){ return Arrays.stream(s.split("\\s+")).map(x->x.isEmpty()?x:Character.toUpperCase(x.charAt(0))+x.substring(1)).collect(Collectors.joining(" ")); }
}
