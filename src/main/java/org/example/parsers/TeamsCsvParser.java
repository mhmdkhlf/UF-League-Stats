package org.example.parsers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.example.models.Team;

public class TeamsCsvParser {
   private static final String TEAMS_CSV_FILE = "luwl-data/teams.csv";

   public static List<Team> getTeams() {
      List<Team> teams = new ArrayList<>();
      InputStream inputStream = TeamsCsvParser.class.getClassLoader().getResourceAsStream(TEAMS_CSV_FILE);
      if (inputStream == null) {
         throw new IllegalArgumentException(TEAMS_CSV_FILE + " is not found");
      }
      try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
         String teamName;
         while ((teamName = br.readLine()) != null) {
            Team team = new Team(teamName);
            teams.add(team);
         }
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
      return teams;
   }
}
