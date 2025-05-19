package com.spribe.test.config;

import lombok.Getter;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.core.config.Configurator;

public class Properties {

    @Getter
    private static ProjectProperties projectProperties;

    public static void load() {
        Configurator.initialize("log4j2", "configurations/log4j2.xml");
        projectProperties = ConfigFactory.create(ProjectProperties.class, System.getProperties());
    }
}
