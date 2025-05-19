package com.spribe.test.utils;

import com.spribe.test.model.request.PlayerRequestDto;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Map.entry;

public class RequestUtils {

    public static Map<String,Object> createQueryParams(PlayerRequestDto playerRequestDto) {
        return Stream.of(
                        playerRequestDto.getAge() != null ? entry("age", playerRequestDto.getAge()) : null,
                        playerRequestDto.getGender() != null ? entry("gender", playerRequestDto.getGender()) : null,
                        playerRequestDto.getLogin() != null ? entry("login", playerRequestDto.getLogin()) : null,
                        playerRequestDto.getPassword() != null ? entry("password", playerRequestDto.getPassword()) : null,
                        playerRequestDto.getRole() != null ? entry("role", playerRequestDto.getRole()) : null,
                        playerRequestDto.getScreenName() != null ? entry("screenName", playerRequestDto.getScreenName()) : null)
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
