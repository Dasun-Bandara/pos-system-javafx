package lk.ijse.dep11.pos.db;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class SingleConnectionDataSource {
    private static SingleConnectionDataSource instance;
    private final Connection connection;

    private SingleConnectionDataSource(){
        try {
            Properties properties = new Properties();
            properties.load(this.getClass().getResourceAsStream("/application.properties"));
            String url = properties.getProperty("app.datasource.url");
            String username = properties.getProperty("app.datasource.username");
            String password = properties.getProperty("app.datasource.password");
            connection = DriverManager.getConnection(url, username, password);
            generateSchema();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static SingleConnectionDataSource getInstance(){
        return instance == null ? instance = new SingleConnectionDataSource():instance;
    }
    public Connection getConnection(){
        return connection;
    }

    private void generateSchema() throws Exception {
        URL url = this.getClass().getResource("/schema.sql");
        Path path = Paths.get(url.toURI());
        String script = Files.readAllLines(path).stream().reduce((previousLine, currentLine) -> {
            return previousLine + currentLine;
        }).get();
        connection.createStatement().execute(script);
    }
}
