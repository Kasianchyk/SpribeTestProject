package com.spribe.test.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class  PlayerItem {
    private Long id;
    private Integer age;
    private String gender;
    private String role;
    private String screenName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerItem that = (PlayerItem) o;
        return Objects.equals(id, that.id) && Objects.equals(age, that.age) && Objects.equals(gender, that.gender) && Objects.equals(role, that.role) && Objects.equals(screenName, that.screenName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, age, gender, role, screenName);
    }
}
