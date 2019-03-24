package com.pdm.packaging;

import java.io.File;
import java.sql.*;

public class H2Calls {

    private Connection h2connection;

    public void createConnection(String location, String user, String password, int attempt) {
        String url = "jdbc:h2:" + location;
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException cnf) {
            System.out.println("Error: environment not set up correct, could not find H2 drivers\n ");
            cnf.printStackTrace();
        }
        try {
            h2connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connection to H2 database established");
        } catch (SQLException se) {
            File path = new File(location);
            if (path.exists()) {
                attempt++;
                if (attempt < 6) {
                    System.out.println("Failed to connect to H2 database, attempting to recover (" + attempt + ")");
                    createConnection(location, user, password, attempt);
                } else {
                    System.out.println("Failed to connect to H2 database after 5 attempts, exiting...");
                    System.exit(1);
                }
            } else {
                System.out.println("Could not find " + location + ", attempting to create...");
                try {
                    if (path.mkdirs()) {
                        System.out.println(location + " created, attempting re-connect...");
                        createConnection(location, user, password, 0);

                    }
                } catch (SecurityException e) {
                    System.out.println("Failed to create location, application does not have permission");
                    System.exit(1);
                }

            }
        }
    }

    public Connection getH2connection() {
        return h2connection;
    }

    public void closeH2connection(){
        try {
            h2connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to close connection to H2 database");
        }
    }

    public void execute(String cmd) {
        try {
            Statement stmt = h2connection.createStatement();
            stmt.execute(cmd);
        } catch (Exception e) {
            System.out.println("Error executing statement '" + cmd + "\n" + e);
        }
    }

    public ResultSet query(String cmd) {
        ResultSet results = null;
        try {
            Statement stmt = h2connection.createStatement();
            results = stmt.executeQuery(cmd);
        } catch (Exception e) {
            System.out.println("Error querying statement '" + cmd + "\n" + e);
        }
        return results;
    }

}
