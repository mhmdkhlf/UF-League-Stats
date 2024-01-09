package org.example.persistence;

import static org.example.Constants.LUWL_SCHEMA;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ClearData {
   public static void main(String[] args) {
      try (H2DB h2DB = new H2DB()) {
         Connection connection = h2DB.getConnection();
         final String[] tableNames = { "SCORE", "MATCH", "PLAYER", "TEAM" };
         try (Statement statement = connection.createStatement()) {
            for (String tableName : tableNames) {
               //noinspection SqlWithoutWhere
               statement.executeUpdate("DELETE FROM " + LUWL_SCHEMA + "." + tableName);
            }
         } catch (SQLException e) {
            throw new RuntimeException("Failed to delete everything from all database tables", e);
         }
      }
   }

}
