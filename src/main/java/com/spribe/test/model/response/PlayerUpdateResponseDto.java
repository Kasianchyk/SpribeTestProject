package com.spribe.test.model.response;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class PlayerUpdateResponseDto {

    private Long id;
    private Integer age;
    private  String gender;
    private  String login;
    private  String role;
    private  String screenName;
}
