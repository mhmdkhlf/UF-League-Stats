package org.example.models;

import static org.example.Constants.LUWL_SCHEMA;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(schema = LUWL_SCHEMA, name = "PLAYER")
public class Player {
   public enum Gender {Female, Male}

   @Id
   @Column(name = "fullName")
   private String fullName;
   @Enumerated(EnumType.STRING)
   private Gender gender;
   @JsonBackReference
   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "teamName", nullable = false)
   private Team team;
   @JsonIgnore
   @OneToMany(mappedBy = "goalScorerName", fetch = FetchType.EAGER)
   private List<Score> goalsScored;
   @JsonIgnore
   @OneToMany(mappedBy = "assistGiverName", fetch = FetchType.EAGER)
   private List<Score> assistsGiven;

   public Player() {
   }

   public Player (String fullName, Gender gender, Team team) {
      this.fullName = fullName;
      this.gender = gender;
      this.team = team;
   }

   public String getFullName() {
      return fullName;
   }

   public void setFullName(String fullName) {
      this.fullName = fullName;
   }

   public Gender getGender() {
      return gender;
   }

   public void setGender(Gender gender) {
      this.gender = gender;
   }

   public Team getTeam() {
      return team;
   }

   public void setTeam(Team team) {
      this.team = team;
   }

   public void addGoal(Score goal) {
      goalsScored.add(goal);
   }

   public int getGoals() {
      return goalsScored.size();
   }

   public void addAssist(Score assist) {
      goalsScored.add(assist);
   }

   public int getAssists() {
      return assistsGiven.size();
   }

   public int getScores() {
      return getGoals() + getAssists();
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      }
      if (o == null || getClass() != o.getClass()) {
         return false;
      }
      Player player = (Player) o;
      return Objects.equals(fullName, player.fullName) && gender == player.gender && Objects.equals(team, player.team);
   }

   @Override
   public int hashCode() {
      return Objects.hash(fullName, gender, team);
   }

   @Override
   public String toString() {
      return "Player{" +
        "fullName='" + fullName + '\'' +
        ", gender=" + gender +
        ", team=" + team +
        ", goalsScored=" + goalsScored +
        ", assistsGiven=" + assistsGiven +
        '}';
   }
}
