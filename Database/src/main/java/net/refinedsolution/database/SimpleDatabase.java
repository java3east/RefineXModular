package net.refinedsolution.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * SimpleDatabase provides an easy-to-use temporary database for testing purposes.
 * It uses H2 in-memory database which automatically gets cleaned up when the connection is closed.
 */
public class SimpleDatabase implements AutoCloseable {
    
    private Connection connection;
    private final String databaseName;
    
    /**
     * Creates a new temporary in-memory database with a random name.
     */
    public SimpleDatabase() {
        this("testdb_" + UUID.randomUUID().toString().replace("-", ""));
    }
    
    /**
     * Creates a new temporary in-memory database with the specified name.
     * @param databaseName the name of the database
     */
    public SimpleDatabase(String databaseName) {
        this.databaseName = databaseName;
        initializeDatabase();
    }
    
    /**
     * Initializes the H2 in-memory database connection.
     */
    private void initializeDatabase() {
        try {
            // Explicitly load the H2 driver
            Class.forName("org.h2.Driver");
            // H2 in-memory database URL
            String url = "jdbc:h2:mem:" + databaseName + ";DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE";
            this.connection = DriverManager.getConnection(url, "sa", "");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database: " + databaseName, e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("H2 database driver not found", e);
        }
    }
    
    public boolean execute(String sql, Object... params) {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            setParameters(statement, params);
            statement.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            return false;
        }
    }

    public List<HashMap<String, Object>> query(String sql, Object... params) {
        List<HashMap<String, Object>> results = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            setParameters(statement, params);
            ResultSet resultSet = statement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                HashMap<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object value = resultSet.getObject(i);
                    if (value instanceof Timestamp)
                        value = value.toString();
                    row.put(columnName, value);
                }
                results.add(row);
            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
        return results;
    }

    private void setParameters(PreparedStatement statement, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            if (params[i] instanceof Integer) {
                statement.setInt(i + 1, (Integer) params[i]);
            } else if (params[i] instanceof String) {
                statement.setString(i + 1, (String) params[i]);
            } else if (params[i] instanceof Double) {
                statement.setDouble(i + 1, (Double) params[i]);
            } else if (params[i] instanceof Boolean) {
                statement.setBoolean(i + 1, (Boolean) params[i]);
            } else if (params[i] == null) {
                statement.setNull(i + 1, Types.NULL);
            } else {
                throw new IllegalArgumentException("Unsupported parameter type: " + params[i].getClass());
            }
        }
    }
    
    /**
     * Closes the database connection and cleans up resources.
     * The in-memory database will be automatically destroyed.
     */
    @Override
    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Warning: Failed to close database connection: " + e.getMessage());
            }
        }
    }
}
