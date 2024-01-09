package org.example.controllers;

import static org.example.Constants.API_URI;

import java.util.Comparator;
import java.util.List;

import org.example.messages.PlayerAssists;
import org.example.messages.PlayerGoals;
import org.example.messages.PlayerScores;
import org.example.messages.PlayerStats;
import org.example.models.Player;
import org.example.repositories.IPlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(API_URI + "players")
public class PlayersController {
   @Autowired
   private IPlayerRepository playerRepository;

   @GetMapping("")
   public List<PlayerStats> list() {
      return playerRepository.findAll().stream()
        .map(PlayerStats::new)
        .toList();
   }

   @GetMapping("/{playerName}")
   public PlayerStats get(@PathVariable String playerName) {
      Player player = playerRepository.getReferenceById(playerName);
      return new PlayerStats(player);
   }

   @GetMapping("/goals-leaderboard")
   public List<PlayerGoals> getGoalsLeaderBoard() {
      return playerRepository.findAll().stream()
        .filter(player -> player.getGoals() != 0)
        .map(player -> new PlayerGoals(player.getFullName(), player.getGender(), player.getGoals()))
        .sorted(Comparator.comparingInt(PlayerGoals::goals).reversed())
        .toList();
   }

   @GetMapping("/assists-leaderboard")
   public List<PlayerAssists> getAssistsLeaderBoard() {
      return playerRepository.findAll().stream()
        .filter(player -> player.getAssists() != 0)
        .map(player -> new PlayerAssists(player.getFullName(), player.getGender(), player.getAssists()))
        .sorted(Comparator.comparingInt(PlayerAssists::assists).reversed())
        .toList();
   }

   @GetMapping("/scores-leaderboard")
   public List<PlayerScores> getScoresLeaderBoard() {
      return playerRepository.findAll().stream()
        .filter(player -> player.getScores() != 0)
        .map(player -> new PlayerScores(player.getFullName(), player.getGender(), player.getScores()))
        .sorted(Comparator.comparingInt(PlayerScores::scores).reversed())
        .toList();
   }

}
