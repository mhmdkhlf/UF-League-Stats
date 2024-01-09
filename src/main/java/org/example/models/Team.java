package org.example.models;

import static org.example.Constants.LUWL_SCHEMA;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(schema = LUWL_SCHEMA, name = "TEAM")
public class Team {
   @Id
   private String name;
   @JsonIgnore
   @OneToMany(mappedBy = "team", fetch = FetchType.EAGER)
   private List<Player> players;

   public Team() {
   }

   public Team(String name) {
      this.name = name;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public List<Player> getPlayers() {
      return players;
   }


   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      }
      if (o == null || getClass() != o.getClass()) {
         return false;
      }
      Team team = (Team) o;
      return Objects.equals(name, team.name);
   }

   @Override
   public int hashCode() {
      return Objects.hash(name);
   }

   @Override
   public String toString() {
      return "Team{" +
        "name='" + name + '\'' +
        ", players=" + players +
        '}';
   }
}
