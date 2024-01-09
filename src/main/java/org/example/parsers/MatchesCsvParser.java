package org.example.parsers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.example.models.Match;

public class MatchesCsvParser {
   private static final String MATCHES_CSV_FILE = "luwl-data/matches.csv";

   public static List<Match> getMatches() {
      List<Match> matches = new ArrayList<>();
      InputStream inputStream = MatchesCsvParser.class.getClassLoader().getResourceAsStream(MATCHES_CSV_FILE);
      if (inputStream == null) {
         throw new IllegalArgumentException(MATCHES_CSV_FILE + " is not found");
      }
      try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
         String inputLine;
         while ((inputLine = br.readLine()) != null) {
            Match match = createMatch(inputLine);
            matches.add(match);
         }
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
      return matches;
   }

   private static Match createMatch(String csvLine) {
      List<String> values = Stream.of(csvLine.split(","))
        .map(String::trim)
        .toList();
      return new Match(values.get(0), values.get(1));
   }
}
