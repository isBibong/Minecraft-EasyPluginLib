package pers.bibong.lib.data.mysql;

import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MySQLTable {
    private final Connection connection;
    private final String     tableName;

    public MySQLTable (
            Connection connection,
            String tableName
    )
    {
        this.connection = connection;
        this.tableName  = tableName;
    }

    //VARCHAR(255)
    protected void createTable (
            @NotNull List<String> columnNames,
            List<String> columnType
    )
    {
        String sql = "CREATE TABLE " + tableName + " (" + String.join(", ", columnNames) + " " + String.join(", ",
                                                                                                             columnType) + ")";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertRow (@NotNull List<String> values) throws SQLException {
        String sql = "INSERT INTO " + tableName + " VALUES (" + String.join(", ",
                                                                            Collections.nCopies(values.size(),
                                                                                                "?")) + ")";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < values.size(); i++) {
                statement.setString(i + 1, values.get(i));
            }
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<List<String>> selectAll () throws SQLException {
        String             sql       = "SELECT * FROM " + tableName;
        PreparedStatement  statement = connection.prepareStatement(sql);
        ResultSet          resultSet = statement.executeQuery();
        List<List<String>> rows      = new ArrayList<>();
        while (resultSet.next()) {
            List<String> row = new ArrayList<>();
            for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                row.add(resultSet.getString(i));
            }
            rows.add(row);
        }
        return rows;
    }

    public void updateRow (
            @NotNull List<String> columnNames,
            List<String> values,
            String whereClause
    )
    {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ").append(tableName).append(" SET ");
        for (int i = 0; i < columnNames.size(); i++) {
            sb.append(columnNames.get(i)).append(" = ?");
            if (i < columnNames.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append(" WHERE ").append(whereClause);
        String sql = sb.toString();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            for (int i = 0; i < values.size(); i++) {
                statement.setString(i + 1, values.get(i));
            }
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteRow (String whereClause) throws SQLException {
        String            sql       = "DELETE FROM " + tableName + " WHERE " + whereClause;
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.executeUpdate();
    }

    public void dropTable () throws SQLException {
        String            sql       = "DROP TABLE " + tableName;
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.executeUpdate();
    }

}
