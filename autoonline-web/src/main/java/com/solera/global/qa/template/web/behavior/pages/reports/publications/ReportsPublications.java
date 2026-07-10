package com.solera.global.qa.template.web.behavior.pages.reports.publications;

import com.solera.global.qa.taf.web.tools.webdriver.BrowserPage;
import com.solera.global.qa.template.web.behavior.data.types.AolWebUser;
import com.solera.global.qa.template.web.behavior.pages.componentpages.CaseType;
import com.solera.global.qa.template.web.behavior.pages.componentpages.FilesCompare;
import com.solera.global.qa.template.web.behavior.pages.componentpages.PublicationSearch;
import com.solera.global.qa.template.web.behavior.pages.componentpages.enums.Reports;
import com.solera.global.qa.template.web.behavior.pages.componentpages.submenu.PublicationsOptions;
import com.solera.global.qa.template.web.behavior.pages.loginpage.LogInPage;
import com.solera.global.qa.template.web.behavior.pages.menupage.MenuPage;
import com.solera.global.qa.template.web.behavior.pages.publications.PublicationCreation;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;

@Slf4j
public class ReportsPublications extends BrowserPage {

    private static final String TEST_INSURER = "AAPR011";
    private static final String RESULTS_REPORT = "//button[@jest-id='reportButton' and "
            + "span[text()='Reporte de resultados']]";
    private static final String OFFERS_REPORT = "//button[@jest-id='reportButton' and "
            + "span[text()='Reporte de ofertas']]";
    private static final String VIEWS_REPORT = "//button[@jest-id='reportButton' and "
            + "span[text()='Reporte de vistas']]";


    private final LogInPage login = new LogInPage();
    public final FilesCompare filesActions = new FilesCompare();

    public ReportsPublications() {
        super();
    }


    public boolean validateResultReport(AolWebUser user) {
        log.info("validating result report");
        login.logIn(user);
        //Search publication
        new MenuPage().clickPublications(PublicationsOptions.CONSULT);

        PublicationSearch search = new PublicationSearch();
        search.selectPublicationType(CaseType.VEHICLES);
        search.setNotFocusOnYourOffer();
        String publicationId = "APRV2506000042";
        search.setPublicationID(publicationId);

        search.selectInsurer(TEST_INSURER);
        log().image("Before search publication", takeScreenshot());
        search.search();
        log().image("After search publication", takeScreenshot());
        // Select and open publication from results list
        log.info("Select and open publication from results list");

        PublicationCreation publicationCreation = new PublicationCreation();
        String publicationRptName = "REPORT AUTOMATIO VEH 2";
        publicationCreation.selectPublication(publicationRptName,"Cerrada");

        filesActions.waitButtonAndClick(RESULTS_REPORT);
        return filesActions.reportToDownload(Reports.REPORTS_PUBLICATION_RESULTS);

    }

    public boolean validateOffersReport(AolWebUser user) {
        log.info("validating Offers report");
        login.logIn(user);
        String currentURL = getDriver().getCurrentUrl();
        String expectedURL = "/adverts/detail/APRV2506000042";
        currentURL = currentURL.replace("/dashboard",expectedURL);
        getDriver().get(currentURL);


        filesActions.waitButtonAndClick(OFFERS_REPORT);

        return filesActions.reportToDownload(Reports.REPORTS_PUBLICATION_OFFERS);

    }

    public boolean validateViewsReport(AolWebUser user) {
        log.info("validating Views report");
        login.logIn(user);
        String currentURL = getDriver().getCurrentUrl();
        String expectedURL = "/adverts/detail/APRV2407000043";
        currentURL = currentURL.replace("/dashboard",expectedURL);
        getDriver().get(currentURL);

        log.info("waiting for all information to be available");
        waitForElementPresence(getElement(By.xpath(RESULTS_REPORT)));
        filesActions.waitButtonAndClick(VIEWS_REPORT);

        return filesActions.reportToDownload(Reports.REPORTS_PUBLICATION_VIEWS);
    }

}
