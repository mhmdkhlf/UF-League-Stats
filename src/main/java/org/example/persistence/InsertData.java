package org.example.persistence;

import static org.example.Constants.LUWL_SCHEMA;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.example.models.Match;
import org.example.models.Player;
import org.example.models.Team;
import org.example.parsers.MatchesCsvParser;
import org.example.parsers.PlayersCsvParser;
import org.example.parsers.TeamsCsvParser;

public class InsertData {

   public static void main(String[] args) {
      try (H2DB h2DB = new H2DB()) {
         Connection connection = h2DB.getConnection();
         insertTeams(TeamsCsvParser.getTeams(), connection);
         insertPlayers(PlayersCsvParser.getPlayers(), connection);
         insertMatches(MatchesCsvParser.getMatches(), connection);
      }
   }

   private static void insertTeams(List<Team> teams, Connection connection) {
      teams.forEach(team -> {
         try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " + LUWL_SCHEMA + ".TEAM (name)" +
           " VALUES (?)")) {
            preparedStatement.setString(1, team.getName());
            preparedStatement.execute();
         } catch (SQLException e) {
            throw new RuntimeException("Failed to store" + team + " in DB", e);
         }
      });
   }

   private static void insertPlayers(List<Player> players, Connection connection) {
      players.forEach(player -> {
         try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " + LUWL_SCHEMA + ".PLAYER (fullname, gender, teamName)" +
           " VALUES (?, ?, ?)")) {
            preparedStatement.setString(1, player.getFullName());
            preparedStatement.setString(2, player.getGender().name());
            preparedStatement.setString(3, player.getTeam().getName());
            preparedStatement.execute();
         } catch (SQLException e) {
            throw new RuntimeException("Failed to store" + player + " in DB", e);
         }
      });
   }

   private static void insertMatches(List<Match> matches, Connection connection) {
      matches.forEach(match -> {
         try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " + LUWL_SCHEMA + ".MATCH (homeTeamName, awayTeamName, matchStatus)" +
           " VALUES (?, ?, ?)")) {
            preparedStatement.setString(1, match.getHomeTeamName());
            preparedStatement.setString(2, match.getAwayTeamName());
            preparedStatement.setString(3, match.getMatchStatus().name());
            preparedStatement.execute();
         } catch (SQLException e) {
            throw new RuntimeException("Failed to store" + match + " in DB", e);
         }
      });
   }
}
