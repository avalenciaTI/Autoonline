package com.solera.global.qa.template.web.behavior.pages.reports.Inventory;
import com.solera.global.qa.taf.web.tools.webdriver.BrowserPage;
import com.solera.global.qa.template.web.behavior.data.timeouts.Timeouts;
import com.solera.global.qa.template.web.behavior.data.tools.TestDateGenerator;
import com.solera.global.qa.template.web.behavior.data.types.AolWebUser;
import com.solera.global.qa.template.web.behavior.pages.componentpages.CommonComponents;
import com.solera.global.qa.template.web.behavior.pages.componentpages.DownloadManager;
import com.solera.global.qa.template.web.behavior.pages.componentpages.FilesCompare;
import com.solera.global.qa.template.web.behavior.pages.componentpages.InventorySearch;
import com.solera.global.qa.template.web.behavior.pages.componentpages.TransferSearch;
import com.solera.global.qa.template.web.behavior.pages.componentpages.enums.Reports;
import com.solera.global.qa.template.web.behavior.pages.componentpages.submenu.ReportsOptions;
import com.solera.global.qa.template.web.behavior.pages.loginpage.LogInPage;
import com.solera.global.qa.template.web.behavior.pages.menupage.MenuPage;
import com.solera.global.qa.template.web.behavior.pages.payments.Insurers;
import com.solera.global.qa.template.web.behavior.pages.payments.Supplier;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.function.Consumer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReportsInventory extends BrowserPage {

    public static final String INVENTORY_REPORT_BUTTON =
            "//button[@type='button'][contains(.,'Reporte')]";
    private static final String PRESERVED_REPORTS_DIR = "target/test-classes/downloads/preserved";
    private static final String BASELINE_RESOURCE_DIR = "src/test/resources/attachments/reportCompare";
    public static final String ADVANCED_INVENTORY_REPORT_SECTION =
            "//form[@id='search-form-cases'][//label[text()='Tipo de reporte']]";
    private static final String REPORT_CITY = "CIUDAD DE MEXICO";
    private static final String REPORT_ORIGIN = "CIUDAD DE MEXICO";
    private static final String REPORT_MANUFACTURER = "NISSAN";
    private static final String REPORT_VEHICLE_TYPE = "NP300";
    private static final String REPORT_MODEL = "2024";
    private static final String REPORT_UNIT_TYPE = "Pick Up";
    private static final String REPORT_STATUS = "En tránsito";

    private final LogInPage login = new LogInPage();
    private final FilesCompare filesActions = new FilesCompare();
    private final DownloadManager downloadManager = new DownloadManager();

    public ReportsInventory() {
        super();
    }

    public boolean validateGeneralInventoryReport(AolWebUser user) {
        return executeInventoryReport(user, search -> {
            search.selectReportType("reportType", "Entradas");
            search.selectInsurerMarket(Insurers.ALL);
            search.selectSupplierCorralon(Supplier.ALL);
            search.selectCorralonSupplierBranch(Supplier.ALL);
        }, Reports.REPORTS_INVENTORY_GENERAL);
    }

    public boolean validateTransferReportByInsurer(AolWebUser user) {
        return executeInventoryReport(user, search -> {
            search.selectInsurerMarket(Insurers.ANA_INSURER);
            search.selectSupplierCrane(Supplier.ALL);
            search.selectCraneSupplierBranch(Supplier.ALL);
            search.selectSupplierCorralon(Supplier.ALL);
            search.selectCorralonSupplierBranch(Supplier.ALL);
        }, Reports.REPORTS_TRANSFERS_BY_INSURER);
    }

    public boolean validateTransferReportByProvider(AolWebUser user) {
        return executeInventoryReport(user, search -> {
            search.selectInsurerMarket(Insurers.ALL);
            search.selectSupplierCrane(Supplier.GRUAS_AUTOONLINE);
            search.selectCraneSupplierBranch(Supplier.ALL);
            search.selectCorralonSupplierBranch(Supplier.ALL);
        }, Reports.REPORTS_TRANSFERS_BY_PROVIDER);
    }

    public boolean validateTransferReportByBranch(AolWebUser user) {
        return executeInventoryReport(user, search -> {
            search.selectInsurerMarket(Insurers.AIG);
            search.selectSupplierCrane(Supplier.ALL);
            search.selectCraneSupplierBranch(Supplier.QA_REGRESION);
            search.selectSupplierCorralon(Supplier.ALL);
        }, Reports.REPORTS_TRANSFERS_BY_BRANCH);
    }

    public boolean validateTransferReportByCity(AolWebUser user) {
        return executeInventoryReport(user, search -> {
            search.selectInsurerMarket(Insurers.AIG);
            search.selectSupplierCrane(Supplier.ALL);
            search.selectCraneSupplierBranch(Supplier.ALL);
            search.setCityTextFilter("city", REPORT_CITY);
            search.selectSupplierCorralon(Supplier.ALL);
            search.selectCorralonSupplierBranch(Supplier.ALL);
            
        }, Reports.REPORTS_TRANSFERS_BY_CITY);
    }

    public boolean validateTransferReportByOrigin(AolWebUser user) {
        return executeInventoryReport(user, search -> {
            search.selectInsurerMarket(Insurers.ALL);
            search.selectSupplierCrane(Supplier.ALL);
            search.selectCraneSupplierBranch(Supplier.ALL);
            search.selectOriginReportDropdown("origin", REPORT_ORIGIN);
        }, Reports.REPORTS_TRANSFERS_BY_ORIGIN);
    }

    public boolean validateTransferReportByManufacturer(AolWebUser user) {
        return executeInventoryReport(user, search -> {
            search.selectInsurerMarket(Insurers.ANA_INSURER);
            search.selectSupplierCrane(Supplier.ALL);
            search.selectCraneSupplierBranch(Supplier.ALL);
            search.setManufacturerTextFilter("manufacturer", REPORT_MANUFACTURER);
            search.selectSupplierCorralon(Supplier.ALL);
            search.selectCorralonSupplierBranch(Supplier.ALL);
        }, Reports.REPORTS_TRANSFERS_BY_MANUFACTURER);
    }

    public boolean validateTransferReportByType(AolWebUser user) {
        return executeInventoryReport(user, search -> {
            search.selectInsurerMarket(Insurers.ALL);
            search.selectSupplierCrane(Supplier.ALL);
            search.selectCraneSupplierBranch(Supplier.ALL);
            search.setTypeTextFilter("vehicleType", REPORT_VEHICLE_TYPE);
            search.selectSupplierCorralon(Supplier.ALL);
            search.selectCorralonSupplierBranch(Supplier.ALL);
        }, Reports.REPORTS_TRANSFERS_BY_TYPE);
    }

    public boolean validateTransferReportByModel(AolWebUser user) {
        return executeInventoryReport(user, search -> {
            search.selectInsurerMarket(Insurers.ANA_INSURER);
            search.selectSupplierCrane(Supplier.ALL);
            search.selectCraneSupplierBranch(Supplier.ALL);
            search.setModelTextFilter("model", REPORT_MODEL);
            search.selectSupplierCorralon(Supplier.ALL);
            search.selectCorralonSupplierBranch(Supplier.ALL);
        }, Reports.REPORTS_TRANSFERS_BY_MODEL);
    }

    public boolean validateTransferReportByUnitType(AolWebUser user) {
        return executeInventoryReport(user, search -> {
            search.selectInsurerMarket(Insurers.ATLAS_INSURER);
            search.selectSupplierCrane(Supplier.ALL);
            search.selectCraneSupplierBranch(Supplier.ALL);
            search.selectTypeUnitReportDropdown("unitType", REPORT_UNIT_TYPE);
            search.selectSupplierCorralon(Supplier.ALL);
            search.selectCorralonSupplierBranch(Supplier.ALL);
        }, Reports.REPORTS_TRANSFERS_BY_UNIT_TYPE);
    }

    public boolean validateTransferReportByDates(AolWebUser user) {
        String startDate = TestDateGenerator.todayPlusDays(-30);
        String endDate = TestDateGenerator.todayPlusDays(0);
        return executeInventoryReport(user, search -> {
            search.selectInsurerMarket(Insurers.ANA_INSURER);
            search.selectSupplierCrane(Supplier.ALL);
            search.selectCraneSupplierBranch(Supplier.ALL);
            search.setReportDateRange(startDate, endDate);
            search.selectSupplierCorralon(Supplier.ALL);
            search.selectCorralonSupplierBranch(Supplier.ALL);
        }, Reports.REPORTS_TRANSFERS_BY_DATES);
    }

    public boolean validateTransferReportByStatus(AolWebUser user) {
        return executeInventoryReport(user, search -> {
            search.selectInsurerMarket(Insurers.ASEGURADORA_PRUEBAS);
            search.selectSupplierCrane(Supplier.ALL);
            search.selectCraneSupplierBranch(Supplier.ALL);
            search.selectTransferStatus(REPORT_STATUS);
            search.selectSupplierCorralon(Supplier.ALL);
            search.selectCorralonSupplierBranch(Supplier.ALL);
        }, Reports.REPORTS_TRANSFERS_BY_STATUS);
    }

    public boolean validateInventoryAdvancedReport(AolWebUser user) {
        log.info("Validating advanced inventory report section for each report type");
        login.logIn(user);
        new MenuPage().clickReports(ReportsOptions.INVENTORY);

        InventorySearch search = new InventorySearch();
        String[] reportTypes = {"Entradas", "Salidas", "Vehículos por ingresar"};

        for (String reportType : reportTypes) {
            log.info("Selecting report type: {}", reportType);
            search.selectReportType("reportType", reportType);
            log().image("After selecting report type: " + reportType, takeScreenshot());

            WebElement advancedSection = getBrowser().getDriver().findElement(By.xpath(ADVANCED_INVENTORY_REPORT_SECTION));
            if (!waitForElementVisibility(advancedSection, Timeouts.LOAD_ELEMENT)) {
                log.error("Advanced inventory report section NOT visible after selecting '{}'", reportType);
                return false;
            }
            log.info("Advanced inventory report section is visible for '{}'", reportType);
        }

        return true;
    }
    
    
    public boolean validateInventoryOutgoingReport(AolWebUser user) {
        return executeInventoryReport(user, search -> {
            search.selectReportType("reportType", "Salidas");
            search.selectInsurerMarket(Insurers.ALL);
            search.selectSupplierCorralon(Supplier.ALL);
            search.selectCorralonSupplierBranch(Supplier.ALL);
        }, Reports.REPORTS_INVENTORY_EXITS);
    }

    public boolean validateInventoryPendingEntryReportVehicles (AolWebUser user) {
        return executeInventoryReport(user, search -> {
            search.selectReportType("reportType", "Vehículos por ingresar");
            search.selectInsurerMarket(Insurers.ALL);
            search.selectSupplierCorralon(Supplier.ALL);
            search.selectCorralonSupplierBranch(Supplier.ALL);
            search.selectSupplierCrane(Supplier.ALL);
            search.selectCraneSupplierBranch(Supplier.ALL);
        }, Reports.REPORTS_INVENTORY_PENDING_ENTRY_VEHICLES);
    }

    @Deprecated
    public boolean ValidateGeneralInventoryReport(AolWebUser user) {
        return validateGeneralInventoryReport(user);
    }

    private boolean executeInventoryReport(AolWebUser user, Consumer<InventorySearch> filterSetup, Reports report) {
        log.info("Validating inventory report: {}", report.name());
        login.logIn(user);
        new MenuPage().clickReports(ReportsOptions.INVENTORY);

        InventorySearch search = new InventorySearch();
        filterSetup.accept(search);

        log().image("Before search inventory", takeScreenshot());
        search.search();
        log().image("After search inventory", takeScreenshot());

        if (!search.waitForInventoryResults()) {
            log.error("No inventory displayed in results grid");
            return false;
        }

        filesActions.waitButtonAndClick(INVENTORY_REPORT_BUTTON);
        return validateReportDownload(report);
    }

    private boolean validateReportDownload(Reports report) {
        String expectedPartialName = report.getExpectedName();
        String extension = report.getExtensionFile();
        File downloaded = downloadManager.getDownloadedFileByPartialName(
                expectedPartialName, extension, 40);

        if (downloaded == null || !downloaded.exists()) {
            log.error("Inventory report file was not downloaded. Searched in: {} and user Downloads/Descargas",
                    CommonComponents.getDownloadDir());
            return false;
        }

        log.info("Inventory report downloaded: {} (expected partial name: {})",
                downloaded.getName(), expectedPartialName);
        log.info("Full path: {}", downloaded.getAbsolutePath());

        File preservedCopy = preserveDownloadedReport(downloaded, report);
        log.info("Preserved copy for manual review at: {}", preservedCopy.getAbsolutePath());

        File baseline = resolveBaselineFile(report);
        if (!baseline.exists()) {
            log.warn("Baseline not found for {}. Copy preserved file to: {}/{}",
                    report.name(), BASELINE_RESOURCE_DIR, baseline.getName());
            log.warn("Do not place baselines under target/ — mvn clean deletes that folder.");
            return true;
        }

        log.info("Comparing download against baseline: {}", baseline.getAbsolutePath());
        return filesActions.compareDocs(
                downloaded.getAbsolutePath(),
                baseline.getAbsolutePath(),
                report.getRows(),
                report.getColumns());
    }

    private File preserveDownloadedReport(File downloaded, Reports report) {
        File preservedDir = new File(PRESERVED_REPORTS_DIR, report.name());
        if (!preservedDir.exists()) {
            preservedDir.mkdirs();
        }

        File preservedCopy = new File(preservedDir, downloaded.getName());
        try {
            Files.copy(downloaded.toPath(), preservedCopy.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("Could not preserve downloaded report at {}", preservedCopy.getAbsolutePath(), e);
            return downloaded;
        }
        return preservedCopy;
    }

    private File resolveBaselineFile(Reports report) {
        String baselineName = new File(report.getFileNamePath()).getName();
        String[] candidatePaths = {
                report.getFileNamePath(),
                BASELINE_RESOURCE_DIR + "/" + baselineName,
                "autoonline-web/" + BASELINE_RESOURCE_DIR + "/" + baselineName
        };

        for (String path : candidatePaths) {
            File candidate = new File(path);
            if (candidate.exists()) {
                return candidate;
            }
        }
        return new File(BASELINE_RESOURCE_DIR + "/" + baselineName);
    }

}
