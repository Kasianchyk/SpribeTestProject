package com.spribe.test;

import com.spribe.test.api.ApiResponse;
import com.spribe.test.model.request.PlayerRequestDto;
import com.spribe.test.model.response.PlayerCreateResponseDto;
import com.spribe.test.utils.TestDataGeneratorUtils;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.SeverityLevel;
import lombok.extern.log4j.Log4j2;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static com.spribe.test.utils.RequestUtils.createQueryParams;

@Log4j2
@Epic("Application API")
@Feature("Create Player")
@io.qameta.allure.Severity(SeverityLevel.CRITICAL)
public class CreatePlayerTest extends BaseTest {

    @Description("Create player request test")
    @io.qameta.allure.Severity(SeverityLevel.CRITICAL)
    @Test
    void createPlayerTest() {
        log.debug("TEST DEBUG MESSAGE");
        PlayerRequestDto requestDto = TestDataGeneratorUtils.generateRequestDTO();
        ApiResponse<PlayerCreateResponseDto> response = CLIENT.createPlayer(createQueryParams(requestDto));
        Assert.assertEquals(response.getStatusCode(), 200, "Not expected status code");

        PlayerCreateResponseDto player = response.getData();
        Assert.assertNotNull(player.getId());
        cleanUpList.add(player.getId());

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(player.getAge(), requestDto.getAge());
        softAssert.assertEquals(player.getRole(), requestDto.getRole());
        softAssert.assertEquals(player.getLogin(), requestDto.getLogin());
        softAssert.assertEquals(player.getScreenName(), requestDto.getScreenName());
        softAssert.assertEquals(player.getGender(), requestDto.getGender());
        softAssert.assertEquals(player.getPassword(), requestDto.getPassword());
        softAssert.assertAll();
    }

    @Description("Verifying body request mandatory fields")
    @Test(dataProvider = "mandatoryFieldDataProvider")
    @io.qameta.allure.Severity(SeverityLevel.NORMAL)
    void mandatoryFieldValidationTest(PlayerRequestDto playerRequestDto) {
        ApiResponse<PlayerCreateResponseDto> response = CLIENT.createPlayer(createQueryParams(playerRequestDto));
        Assert.assertEquals(response.getStatusCode(), 400, String.format("Mandatory field validation failed. Request body: %s", playerRequestDto));
    }

    @DataProvider
    public Object[][] mandatoryFieldDataProvider() {
        PlayerRequestDto noAgePlayer = TestDataGeneratorUtils.generateRequestDTO();
        noAgePlayer.setAge(null);
        PlayerRequestDto noGenderPlayer = TestDataGeneratorUtils.generateRequestDTO();
        noGenderPlayer.setGender(null);
        return new Object[][]{
                {noAgePlayer},
                {noGenderPlayer},
        };
    }
}
