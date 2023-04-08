package ru.tinkoff.edu.java.linkparser.db;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;


public class IntegrationEnvironmentTest extends IntegrationEnvironment {

    @Test
    void checkExistsTables() throws SQLException {
        Connection connection = getConnection();
        List<String> tableNames = getTablesNames(connection);
        Set<String> correctNames = new HashSet<>(Arrays.asList(
                "link", "chat", "subscription", "databasechangeloglock", "databasechangelog"));
        for (String name : tableNames) {
            assertThat(correctNames).contains(name);
        }
    }

    private List<String> getTablesNames(Connection connection) throws SQLException {
        List<String> tablesNames = new ArrayList<>();
        DatabaseMetaData metaData = connection.getMetaData();
        try (ResultSet rs = metaData.getTables(null, "public", "%", new String[]{"TABLE"})) {
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                tablesNames.add(tableName);
            }
        }
        return tablesNames;
    }

}