package org.example.messages;

import org.example.models.Player;

public class PlayerStats {
   private final String fullName;
   private final Player.Gender gender;
   private final String teamName;
   private final int goals;
   private final int assists;
   private final int scores;

   public PlayerStats(Player player) {
      this(player.getFullName(), player.getGender(), player.getTeam().getName(), player.getGoals(), player.getAssists());
   }

   private PlayerStats(String fullName, Player.Gender gender, String teamName, int goals, int assists) {
      this.fullName = fullName;
      this.gender = gender;
      this.teamName = teamName;
      this.goals = goals;
      this.assists = assists;
      this.scores = goals + assists;
   }

   public String getFullName() {
      return fullName;
   }

   public Player.Gender getGender() {
      return gender;
   }

   public String getTeamName() {
      return teamName;
   }

   public int getGoals() {
      return goals;
   }

   public int getAssists() {
      return assists;
   }

   public int getScores() {
      return scores;
   }

   @Override
   public String toString() {
      return "PlayerStats{" +
        "fullName='" + fullName + '\'' +
        ", teamName='" + teamName + '\'' +
        ", goals=" + goals +
        ", assists=" + assists +
        ", scores=" + scores +
        '}';
   }
}
