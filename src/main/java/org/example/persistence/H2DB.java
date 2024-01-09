package org.example.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2DB implements AutoCloseable {
   private final Connection connection;

   public H2DB() {
      try {
         connection = DriverManager.getConnection("jdbc:h2:~/test", "mhmdkhlf", "password");
      } catch (SQLException e) {
         throw new RuntimeException("Failed to connect to H2DB", e);
      }
   }

   public Connection getConnection() {
      return connection;
   }

   @Override
   public void close() {
      try {
         connection.close();
      } catch (SQLException e) {
         throw new RuntimeException("Failed to close database connection", e);
      }
   }
}
