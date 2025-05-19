package com.spribe.test;

import com.spribe.test.api.ApiClient;
import com.spribe.test.config.ProjectProperties;
import com.spribe.test.config.Properties;
import com.spribe.test.model.request.PlayerByIdRequestDto;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

@Test
public class BaseTest {

    private static final ProjectProperties properties = Properties.getProjectProperties();

    public static ApiClient CLIENT;
    public static final Set<Long> cleanUpList = new HashSet<>();

    @BeforeSuite
    void setUpSuite() {
        CLIENT = ApiClient.getInstance();

    }

    @AfterSuite
    void cleanUp() {
        cleanUpList.forEach(id -> {
            PlayerByIdRequestDto body = new PlayerByIdRequestDto(id);
            CLIENT.deletePlayer(body);
        });
    }
}
