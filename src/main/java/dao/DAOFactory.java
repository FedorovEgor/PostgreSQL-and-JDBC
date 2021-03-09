package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DAOFactory {
    private static String URL;
    private static String USER;
    private static String PASSWORD;

    static {
        try (InputStream inputStream = DAOFactory.class.getClassLoader().getResourceAsStream("dbConnectionData.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);

            URL = properties.getProperty("CONNECTION_URL");
            USER = properties.getProperty("USER");
            PASSWORD = properties.getProperty("PASSWORD");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static DAOFactory instance;

    private DAOFactory() {}

    public static DAOFactory getInstance() {
        if (instance == null) {
            instance = new DAOFactory();
        }
        return instance;
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}

