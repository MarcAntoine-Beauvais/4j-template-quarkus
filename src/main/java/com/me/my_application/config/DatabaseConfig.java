package com.me.my_application.config;

import io.quarkus.logging.Log;
import io.quarkus.runtime.StartupEvent;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.AttributeConverter;
import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serial;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class DatabaseConfig {

    @Inject
    @SuppressWarnings("CdiInjectionPointsInspection")
    DataSource dataSource;

    void onStart(@Observes StartupEvent ev) {
        this.init();
    }

    private void init() {
        try (Connection connection = this.dataSource.getConnection()) {
            try (Statement stmt = connection.createStatement()) {
                stmt.execute(
                        this.read("/init.sql")
                );
            } catch (SQLException e) {
                Log.error("Error processing statement", e);
            }
        } catch (SQLException e) {
            Log.error("Error processing connection", e);
        }
    }

    private String read(@SuppressWarnings("SameParameterValue") String path) {
        StringBuilder result = new StringBuilder();
        try {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                    Objects.requireNonNull(DatabaseConfig.class.getResourceAsStream(path))
            ))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            Log.error(e);
        }

        return result.toString();
    }

    public static class ListToStringConverter implements AttributeConverter<List<String>, String> {
        @Override
        public String convertToDatabaseColumn(List<String> attribute) {
            return attribute == null ? null : String.join(",",attribute);
        }

        @Override
        public List<String> convertToEntityAttribute(String dbData) {
            return dbData == null ? Collections.emptyList() : Arrays.asList(dbData.split(","));
        }
    }

    public static class SnakeCasePhysicalNamingStrategy extends PhysicalNamingStrategyStandardImpl {

        @Serial
        private static final long serialVersionUID = 2565585077301061335L;

        @Override
        public Identifier toPhysicalCatalogName(Identifier name, JdbcEnvironment context) {
            return super.toPhysicalCatalogName(this.toSnakeCase(name), context);
        }

        @Override
        public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
            return super.toPhysicalColumnName(this.toSnakeCase(name), context);
        }

        @Override
        public Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment context) {
            return super.toPhysicalSchemaName(this.toSnakeCase(name), context);
        }

        @Override
        public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment context) {
            return super.toPhysicalSequenceName(this.toSnakeCase(name), context);
        }

        @Override
        public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
            return super.toPhysicalTableName(this.toSnakeCase(name), context);
        }

        private Identifier toSnakeCase(Identifier id) {
            if (id == null) {
                return id;
            }

            String name = id.getText();
            String snakeName = name.replaceAll("([a-z]+)([A-Z]+)", "$1\\_$2").toLowerCase();
            if (!snakeName.equals(name)) {
                return new Identifier(snakeName, id.isQuoted());
            } else {
                return id;
            }
        }
    }
}
