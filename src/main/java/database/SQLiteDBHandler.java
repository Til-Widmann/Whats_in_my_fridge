package main.java.database;

import java.sql.*;

/**
 * @author Til-W
 * @version 1.0
 *
 * The Database is Specified in the DBConnect class.
 */

public class SQLiteDBHandler {
    protected static Connection c;
    protected static PreparedStatement prepStm;

    /**
     * Closes the current Connection to the database.
     */
     protected static void close(){
        try {
            c.close();
        } catch (Exception e) {
            System.out.println("Error at close: " + e.getMessage());
        }
    }
}
