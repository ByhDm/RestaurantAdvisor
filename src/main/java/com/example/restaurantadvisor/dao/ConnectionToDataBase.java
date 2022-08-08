package com.example.restaurantadvisor.dao;

import com.example.restaurantadvisor.config.DataBaseConnectionProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.example.restaurantadvisor.config.DataBaseConnectionProperties.url;

@Component
public class ConnectionToDataBase {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionToDataBase.class);

    public static Connection connection;

    @PostConstruct
    public void initialize() {
        try {
            connection = DriverManager.getConnection(url, DataBaseConnectionProperties.getConnectionProps());
        } catch (SQLException e) {
            logger.debug("Incorrect dataBaseURL '{}' or connection properties '{}'"
                    , url
                    , DataBaseConnectionProperties.getConnectionProps()
                    , e);
        }
    }

    @PreDestroy
    public void destroy() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}