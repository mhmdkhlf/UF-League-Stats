package org.example.parsers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.example.models.Player;
import org.example.models.Team;

public class PlayersCsvParser {
   private static final String PLAYERS_CSV_FILE = "luwl-data/players.csv";

   public static List<Player> getPlayers() {
      List<Player> players = new ArrayList<>();
      InputStream inputStream = PlayersCsvParser.class.getClassLoader().getResourceAsStream(PLAYERS_CSV_FILE);
      if (inputStream == null) {
         throw new IllegalArgumentException(PLAYERS_CSV_FILE + " is not found");
      }
      try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
         String csvLine;
         while ((csvLine = br.readLine()) != null) {
            Player player = createPlayer(csvLine);
            players.add(player);
         }
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
      return players;
   }

   private static Player createPlayer(String csvLine) {
      List<String> values = Stream.of(csvLine.split(","))
        .map(String::trim)
        .toList();
      return new Player(values.get(0), Player.Gender.valueOf(values.get(1)), new Team(values.get(2)));
   }
}
