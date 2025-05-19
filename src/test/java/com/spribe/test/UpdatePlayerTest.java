package com.spribe.test;


import com.spribe.test.api.ApiResponse;
import com.spribe.test.model.request.PlayerRequestDto;
import com.spribe.test.model.response.PlayerCreateResponseDto;
import com.spribe.test.model.response.PlayerUpdateResponseDto;
import com.spribe.test.utils.RequestUtils;
import com.spribe.test.utils.TestDataGeneratorUtils;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UpdatePlayerTest extends BaseTest {


    PlayerCreateResponseDto player;

    @BeforeClass
    void setUp() {
        ApiResponse<PlayerCreateResponseDto> newPlayerResponse = CLIENT.createPlayer(RequestUtils.createQueryParams(TestDataGeneratorUtils.generateRequestDTO()));
        player = newPlayerResponse.getData();
        cleanUpList.add(player.getId());
    }


    @Description("Updating player data test")
    @Test
    void updatePlayerTest() {
        PlayerRequestDto playerRequestDto = PlayerRequestDto
                .builder()
                .screenName("updated_screen_name")
                .build();

        ApiResponse<PlayerUpdateResponseDto> playerUpdateResponse = CLIENT.updatePlayer(player.getId(), playerRequestDto);
        Assert.assertEquals(playerUpdateResponse.getStatusCode(), 200, "Unexpected response code");
        PlayerUpdateResponseDto playerUpdated = playerUpdateResponse.getData();
        Assert.assertEquals(playerUpdated.getScreenName(), playerRequestDto.getScreenName(), String.format("Player with id=%s was not updated", player.getId()));
    }

    @Description("Updating non existing player test")
    @Test
    void updateNonExistingPlayer() {
        PlayerRequestDto playerRequestDto = PlayerRequestDto
                .builder()
                .screenName("updated_screen_name")
                .build();
        ApiResponse<PlayerUpdateResponseDto> playerUpdateResponse = CLIENT.updatePlayer(Long.MAX_VALUE, playerRequestDto);
        cleanUpList.add(Long.MAX_VALUE);
        Assert.assertEquals(playerUpdateResponse.getStatusCode(), 404);

    }
}
