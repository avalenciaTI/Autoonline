package com.solera.global.qa.autoonline.rest.test.assertions;

import static org.testng.AssertJUnit.assertEquals;

import com.solera.global.qa.core.framework.test.TestCaseReport;
import com.solera.global.qa.template.rest.behavior.data.responsedata.PhotoData;
import org.assertj.core.api.AbstractAssert;

public class PhotoAssert extends AbstractAssert<PhotoAssert, PhotoData> {

    protected PhotoAssert(PhotoData photoBean) {
        super(photoBean, PhotoAssert.class);
    }

    public static PhotoAssert assertThat(PhotoData photoBean) {
        return new PhotoAssert(photoBean);
    }

    public PhotoAssert isEqualTo(int id) {
        isNotNull();
        if (actual.getId() != id) {
            failWithMessage("Expected id to be <%s> but was <%s>", id, actual.getId());
        }
        return this;
    }

    public void idIsEqualTo(int id) {
        assertEquals("Id is correct", id, actual.getId());
    }

    public void albumIdIsEqualTo(int albumId) {
        assertEquals("Album Id is correct", albumId, actual.getAlbumId());
    }

    /*
     * Fill this out with asserts that you need
     */

}
