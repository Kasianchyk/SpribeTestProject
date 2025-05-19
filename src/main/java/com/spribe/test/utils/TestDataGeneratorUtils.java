package com.spribe.test.utils;

import com.github.javafaker.Faker;
import com.spribe.test.model.request.PlayerRequestDto;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class TestDataGeneratorUtils {

    private static final Faker FAKER = new Faker();

    @Step("Generate player request body")
    public static PlayerRequestDto generateRequestDTO() {
        List<String> genderOptions = List.of("Male", "Female", "Other");
        String password = FAKER.name().lastName();
        String gender = FAKER.options().option(genderOptions).get(0);
        String login = FAKER.name().firstName();
        return PlayerRequestDto
                .builder()
                .age(ThreadLocalRandom.current().nextInt(20, 61))
                .gender(gender)
                .login(FAKER.name().firstName())
                .password(password)
                .role("user")
                .screenName(login.concat("_").concat(password))
                .build();
    }

}
