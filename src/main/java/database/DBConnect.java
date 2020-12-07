package main.java.database;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author Til-W
 * @version 1.0
 *
 *
 */
 class DBConnect {
    private static Connection c = null;

     static Connection getConnection()  {
        try {
            if (c == null)
            Class.forName("org.sqlite.JDBC");

            c = DriverManager
                    .getConnection( "jdbc:sqlite:database.db");

            return c;

        } catch (Exception e) {
            System.out.println("Not connected\n" +
                    "Error: " + e.getMessage());
            return null;
        }
    }
}
