package com.solera.global.qa.template.web.behavior.pages.componentpages;

import com.solera.global.qa.taf.web.tools.webdriver.BrowserPage;
import com.solera.global.qa.template.web.behavior.pages.componentpages.enums.Reports;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;

@Slf4j
public class FilesCompare extends BrowserPage {
    public FilesCompare() {
        super();
    }

    Integer globalErrorStatus;

    public String waitForDownload(String fileName, String extensionFile) {
        String downloadDir = CommonComponents.getDownloadDir();
        File dir = new File(downloadDir);
        for (int waiting = 0; waiting < 12; waiting++) {
            File closestMatch = findClosestFile(dir, fileName, extensionFile);
            if (closestMatch != null) {
                log.info("File found: {}", closestMatch.getAbsolutePath());
                return closestMatch.getAbsolutePath();
            }
            log.info("Waiting for file to be downloaded...");
            sleep(10000);
        }
        log.warn("No matching file found after waiting");
        return null;
    }

    private File findClosestFile(File dir, String fileName, String extensionFile) {
        File[] files = dir.listFiles((d, name) -> name.endsWith(extensionFile));
        if (files != null && files.length > 0) {
            return Arrays.stream(files)
                    .filter(file -> file.getName().contains(fileName))
                    .max(Comparator.comparingInt(file -> calculateFileScore(file, fileName)))
                    .orElse(null);
        }
        return null;
    }

    private int calculateFileScore(File file, String fileName) {
        String name = file.getName();
        int score = 0;
        if (name.startsWith(fileName)) {
            score += 10;
        }
        if (name.contains(fileName)) {
            score += 5;
        }
        return score;
    }

    public boolean compareDocs(String downloaded, String fileNamePath, List<Integer> excludeRows,
            List<Integer> excludeColumns) {
        globalErrorStatus = 0;
        String filePath1 = fileNamePath;
        String filePath2 = downloaded;

        try {
            compareExcelFiles(filePath1, filePath2, excludeRows, excludeColumns);
            File dir = new File(downloaded);
            dir.delete();
        } catch (IOException e) {
            e.getCause();
        }
        return globalErrorStatus <= 0;
    }

    public void compareExcelFiles(String filePath1, String filePath2,List<Integer> excludeRows,
            List<Integer> excludeColumns) throws IOException {

        try (FileInputStream fis1 = new FileInputStream(filePath1);
                FileInputStream fis2 = new FileInputStream(filePath2)) {

            Workbook workbook1 = WorkbookFactory.create(fis1);
            Workbook workbook2 = WorkbookFactory.create(fis2);

            Sheet sheet1 = workbook1.getSheetAt(0);
            Sheet sheet2 = workbook2.getSheetAt(0);

            int rowCount1 = sheet1.getPhysicalNumberOfRows();
            int rowCount2 = sheet2.getPhysicalNumberOfRows();
            int maxRows = Math.max(rowCount1, rowCount2);
            log.info("maxRow file 1: {}, maxRow file 2: {}", rowCount1, rowCount2);


            for (int i = 0; i < maxRows; i++) {
                Row row1 = (i < rowCount1) ? sheet1.getRow(i) : null;
                Row row2 = (i < rowCount2) ? sheet2.getRow(i) : null;

                if (row1 != null && row2 != null && !excludeRows.contains(i)) {
                    compareRows(row1, row2, i,excludeColumns);
                }
            }
            if (rowCount1!=rowCount2) {
                log.info("Files row mismatch, maxRow file 1: {}, maxRow file 2: {}", rowCount1, rowCount2);
                globalErrorStatus++;
            }

            workbook1.close();
            workbook2.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void compareRows(Row row1, Row row2, int rowIndex, List<Integer> excludeColumns) {
        int maxColumns = Math.max(getMaxColumns(row1), getMaxColumns(row2));
        for (int j = 0; j < maxColumns; j++) {
            Cell cell1 = (row1 != null) ? row1.getCell(j) : null;
            Cell cell2 = (row2 != null) ? row2.getCell(j) : null;

            if (!excludeColumns.contains(j)) {
                compareCells(cell1, cell2, rowIndex, j);
            }
        }
    }

    private void compareCells(Cell cell1, Cell cell2, int rowIndex, int columnIndex) {
        String value1 = (cell1 != null) ? cell1.toString() : "null";
        String value2 = (cell2 != null) ? cell2.toString() : "null";

        if (!value1.equals(value2)) {
            log.info("Difference on row {}, column {}: '{}' vs '{}'", rowIndex + 1, columnIndex + 1, value1, value2);
            globalErrorStatus++;
        }
    }

    private static int getMaxColumns(Row row) {
        return (row != null) ? row.getLastCellNum() : 0;
    }

    public void waitButtonAndClick(String pathToFind) {
        waitForElementPresence(getElement(By.xpath(pathToFind)));
        log.info("element found and waiting to be clickable");
        waitForElementToBeClickable(getElement(By.xpath(pathToFind)));
        log.info("element is clickable");
        click(getElement(By.xpath(pathToFind)));
        log().image("After  Click on button", takeScreenshot());

    }

    public boolean reportToDownload(Reports reportName) {

        FilesCompare filesCompare = new FilesCompare();
        String expectedName = reportName.getExpectedName();
        String extensionFile = reportName.getExtensionFile();

        String fileDownloaded = filesCompare.waitForDownload(expectedName,extensionFile);

        String reportChecked = reportName.getFileNamePath();
        List<Integer> exRows = reportName.getRows();
        List<Integer> exColumns = reportName.getColumns();

        return filesCompare.compareDocs(fileDownloaded,reportChecked, exRows,exColumns);
    }

}
