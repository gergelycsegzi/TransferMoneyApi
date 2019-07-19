package main.java.config;

import org.glassfish.jersey.ExtendedConfig;
import org.glassfish.jersey.server.ResourceConfig;

public class JerseyConfiguration extends ResourceConfig {

    public JerseyConfiguration() {
        ExtendedConfig extendedConfig;
        packages("main.java.endpoints");
    }
}
