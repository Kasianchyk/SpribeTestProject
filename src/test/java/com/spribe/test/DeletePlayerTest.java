package com.spribe.test;

import com.spribe.test.api.ApiResponse;
import com.spribe.test.model.request.PlayerByIdRequestDto;
import com.spribe.test.model.request.PlayerRequestDto;
import com.spribe.test.utils.TestDataGeneratorUtils;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.spribe.test.utils.RequestUtils.createQueryParams;

public class DeletePlayerTest extends BaseTest {

    private Long playerIdToDelete;

    @BeforeClass
    void setUp() {
        PlayerRequestDto playerRequestDto = TestDataGeneratorUtils.generateRequestDTO();
        playerIdToDelete = CLIENT.createPlayer(createQueryParams(playerRequestDto)).getData().getId();
    }

    @Description("Delete player request test")
    @Test
    void deletePlayerTest() {
        PlayerByIdRequestDto body = new PlayerByIdRequestDto(playerIdToDelete);
        ApiResponse<String> apiResponse = CLIENT.deletePlayer(body);
        Assert.assertEquals(apiResponse.getStatusCode(), 204);
    }

    @Description("Deleting non existing player")
    @Test
    void deleteNonExistingPlayerTest() {
        PlayerByIdRequestDto body = new PlayerByIdRequestDto(Long.MAX_VALUE);
        ApiResponse<String> apiResponse = CLIENT.deletePlayer(body);
        Assert.assertEquals(apiResponse.getStatusCode(), 403);
    }
}
