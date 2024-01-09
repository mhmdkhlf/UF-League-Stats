package org.example.models;

import static org.example.Constants.LUWL_SCHEMA;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(schema = LUWL_SCHEMA, name = "SCORE")
public class Score {
   @EmbeddedId
   private PrimaryKey primaryKey;
   @Column(name = "teamName", nullable = false)
   private String teamName;
   @Column(name = "goalScorerName")
   private String goalScorerName;
   @Column(name = "assistGiverName")
   private String assistGiverName;

   public Score() {
   }

   public static Score create(String teamName, String goalScorerName, String assistGiverName, long matchId, int index) {
      Score score = new Score();
      score.setTeamName(teamName);
      score.setGoalScorerName(goalScorerName);
      score.setAssistGiverName(assistGiverName);
      PrimaryKey primaryKey = new PrimaryKey();
      primaryKey.setMatchId(matchId);
      primaryKey.setIndex(index);
      score.setPrimaryKey(primaryKey);
      return score;
   }

   public PrimaryKey getPrimaryKey() {
      return primaryKey;
   }

   public void setPrimaryKey(PrimaryKey primaryKey) {
      this.primaryKey = primaryKey;
   }

   public String getTeamName() {
      return teamName;
   }

   public void setTeamName(String teamName) {
      this.teamName = teamName;
   }

   public String getGoalScorerName() {
      return goalScorerName;
   }

   public void setGoalScorerName(String goalScorerName) {
      this.goalScorerName = goalScorerName;
   }

   public String getAssistGiverName() {
      return assistGiverName;
   }

   public void setAssistGiverName(String assistGiverName) {
      this.assistGiverName = assistGiverName;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      }
      if (o == null || getClass() != o.getClass()) {
         return false;
      }
      Score score = (Score) o;
      return primaryKey == score.primaryKey;
   }

   @Override
   public int hashCode() {
      return Objects.hash(primaryKey);
   }

   public static class PrimaryKey implements Serializable, Comparable<PrimaryKey> {
      private long matchId;
      private Integer index;

      public PrimaryKey() {
      }

      public PrimaryKey(long matchId, int index) {
         this.matchId = matchId;
         this.index = index;
      }

      public long getMatchId() {
         return matchId;
      }

      public void setMatchId(long matchId) {
         this.matchId = matchId;
      }

      public int getIndex() {
         return index;
      }

      public void setIndex(int index) {
         this.index = index;
      }

      @Override
      public boolean equals(Object o) {
         if (this == o) {
            return true;
         }
         if (o == null || getClass() != o.getClass()) {
            return false;
         }
         PrimaryKey that = (PrimaryKey) o;
         return matchId == that.matchId && index == that.index;
      }

      @Override
      public int hashCode() {
         return Objects.hash(matchId, index);
      }

      @Override
      public int compareTo(PrimaryKey o) {
         return index.compareTo(o.index);
      }

      @Override
      public String toString() {
         return "PrimaryKey{" +
           "matchId=" + matchId +
           ", index=" + index +
           '}';
      }
   }
}
