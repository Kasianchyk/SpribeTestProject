package com.spribe.test;

import com.spribe.test.api.ApiResponse;
import com.spribe.test.model.request.PlayerByIdRequestDto;
import com.spribe.test.model.request.PlayerRequestDto;
import com.spribe.test.model.response.PlayerCreateResponseDto;
import com.spribe.test.model.response.PlayerGetByPlayerIdResponseDto;
import com.spribe.test.utils.TestDataGeneratorUtils;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static com.spribe.test.utils.RequestUtils.createQueryParams;

public class GetPlayerTest extends BaseTest {

    private PlayerCreateResponseDto player;

    @BeforeClass
    void setUp() {
        PlayerRequestDto playerRequestDto = TestDataGeneratorUtils.generateRequestDTO();
        player = CLIENT.createPlayer(createQueryParams(playerRequestDto)).getData();
        cleanUpList.add(player.getId());
    }

    @Description("Get player by id test")
    @Test
    void getPlayerByPlayerId() {
        ApiResponse<PlayerGetByPlayerIdResponseDto> playerByIdResponse = CLIENT.getPlayerById(new PlayerByIdRequestDto(player.getId()));
        Assert.assertEquals(playerByIdResponse.getStatusCode(), 200, "Unexpected response code");
        PlayerGetByPlayerIdResponseDto playerById = playerByIdResponse.getData();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(player.getAge(), playerById.getAge());
        softAssert.assertEquals(player.getRole(), playerById.getRole());
        softAssert.assertEquals(player.getLogin(), playerById.getLogin());
        softAssert.assertEquals(player.getScreenName(), playerById.getScreenName());
        softAssert.assertEquals(player.getGender(), playerById.getGender());
        softAssert.assertEquals(player.getPassword(), playerById.getPassword());
        softAssert.assertAll();
    }

    @Description("Get player by id that does not exist in DB")
    @Test
    void getNotExistingPlayer() {
        ApiResponse<PlayerGetByPlayerIdResponseDto> playerByIdResponse = CLIENT.getPlayerById(new PlayerByIdRequestDto(Long.MAX_VALUE));
        Assert.assertEquals(playerByIdResponse.getStatusCode(), 404, "Unexpected response code");
    }
}
