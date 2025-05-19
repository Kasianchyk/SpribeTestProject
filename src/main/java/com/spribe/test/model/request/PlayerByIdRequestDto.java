package com.spribe.test.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class PlayerByIdRequestDto implements IRequestBody {
    private Long playerId;
}
