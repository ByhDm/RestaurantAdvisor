package com.example.restaurantadvisor.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class DataBaseConnectionProperties {

    public static String url;
    private static String userName;
    private static String password;

    @Autowired
    public DataBaseConnectionProperties(@Value("${spring.datasource.url}") String url,
                                        @Value("${spring.datasource.username}") String userName,
                                        @Value("${spring.datasource.password}") String password) {
        DataBaseConnectionProperties.url = url;
        DataBaseConnectionProperties.userName = userName;
        DataBaseConnectionProperties.password = password;
    }

    public static Properties getConnectionProps() {
        Properties properties = new Properties();
        properties.setProperty("user", userName);
        properties.setProperty("password", password);
        return properties;
    }
}