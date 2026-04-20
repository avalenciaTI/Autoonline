package com.solera.global.qa.template.rest.behavior.request;

import com.solera.global.qa.rest.framework.request.RestRequestSpecification;
import com.solera.global.qa.rest.framework.request.RestRestAssured;
import com.solera.global.qa.rest.framework.response.RestResponse;
import com.solera.global.qa.template.rest.behavior.data.responsedata.PhotoData;
import io.restassured.http.ContentType;

public class PhotoRequest {

    private static final String URL_PHOTOS = "/photos";

    private RestRequestSpecification specification;

    public PhotoRequest() {
        specification = RestRestAssured.given()
                .testCaseReport()
                .contentType(ContentType.JSON)
                .baseUri(TemplateBaseRequest.getBaseUrl());
    }

    public RestResponse getPhotoRequest(int photoId) {
        return RestRestAssured.given()
                .spec(specification)
                .basePath("/photos/{id}")
                .pathParam("id", String.valueOf(photoId))
                .when()
                .get();
    }

    public RestResponse postPhotoRequest(PhotoData body) {
        return RestRestAssured.given()
                .spec(specification)
                .basePath(URL_PHOTOS)
                .body(body)
                .when()
                .post();
    }

}
