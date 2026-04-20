package com.solera.global.qa.autoonline.web.test.cases.login;

import com.solera.global.qa.taf.core.data.annotations.TmsData;
import com.solera.global.qa.taf.core.data.enums.TcType;
import com.solera.global.qa.template.web.behavior.data.types.AolWebUser;
import com.solera.global.qa.template.web.behavior.pages.WebTestBase;
import org.testng.annotations.Test;

public class SmokeTest extends WebTestBase {

    @Test(priority = 51)
    @TmsData.Tc(
            tcId = 160970,
            tcName = "CP055_Adjudicate a unit.",
            tcType = TcType.SMOKE
    )
    public void tc1_adjudicatePublicationToBuyer() throws Exception {
        new TestAwards().tc32_adjudicatePublicationToBuyer();
    }
}
