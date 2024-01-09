package org.example.models;

import static org.example.Constants.LUWL_SCHEMA;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;

@Entity
@Table(schema = LUWL_SCHEMA, name = "MATCH")
public class Match {
   public enum Status {Upcoming, Ongoing, Done}

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;
   @Column(name = "homeTeamName", nullable = false)
   private String homeTeamName;
   @Column(name = "awayTeamName", nullable = false)
   private String awayTeamName;
   @Enumerated(EnumType.STRING)
   private Status matchStatus;
   @JsonIgnore
   @OneToMany(mappedBy = "primaryKey.matchId", fetch = FetchType.EAGER)
   @OrderBy("primaryKey ASC")
   private Set<Score> scores;

   public Match() {
      this.matchStatus = Status.Upcoming;
   }

   public Match(String homeTeamName, String awayTeamName) {
      this();
      this.homeTeamName = homeTeamName;
      this.awayTeamName = awayTeamName;
   }

   public long getId() {
      return id;
   }

   public void setId(long id) {
      this.id = id;
   }

   public String getHomeTeamName() {
      return homeTeamName;
   }

   public void setHomeTeamName(String homeTeamName) {
      this.homeTeamName = homeTeamName;
   }

   public String getAwayTeamName() {
      return awayTeamName;
   }

   public void setAwayTeamName(String awayTeamName) {
      this.awayTeamName = awayTeamName;
   }

   public Status getMatchStatus() {
      return matchStatus;
   }

   public void setMatchStatus(Status matchStatus) {
      this.matchStatus = matchStatus;
   }

   public Set<Score> getScores() {
      return scores;
   }

   public void addScore(Score score) {
      scores.add(score);
   }

   public void removeScore(Score score) {
      scores.remove(score);
   }

   @Override
   public String toString() {
      return "Match{" +
        "id=" + id +
        ", homeTeamName=" + homeTeamName +
        ", awayTeamName=" + awayTeamName +
        ", matchStatus=" + matchStatus +
        ", scores=" + scores +
        '}';
   }
}

