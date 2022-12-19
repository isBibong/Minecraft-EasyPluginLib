package pers.bibong.lib.data.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class ConnectMySQL
{
    private final String url;
    private final String userName;
    private final String password;
    private final String database;

    private Connection connection;

    public ConnectMySQL (
            String localhost, String port, String userName, String password, String database
    )
    {
        this.url      = "jdbc:mysql://" + localhost + ":" + port + "/test";
        this.userName = userName;
        this.password = password;
        this.database = database;

        try (Connection connection = DriverManager.getConnection(this.url, this.userName, this.password))
        {
            this.connection = connection;

            Statement statement = this.connection.createStatement();
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS " + this.database);
            statement.execute("USE " + this.database);
            System.out.println("Successfully connected to the database.");
        }
        catch (SQLException e)
        {
            System.out.println("An error occurred while connecting to the database: " + e.getMessage());
        }
    }

    public String getUrl ()
    {
        return url;
    }

    public String getUserName ()
    {
        return userName;
    }

    public String getPassword ()
    {
        return password;
    }

    public String getDatabase ()
    {
        return database;
    }

    public Connection getConnection ()
    {
        return connection;
    }
}
