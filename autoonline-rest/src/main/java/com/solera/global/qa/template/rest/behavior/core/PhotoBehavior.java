package com.solera.global.qa.template.rest.behavior.core;

import com.solera.global.qa.rest.framework.assertions.ResponseAssert;
import com.solera.global.qa.rest.framework.response.RestResponse;
import com.solera.global.qa.template.rest.behavior.data.responsedata.PhotoData;
import com.solera.global.qa.template.rest.behavior.request.PhotoRequest;
import org.apache.http.HttpStatus;

public class PhotoBehavior {

    private PhotoRequest photosRequest;

    public PhotoBehavior() {
        photosRequest = new PhotoRequest();
    }

    public PhotoData getPhoto(int id) {

        RestResponse response = photosRequest.getPhotoRequest(id);

        //Here and further:
        //this assert can be removed from here and moved to template-rest-test project for negative tests

        ResponseAssert.assertThat(response).statusCodeIs(HttpStatus.SC_OK);

        return response.getResponse().as(PhotoData.class);
    }

    public PhotoData postPhotoOption1(PhotoData body) {

        RestResponse response = photosRequest.postPhotoRequest(body);

        //In this option the assert is done in the behavior
        ResponseAssert.assertThat(response).statusCodeIs(HttpStatus.SC_CREATED);

        return response.getResponse().as(PhotoData.class);
    }

    public RestResponse postPhotoOption2(PhotoData body) {

        //In this option there is no assert in the behavior
        return photosRequest.postPhotoRequest(body);
    }

}
