package com.spribe.test.model.request;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class PlayerRequestDto implements IRequestBody {

    private Integer age;
    private String gender;
    private String login;
    private String password;
    private String role;
    private String screenName;
}
