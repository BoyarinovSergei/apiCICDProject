package endPoints;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import lib.Specifications;
import java.util.Map;

import static lib.Urls.APOD;

public class APODApi {

    @Step("GET запрос без параметров для получения apod")
    public static Object getApod(int codeExpected, String path, Class name) {
        Specifications.installSpec(Specifications.requestSpecification(), Specifications.responseSpecification(codeExpected));
        return RestAssured
                .given()
                .when()
                .get(APOD)
                .then().extract().body().jsonPath()
                .getObject(path, name);
    }

    @Step("GET запрос с параметрами для получения apod")
    public static Object getApodWIthParams(int codeExpected, String path, Class name, Map<String, String> params) {
        Specifications.installSpec(Specifications.requestSpecification(), Specifications.responseSpecification(codeExpected));
        return RestAssured
                .given()
                .when()
                .params(params)
                .get(APOD)
                .then().extract().body().jsonPath()
                .getObject(path, name);
    }
}
