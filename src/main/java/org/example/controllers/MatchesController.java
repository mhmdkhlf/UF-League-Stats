package org.example.controllers;

import static org.example.Constants.API_URI;

import java.util.List;
import java.util.Set;

import org.example.messages.MatchResult;
import org.example.messages.NewMatch;
import org.example.models.Match;
import org.example.models.Score;
import org.example.repositories.IMatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(API_URI + "matches")
public class MatchesController {
   @Autowired
   private IMatchRepository matchRepository;

   @GetMapping("")
   public List<Match> list() {
      return matchRepository.findAll();
   }

   @GetMapping("/{matchId}")
   public Match get(@PathVariable long matchId) {
      return matchRepository.getReferenceById(matchId);
   }

   @GetMapping("/{matchId}/scores")
   public Set<Score> getScores(@PathVariable long matchId) {
      return matchRepository.getReferenceById(matchId).getScores();
   }

   @GetMapping("/{matchId}/result")
   public MatchResult getResult(@PathVariable long matchId) {
      Match match = matchRepository.getReferenceById(matchId);
      return MatchResult.create(match);
   }

   @GetMapping("/results")
   public List<MatchResult> getResults() {
      List<Match> matches = matchRepository.findAll();
      return matches.stream()
        .map(MatchResult::create)
        .toList();
   }

   @PostMapping("")
   public ResponseEntity<Match> create(@RequestBody NewMatch newMatch) {
      Match match = new Match(newMatch.homeTeamName(), newMatch.awayTeamName());
      matchRepository.saveAndFlush(match);
      return new ResponseEntity<>(match, HttpStatus.OK);
   }

   @DeleteMapping("/{matchId}")
   public ResponseEntity<String> delete(@PathVariable long matchId) {
      matchRepository.deleteById(matchId);
      return new ResponseEntity<>("deleted match successfully", HttpStatus.OK);
   }

   @PutMapping("/{matchId}/start")
   public ResponseEntity<Match> startMatch(@PathVariable long matchId) {
      Match match = matchRepository.getReferenceById(matchId);
      match.setMatchStatus(Match.Status.Ongoing);
      matchRepository.saveAndFlush(match);
      return new ResponseEntity<>(match, HttpStatus.OK);
   }

   @PutMapping("/{matchId}/done")
   public ResponseEntity<Match> completeMatch(@PathVariable long matchId) {
      Match match = matchRepository.getReferenceById(matchId);
      match.setMatchStatus(Match.Status.Done);
      matchRepository.saveAndFlush(match);
      return new ResponseEntity<>(match, HttpStatus.OK);
   }
}
