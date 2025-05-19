package com.spribe.test.model.response;

import com.spribe.test.model.PlayerItem;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class PlayerGetAllResponseDto {

    List<PlayerItem> players;
}
