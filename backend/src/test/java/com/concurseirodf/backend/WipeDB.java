package com.concurseirodf.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class WipeDB {
    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/concurseiro_db", "concurseiro", "password");
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM flyway_schema_history WHERE version='6'");
            stmt.execute("DROP TABLE IF EXISTS tb_password_reset_token CASCADE");
            System.out.println("Cleaned V6 from Flyway!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
