package api;

import endPoints.APODApi;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import lib.Specifications;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pojoApod.Error;
import pojoApod.SuccessRequest;

import java.util.Map;

import static lib.Urls.APOD;
import static org.assertj.core.api.Assertions.assertThat;

public class APODTests {
    private static final String HOST = "https://api.nasa.gov";

    @BeforeEach
    public void setFilter() {
        RestAssured.filters(new AllureRestAssured());
    }

    @Test
    public void testGetApodWithNoParams() {
        var error = (Error) APODApi
                .getApod(403, "error", Error.class);

        assertThat(error.getCode()).isEqualTo("API_KEY_MISSING");
        assertThat(error.getMessage()).isEqualTo("No api_key was supplied. Get one at https://api.nasa.gov:443");
    }

    @Test
    public void testGetApodWithWrongToken() {
        var error = (Error) APODApi
                .getApodWIthParams(403, "error", Error.class,
                        Map.of(
                        "api_key",
                        "asdfa234"));

        assertThat(error.getCode()).isEqualTo("API_KEY_INVALID");
        assertThat(error.getMessage()).isEqualTo("An invalid api_key was supplied. Get one at https://api.nasa.gov:443");
    }

    @Test
    public void testGetApodWithDemoToken() {
        var successRequest = (SuccessRequest) APODApi
                .getApodWIthParams(200, "$", SuccessRequest.class, Map.of("api_key", "DEMO_KEY"));

        assertThat(successRequest.getDate()).isEqualTo("2023-03-11");
        assertThat(successRequest.getService_version()).isEqualTo("v1");
        assertThat(successRequest.getMedia_type()).isEqualTo("image");
    }
}
