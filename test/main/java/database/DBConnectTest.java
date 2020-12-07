package main.java.database;

import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class DBConnectTest {

    @Test
    void getConnection() {
        Connection connection = DBConnect.getConnection();
        assertNotNull(connection);
        Connection connection2 = DBConnect.getConnection();
        assertSame(connection, connection2);
    }
}