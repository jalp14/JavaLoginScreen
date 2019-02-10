package MainWindow;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import org.h2.tools.Server;

public class SQLShenanigans {

    // JDBC Drivers
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:tcp://localhost/~/test";

    // DB Creds
    static final String user = "root";
    static final String pass = "alpine";
    
    // DB Connection
    Connection dbConnection;
    Statement dbStatement;

    // Query Result
    ResultSet result;

    SQLShenanigans() {
        init();
    }

    public void init() {
      startSQLServer();
    }

    public void startSQLServer() {
        try {
            Server server1 = Server.createTcpServer().start();
            System.out.println("Starting SQL Server...");
            System.out.println(server1.getStatus());
            Server server2 = Server.createWebServer("-webPort", "8082", "-tcpAllowOthers");
            server2.start();
            System.out.println(server2.getStatus());
            System.out.println(server2.getURL());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTable() {
        try {
            System.out.println("Attempting to connect to the db");
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to database");
            dbConnection = DriverManager.getConnection(DB_URL, user, pass);

            // Exec query
            System.out.println("Creating a test table");
            dbStatement = dbConnection.createStatement();
            String sql2 = "CREATE TABLE Garage.Reg " + "(id INTEGER not NULL, "
                    + "first VARCHAR(255), " + "last VARCHAR(255), " +
                    "age INTEGER, " + "PRIMARY KEY (id))";
            dbStatement.execute(sql2);
            System.out.println("Created table REGISTRATION");

            // Clean up
            dbStatement.close();
            dbConnection.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (dbStatement != null)  {
                    dbStatement.close();
                }
            } catch (SQLException se2) {

            }
        }
    }

    public void insertTable() {
        try {
            System.out.println("Attempting to connect to the db");
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to database");

            dbConnection = DriverManager.getConnection(DB_URL, user, pass);
            dbStatement = dbConnection.createStatement();
            String sql = "INSERT INTO GARAGE.REG VALUES (1, 'Ramesh', 'Ahmedabad', 2000)";
            dbStatement.execute(sql);

            dbStatement.close();
            dbConnection.close();

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (dbStatement != null) {
                    dbStatement.close();
                }
            } catch (SQLException se2) {

            }
        }
    }

    public boolean readFromTable(String username, String password) throws SQLException, ClassNotFoundException {
        String first = "";
        String last = "";
            System.out.println("Attempting to connect to the db");
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to database");
            dbConnection = DriverManager.getConnection(DB_URL, user, pass);
            dbStatement = dbConnection.createStatement();
            String sql1 = "SELECT * FROM GARAGE.REG";
            result = dbStatement.executeQuery(sql1);
            while (result.next()) {
            // Retrieve by column name
            first = result.getString("FIRST");
            last = result.getString("LAST");
        }
            result.close();

            if ((username.equals(first)) && (password.equals(last))) {
                return true;
            } else {
                return false;
            }
    }



}


