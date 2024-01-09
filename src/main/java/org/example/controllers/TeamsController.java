package org.example.controllers;

import static org.example.Constants.API_URI;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.example.messages.MatchResult;
import org.example.messages.TeamStanding;
import org.example.models.Match;
import org.example.models.Player;
import org.example.models.Team;
import org.example.repositories.IMatchRepository;
import org.example.repositories.ITeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(API_URI + "teams")
public class TeamsController {
   @Autowired
   private ITeamRepository teamRepository;
   @Autowired
   private IMatchRepository matchRepository;

   @GetMapping()
   public List<Team> list() {
      return teamRepository.findAll();
   }

   @GetMapping("/{name}/players")
   public List<Player> getPlayers(@PathVariable String name) {
      return teamRepository.getReferenceById(name).getPlayers();
   }

   @GetMapping("/{name}/matches")
   public List<Match> getMatches(@PathVariable String name) {
      Team team = teamRepository.getReferenceById(name);
      return matchRepository.findAllByHomeTeamNameOrAwayTeamName(team.getName(), team.getName());
   }

   // Standings are computed from scratch everytime they're requested.
   // A better solution for later is to store the standings in the DB, and update each teams' standings when matches are completed.
   @GetMapping("/standings")
   public List<TeamStanding> getStandings() {
      return teamRepository.findAll().stream()
        .map(this::createTeamStanding)
        .sorted(Comparator.comparingInt(TeamStanding::points).thenComparing(TeamStanding::goalDifference).reversed())
        .toList();
   }

   private TeamStanding createTeamStanding(Team team) {
      String teamName = team.getName();
      AtomicInteger wins = new AtomicInteger();
      AtomicInteger draws = new AtomicInteger();
      AtomicInteger losses = new AtomicInteger();
      AtomicInteger goalsFor = new AtomicInteger();
      AtomicInteger goalsAgainst = new AtomicInteger();
      List<MatchResult> homeResults = getDoneMatchesResults(matchRepository.findAllByHomeTeamName(teamName));
      homeResults.forEach(homeMatchResult -> updateTeamStandingTotals(homeMatchResult, true, wins, draws, losses, goalsFor, goalsAgainst));
      List<MatchResult> awayResults = getDoneMatchesResults(matchRepository.findAllByAwayTeamName(teamName));
      awayResults.forEach(awayMatchResult -> updateTeamStandingTotals(awayMatchResult, false, wins, draws, losses, goalsFor, goalsAgainst));
      return new TeamStanding(teamName, wins.get(), draws.get(), losses.get(), goalsFor.get(), goalsAgainst.get(), goalsFor.get() - goalsAgainst.get(), wins.get() * 3 + draws.get());
   }

   private static void updateTeamStandingTotals(MatchResult matchResult, boolean isHomeMatch, AtomicInteger wins, AtomicInteger draws, AtomicInteger losses, AtomicInteger goalsFor, AtomicInteger goalsAgainst) {
      if (isHomeMatch) {
         goalsFor.addAndGet(matchResult.getHomeTeamScore());
         goalsAgainst.addAndGet(matchResult.getAwayTeamScore());
         if (matchResult.getHomeTeamScore() > matchResult.getAwayTeamScore()) {
            wins.incrementAndGet();
         } else if (matchResult.getHomeTeamScore() < matchResult.getAwayTeamScore()) {
            losses.incrementAndGet();
         } else {
            draws.incrementAndGet();
         }
      } else {
         goalsFor.addAndGet(matchResult.getAwayTeamScore());
         goalsAgainst.addAndGet(matchResult.getHomeTeamScore());
         if (matchResult.getHomeTeamScore() < matchResult.getAwayTeamScore()) {
            wins.incrementAndGet();
         } else if (matchResult.getHomeTeamScore() > matchResult.getAwayTeamScore()) {
            losses.incrementAndGet();
         } else {
            draws.incrementAndGet();
         }
      }
   }

   private static List<MatchResult> getDoneMatchesResults(List<Match> matches) {
      return matches.stream()
        .filter(match -> match.getMatchStatus() == Match.Status.Done)
        .map(MatchResult::create)
        .toList();
   }
}
