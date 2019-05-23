package com.dp.rest;

import com.dp.rest.config.Config;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeControllerIntegTest {

    @ClassRule
    public static final DropwizardAppRule<Config> RULE = new DropwizardAppRule<Config>(App.class);

    @Test
    public void canGetEmployeeWithClient(){

        Client client = new JerseyClientBuilder(RULE.getEnvironment()).build("test client");
        Response response = client.target( String.format("http://localhost:%d/employees/1", RULE.getLocalPort())).request().get();

        assertThat(response.getStatus()).isEqualTo(200);
    }
}
