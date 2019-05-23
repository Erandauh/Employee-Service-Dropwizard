package com.dp.rest.config;

import io.dropwizard.Configuration;

public class Config  extends Configuration {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Config() {
    }
}
