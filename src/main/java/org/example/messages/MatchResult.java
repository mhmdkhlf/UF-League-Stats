package org.example.messages;

import org.example.models.Match;
import org.example.models.Score;

public class MatchResult {
   private final String homeTeamName;
   private final String awayTeamName;
   private final int homeTeamScore;
   private final int awayTeamScore;

   public static MatchResult create(Match match) {
      String homeTeamName = match.getHomeTeamName();
      String awayTeamName = match.getAwayTeamName();
      int homeTeamScore = 0, awayTeamScore = 0;
      for (Score score : match.getScores()) {
         if (score.getTeamName().equals(homeTeamName)) {
            homeTeamScore++;
         } else {
            awayTeamScore++;
         }
      }
      return new MatchResult(homeTeamName, awayTeamName, homeTeamScore, awayTeamScore);
   }

   private MatchResult(String homeTeamName, String awayTeamName, int homeTeamScore, int awayTeamScore) {
      this.homeTeamName = homeTeamName;
      this.awayTeamName = awayTeamName;
      this.homeTeamScore = homeTeamScore;
      this.awayTeamScore = awayTeamScore;
   }

   public String getHomeTeamName() {
      return homeTeamName;
   }

   public String getAwayTeamName() {
      return awayTeamName;
   }

   public int getHomeTeamScore() {
      return homeTeamScore;
   }

   public int getAwayTeamScore() {
      return awayTeamScore;
   }

   @Override
   public String toString() {
      return "MatchResult{" +
        "homeTeamName='" + homeTeamName + '\'' +
        ", awayTeamName='" + awayTeamName + '\'' +
        ", homeTeamScore=" + homeTeamScore +
        ", awayTeamScore=" + awayTeamScore +
        '}';
   }
}
