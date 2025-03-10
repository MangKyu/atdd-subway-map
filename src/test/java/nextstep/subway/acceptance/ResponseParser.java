package nextstep.subway.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import java.util.List;

public class ResponseParser {

    public static Long getIdFromResponse(final ExtractableResponse<Response> response) {
        return response.jsonPath().getLong("id");
    }

    public static String getNameFromResponse(final ExtractableResponse<Response> response) {
        return response.jsonPath().getString("name");
    }

    public static List<String> getNamesFromResponse(final ExtractableResponse<Response> response) {
        return response.jsonPath().getList("name", String.class);
    }

}
