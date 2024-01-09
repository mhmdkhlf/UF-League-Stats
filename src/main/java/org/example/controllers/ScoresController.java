package org.example.controllers;

import static org.example.Constants.API_URI;

import java.util.Objects;

import org.example.messages.NewScore;
import org.example.models.Match;
import org.example.models.Player;
import org.example.models.Score;
import org.example.repositories.IMatchRepository;
import org.example.repositories.IPlayerRepository;
import org.example.repositories.IScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(API_URI + "scores")
public class ScoresController {
   @Autowired
   private IMatchRepository matchRepository;
   @Autowired
   private IScoreRepository scoreRepository;
   @Autowired
   private IPlayerRepository playerRepository;

   @PostMapping("")
   public ResponseEntity<Object> create(@RequestBody NewScore newScore) {
      Match match = matchRepository.getReferenceById(newScore.matchId());
      if (match.getMatchStatus() != Match.Status.Ongoing) {
         return new ResponseEntity<>("match is " + match.getMatchStatus() + " and isn't ongoing", HttpStatus.BAD_REQUEST);
      }
      Score score = Score.create(newScore.teamName(), newScore.goalScorerName(), newScore.assistGiverName(), newScore.matchId(), match.getScores().size());

      Player goalScorer = null, assistGiver = null;
      if (newScore.goalScorerName() != null) {
         goalScorer = playerRepository.getReferenceById(newScore.goalScorerName());
      }
      if (newScore.assistGiverName() != null) {
         assistGiver = playerRepository.getReferenceById(newScore.assistGiverName());
      }
      assertScoreValid(goalScorer, assistGiver, newScore.teamName());
      if (goalScorer != null) {
         goalScorer.addGoal(score);
      }
      if (assistGiver != null) {
         assistGiver.addAssist(score);
      }

      match.addScore(score);
      matchRepository.saveAndFlush(match);
      scoreRepository.saveAndFlush(score);
      return new ResponseEntity<>(score, HttpStatus.OK);
   }

   @GetMapping("/{matchId}/{index}")
   public ResponseEntity<Score> get(@PathVariable long matchId, @PathVariable int index) {
      Score score = scoreRepository.getReferenceById(new Score.PrimaryKey(matchId, index));
      return new ResponseEntity<>(score, HttpStatus.OK);
   }

   @DeleteMapping("/{matchId}/{index}")
   public ResponseEntity<String> delete(@PathVariable long matchId, @PathVariable int index) {
      Score scoreToDelete = scoreRepository.getReferenceById(new Score.PrimaryKey(matchId, index));
      Match match = matchRepository.getReferenceById(scoreToDelete.getPrimaryKey().getMatchId());
      match.removeScore(scoreToDelete);
      matchRepository.save(match);
      scoreRepository.delete(scoreToDelete);
      return new ResponseEntity<>("score deleted successfully", HttpStatus.OK);
   }

   private static void assertScoreValid(Player goalScorer, Player assistGiver, String teamName) {
      assert goalScorer == null || goalScorer.getTeam().getName().equals(teamName);
      if (assistGiver != null) {
         assert Objects.equals(goalScorer, assistGiver);
         assert goalScorer.getTeam().equals(assistGiver.getTeam());
      }
   }
}
