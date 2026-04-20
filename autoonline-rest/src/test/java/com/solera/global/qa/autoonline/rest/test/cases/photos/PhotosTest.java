package com.solera.global.qa.autoonline.rest.test.cases.photos;

import static com.solera.global.qa.autoonline.rest.test.assertions.PhotoAssert.assertThat;

import com.solera.global.qa.autoonline.rest.test.cases.TemplateRestTestBase;
import com.solera.global.qa.rest.framework.assertions.ResponseAssert;
import com.solera.global.qa.rest.framework.response.RestResponse;
import com.solera.global.qa.template.rest.behavior.data.responsedata.PhotoData;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class PhotosTest extends TemplateRestTestBase  {

    private static PhotoData expected;

    @BeforeClass
    public static void setUp() {
        expected = PhotoData.builder()
                .albumId(999)
                .id(1)
                .title("Any title of a photo")
                .url("https://via.placeholder.com/600/92c952")
                .thumbnailUrl("https://via.placeholder.com/150/92c952")
                .build();
    }

    @Test
    public void getPhotoTest() {
        //initiateTemplateRestTest("getPhotoTest");
        int id = 1;

        PhotoData response = behavior.photoService().getPhoto(id);

        //There are two options in order to make the assertions
        //Option 1: Using testCaseReport
        assertions().assertThat(response.getAlbumId()).as("Id is correct").isEqualTo(id);

        //Option 2: Using a class with methods for the assertions
        assertions().assertThat(response.getAlbumId()).isEqualTo(id);
    }

    @Test
    public void postPhotoTestOption1() {
        //initiateTemplateRestTest("postPhotoTestOption1");
        PhotoData response = behavior.photoService().postPhotoOption1(expected);

        //Option 1
        assertions().assertThat(expected.getAlbumId()).as("Id is correct").isEqualTo(response.getAlbumId());

        //Option 2
        assertions().assertThat(response).isEqualTo(expected.getAlbumId());
    }

    @Test
    public void postPhotoTestOption2() {
        RestResponse response = behavior.photoService().postPhotoOption2(expected);
        ResponseAssert.assertThat(response).statusCodeIs(HttpStatus.SC_CREATED);

        PhotoData photoBean = response.getResponse().as(PhotoData.class);
        assertions().assertThat(expected.getAlbumId()).as("Album Id is correct")
                .isEqualTo(photoBean.getAlbumId());
    }

}
