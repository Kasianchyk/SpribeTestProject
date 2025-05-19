package com.spribe.test.model.response;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class PlayerCreateResponseDto {

    private Long id;
    private Integer age;
    private String gender;
    private String login;
    private String password;
    private String role;
    private String screenName;

}
