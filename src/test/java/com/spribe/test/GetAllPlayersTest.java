package com.spribe.test;

import com.spribe.test.api.ApiResponse;
import com.spribe.test.model.PlayerItem;
import com.spribe.test.model.request.PlayerRequestDto;
import com.spribe.test.model.response.PlayerCreateResponseDto;
import com.spribe.test.model.response.PlayerGetAllResponseDto;
import com.spribe.test.utils.RequestUtils;
import com.spribe.test.utils.TestDataGeneratorUtils;
import io.qameta.allure.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;


public class GetAllPlayersTest extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(GetAllPlayersTest.class);
    private PlayerRequestDto newPlayer;

    @BeforeClass
    void setUp() {
        newPlayer = TestDataGeneratorUtils.generateRequestDTO();
    }

    @Description("Getting all list of players test")
    @Test
    void getAllPlayersTest() {
        ApiResponse<PlayerGetAllResponseDto> allPlayersBeforeResponse = CLIENT.getAllPlayers();
        Assert.assertEquals(allPlayersBeforeResponse.getStatusCode(), 200, "Unexpected response code");

        ApiResponse<PlayerCreateResponseDto> playerResponse = CLIENT.createPlayer(RequestUtils.createQueryParams(newPlayer));
        Assert.assertEquals(playerResponse.getStatusCode(), 200, "Unexpected response code");

        cleanUpList.add(playerResponse.getData().getId());

        ApiResponse<PlayerGetAllResponseDto> allPlayersAfterResponse = CLIENT.getAllPlayers();
        Assert.assertEquals(allPlayersAfterResponse.getStatusCode(), 200, "Unexpected response code");

        List<PlayerItem> playersAfter = allPlayersAfterResponse.getData().getPlayers();
        List<PlayerItem> playersBefore = allPlayersBeforeResponse.getData().getPlayers();

        Assert.assertTrue(playersBefore.size() <= playersAfter.size());

        boolean allPlayerListContainsNewPlayer = playersAfter
                .stream()
                .anyMatch(playerItem -> playerItem.getId().equals(playerResponse.getData().getId()));

        Assert.assertTrue(allPlayerListContainsNewPlayer);
    }

    @Description("")
    @Test
    void getAllPlayersNegativeTest() {
        log.debug("Negative test for Get All Players request");
    }
}
