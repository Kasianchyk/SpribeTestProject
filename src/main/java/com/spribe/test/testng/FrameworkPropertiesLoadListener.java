package com.spribe.test.testng;

import com.spribe.test.config.Properties;
import lombok.extern.log4j.Log4j2;
import org.testng.IExecutionListener;

@Log4j2
public class FrameworkPropertiesLoadListener implements IExecutionListener {


    @Override
    public void onExecutionStart() {
        Properties.load();
        String environment = System.getProperty("environment", "local");

        log.info("Configuration initialized for environment: {}", environment);
        log.info("Base URL: {}", Properties.getProjectProperties().baseUrl());
    }
}
