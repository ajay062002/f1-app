package com.example.f1app.service;

import com.example.f1app.controller.dto.ChatResponse;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Routes natural-language F1 questions to the right answer.
 * All static data lives in ChatData — this class is purely routing logic.
 */
@Service
public class ChatService {

    private static final String DEFAULT_HINT =
        "Try: \"drivers\", \"teams\", \"Ferrari stats\", \"Stats for Charles Leclerc\", " +
        "\"Who is Lando's teammate?\", \"DRS\", \"Qualifying\", \"Sprint points\", or \"Next race\".";

    public ChatResponse answer(String raw) {
        if (raw == null || raw.isBlank()) {
            return new ChatResponse("Ask about drivers, teams, stats, DRS, qualifying, sprint points, teammate queries, or next race.");
        }
        String q = raw.toLowerCase().trim();

        return firstMatch(q,
            this::tryListQuery,
            this::tryKnowledgeQuery,
            this::tryTeamStats,
            this::tryDriverStats,
            this::tryTeammateQuery,
            this::tryTeamDriversQuery
        ).orElse(new ChatResponse(DEFAULT_HINT));
    }

    // ── Matchers ─────────────────────────────────────────────────────────────

    private Optional<ChatResponse> tryListQuery(String q) {
        if (q.equals("drivers") || q.contains("all drivers")) {
            List<String> drivers = ChatData.TEAM_TO_DRIVERS.values().stream()
                .flatMap(List::stream).sorted().toList();
            return Optional.of(new ChatResponse("There are 20 drivers (2 per team).",
                Map.of("count", drivers.size()), drivers));
        }
        if (q.equals("teams") || q.contains("all teams")) {
            return Optional.of(new ChatResponse("Here are the 10 teams on the current grid.",
                Map.of("count", ChatData.TEAMS.size()), ChatData.TEAMS));
        }
        return Optional.empty();
    }

    private Optional<ChatResponse> tryKnowledgeQuery(String q) {
        if (q.contains("sprint points"))
            return Optional.of(new ChatResponse("Sprint points: 8-7-6-5-4-3-2-1 for P1-P8. Separate from Grand Prix points."));
        if (q.contains("drs"))
            return Optional.of(new ChatResponse("DRS opens the rear wing in set zones when within 1s of a car ahead, reducing drag to aid overtaking."));
        if (q.contains("qualifying") || q.equals("quali"))
            return Optional.of(new ChatResponse("Qualifying: Q1 (20->15), Q2 (15->10), Q3 (top 10). Slowest five eliminated each session; fastest in Q3 takes pole."));
        if (q.contains("next race"))
            return Optional.of(new ChatResponse("Next race info is not wired to a live calendar yet. Connect to your Races table/API to return date, circuit, sessions."));
        return Optional.empty();
    }

    private Optional<ChatResponse> tryTeamStats(String q) {
        if (!q.contains("stats")) return Optional.empty();
        return ChatData.TEAM_STATS.keySet().stream()
            .filter(q::contains).findFirst()
            .map(team -> new ChatResponse("Stats for " + titleCase(team) + ": " + ChatData.TEAM_STATS.get(team),
                Map.of("team", titleCase(team)), null));
    }

    private Optional<ChatResponse> tryDriverStats(String q) {
        return extractDriverName(q).map(name -> {
            Map<String, Object> s = ChatData.DRIVER_STATS.get(name.toLowerCase());
            if (s == null) return new ChatResponse("Couldn't find that driver. Try full names like \"Max Verstappen\" or \"Lando Norris\".");
            String summary = String.format(
                "%s (#%d) - %s - Position %d, %d pts | Wins: %d | Podiums: %d | Poles: %d | Fastest laps: %d | Starts: %d",
                s.get("name"), s.get("car_no"), s.get("team"), s.get("position"), s.get("points"),
                s.get("wins"), s.get("podiums"), s.get("poles"), s.get("fastest_laps"), s.get("starts")
            );
            return new ChatResponse(summary, Map.of("type", "driver_stats"), List.of(s));
        });
    }

    private Optional<ChatResponse> tryTeammateQuery(String q) {
        if (!q.contains("teammate")) return Optional.empty();
        String hit = ChatData.TEAM_TO_DRIVERS.values().stream().flatMap(List::stream)
            .filter(n -> q.contains(n.toLowerCase())).findFirst().orElse(null);
        if (hit == null) return Optional.of(new ChatResponse("Ask like: \"Who is Lando Norris's teammate?\""));
        String teammate = ChatData.TEAM_TO_DRIVERS.values().stream()
            .filter(list -> list.contains(hit)).findFirst().get()
            .stream().filter(n -> !n.equals(hit)).findFirst().orElse("Unknown");
        return Optional.of(new ChatResponse(hit + "'s teammate is " + teammate + "."));
    }

    private Optional<ChatResponse> tryTeamDriversQuery(String q) {
        if (!q.contains("driver")) return Optional.empty();
        return ChatData.TEAM_TO_DRIVERS.keySet().stream().filter(q::contains).findFirst()
            .map(team -> new ChatResponse(
                titleCase(team) + " drivers: " + String.join(", ", ChatData.TEAM_TO_DRIVERS.get(team)),
                Map.of("team", titleCase(team)), ChatData.TEAM_TO_DRIVERS.get(team)));
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    @SafeVarargs
    private Optional<ChatResponse> firstMatch(String q, Function<String, Optional<ChatResponse>>... matchers) {
        for (Function<String, Optional<ChatResponse>> m : matchers) {
            Optional<ChatResponse> r = m.apply(q);
            if (r.isPresent()) return r;
        }
        return Optional.empty();
    }

    private Optional<String> extractDriverName(String q) {
        String r1 = q.replaceAll(".*(?:stats for|about|tell me about)\\s+([a-z\\s.'-]+)$", "$1").trim();
        if (!r1.equals(q)) return Optional.of(titleCase(r1));
        String r2 = q.replaceAll("^([a-z\\s.'-]+)\\s+stats.*$", "$1").trim();
        if (!r2.equals(q)) return Optional.of(titleCase(r2));
        return ChatData.DRIVER_STATS.keySet().stream().filter(q::contains).findFirst().map(this::titleCase);
    }

    private String titleCase(String s) {
        return Arrays.stream(s.split("\\s+"))
            .map(w -> w.isEmpty() ? w : Character.toUpperCase(w.charAt(0)) + w.substring(1))
            .collect(Collectors.joining(" "));
    }
}
