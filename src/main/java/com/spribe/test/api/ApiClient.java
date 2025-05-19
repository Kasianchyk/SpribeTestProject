package com.spribe.test.api;

import com.spribe.test.config.Properties;
import com.spribe.test.model.request.IRequestBody;
import com.spribe.test.model.response.PlayerCreateResponseDto;
import com.spribe.test.model.response.PlayerGetAllResponseDto;
import com.spribe.test.model.response.PlayerGetByPlayerIdResponseDto;
import com.spribe.test.model.response.PlayerUpdateResponseDto;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.log4j.Log4j2;

import java.util.Map;
import java.util.Objects;

@Log4j2
public class ApiClient {

    private static final String BASE_URL = Properties.getProjectProperties().baseUrl();
    private final RequestSpecification requestSpecification;
    private static ApiClient instance;

    private ApiClient() {
        this.requestSpecification = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(io.restassured.http.ContentType.JSON)
                .build();
    }

    public static ApiClient getInstance() {
        if (instance == null) {
            instance = new ApiClient();
        }
        return instance;
    }

    @Step("Send create request with body: {0}")
    public ApiResponse<PlayerCreateResponseDto> createPlayer(Map<String,Object> queryParams){
        log.debug("executing Create Player request with query params: {}", queryParams);
        return executeRequest(HttpMethod.GET, Endpoints.CREATE_PLAYER, null, queryParams, PlayerCreateResponseDto.class);
    }

    @Step("Send get player by id request for player id: {0}")
    public ApiResponse<PlayerGetByPlayerIdResponseDto> getPlayerById(IRequestBody body) {
        log.debug("executing Get Player By Id request: {}", body);
        return executeRequest(HttpMethod.POST, Endpoints.GET_PLAYER, body, null, PlayerGetByPlayerIdResponseDto.class);
    }

    @Step("Send get all players request")
    public ApiResponse<PlayerGetAllResponseDto> getAllPlayers() {
        log.debug("executing Get All Players request");
        return executeRequest(HttpMethod.GET, Endpoints.GET_ALL_PLAYERS, null, null, PlayerGetAllResponseDto.class);
    }

    @Step("Send update request with body: {0}")
    public ApiResponse<PlayerUpdateResponseDto> updatePlayer(Long id, IRequestBody body) {
        log.debug("executing Update Player request with id: {}, and body: {}", id, body);
        return executeRequest(HttpMethod.PATCH, String.format(Endpoints.UPDATE_PLAYER, id), body, null, PlayerUpdateResponseDto.class);
    }

    public ApiResponse<String> deletePlayer(IRequestBody body) {
        log.debug("executing Delete Player request: {}", body);
        return executeRequest(HttpMethod.DELETE, Endpoints.DELETE_PLAYER, body, null, String.class);
    }

    private  <T> ApiResponse<T> executeRequest(HttpMethod method, String path, IRequestBody body, Map<String,Object> queryParams , Class<T> responseClass) {
        Response response = sendRequest(method, path, body, queryParams);
        int statusCode = response.getStatusCode();
        if (statusCode != 200)
            return new ApiResponse<>(statusCode, "", null);
        String responseBody = response.asString();
        T data = null;
        if (!responseBody.isEmpty()){
            data = response.as(responseClass);
        }

        return new ApiResponse<>(statusCode, responseBody, data);
    }

    private Response sendRequest(HttpMethod method, String path, Object body, Map<String,Object> queryParams) {
        RequestSpecification spec = RestAssured
                .given()
                .spec(requestSpecification).log().all();
        if (Objects.nonNull(queryParams) && !queryParams.isEmpty()){
            queryParams.forEach(spec::queryParam);
        }
        if (body != null) {
            spec.body(body);
        }

        return switch (method.name()) {
            case "GET" -> spec.when().get(path);
            case "POST" -> spec.when().post(path);
            case "PUT" -> spec.when().put(path);
            case "DELETE" -> spec.when().delete(path);
            case "PATCH" -> spec.when().patch(path);
            default -> throw new IllegalArgumentException("Method not supported: " + method);
        };
    }
}
