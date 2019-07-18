package main.java.config;

import org.glassfish.jersey.server.ResourceConfig;

public class JerseyConfiguration extends ResourceConfig {

    public JerseyConfiguration() {
        packages("main.java.endpoints");
    }
}
