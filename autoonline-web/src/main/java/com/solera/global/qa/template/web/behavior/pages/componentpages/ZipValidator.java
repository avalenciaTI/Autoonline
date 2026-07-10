package com.solera.global.qa.template.web.behavior.pages.componentpages;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ZipValidator {

    private final DownloadManager downloadManager;

    public ZipValidator() {
        this.downloadManager = new DownloadManager();
    }

    /**
     * Waits for a ZIP file matching the partial name to be downloaded and returns it.
     *
     * @param partialFileName partial name of the ZIP file to wait for
     * @param timeoutSeconds  max seconds to wait for the download
     * @return the downloaded File, or null if not found within the timeout
     */
    public File waitAndGetDownloadedZip(String partialFileName, int timeoutSeconds) {
        File downloaded = downloadManager.getDownloadedFileByPartialName(partialFileName, ".zip", timeoutSeconds);
        if (downloaded == null || !downloaded.exists()) {
            log.error("ZIP file was not downloaded. Searched for partial name: {} in {} and user Downloads/Descargas",
                    partialFileName, CommonComponents.getDownloadDir());
            return null;
        }
        log.info("ZIP file downloaded: {} (full path: {})", downloaded.getName(), downloaded.getAbsolutePath());
        return downloaded;
    }

    /**
     * Unzips the given ZIP file into a subdirectory named after the ZIP file (without extension)
     * under the same parent directory.
     *
     * @param zipFile the ZIP file to extract
     * @return the output directory where files were extracted, or null if extraction failed
     */
    public File unzipToDirectory(File zipFile) {
        String outputDirPath = zipFile.getParent() + "/" + removeExtension(zipFile.getName());
        File outputDir = new File(outputDirPath);

        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile))) {
            ZipEntry entry;
            int totalEntries = 0;
            while ((entry = zis.getNextEntry()) != null) {
                String entryName = entry.getName();
                File entryFile = new File(outputDir, entryName);

                if (entry.isDirectory()) {
                    entryFile.mkdirs();
                } else {
                    entryFile.getParentFile().mkdirs();
                    java.nio.file.Files.copy(zis, entryFile.toPath(),
                            java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                }
                zis.closeEntry();
                totalEntries++;
            }
            log.info("ZIP extracted to: {} ({} entries)", outputDir.getAbsolutePath(), totalEntries);
            return outputDir;
        } catch (IOException e) {
            log.error("Failed to unzip file: {}", zipFile.getAbsolutePath(), e);
            return null;
        }
    }

    /**
     * Validates that every expected case ID appears as either a subdirectory or a file name
     * inside the extracted directory.
     *
     * @param extractedDir  the directory where the ZIP was extracted
     * @param expectedCaseIds list of case IDs expected to be present
     * @return true if all expected case IDs are present, false otherwise
     */
    public boolean validateExtractedFoldersContainCaseIds(File extractedDir, List<String> expectedCaseIds) {
        if (extractedDir == null || !extractedDir.exists() || !extractedDir.isDirectory()) {
            log.error("Extracted directory does not exist: {}", extractedDir);
            return false;
        }

        File[] extractedItems = extractedDir.listFiles();
        if (extractedItems == null || extractedItems.length == 0) {
            log.error("Extracted directory is empty: {}", extractedDir.getAbsolutePath());
            return false;
        }

        Set<String> extractedNames = new HashSet<>();
        for (File item : extractedItems) {
            extractedNames.add(item.getName());
            log.info("Found extracted item: {} (isDirectory: {})", item.getName(), item.isDirectory());
        }

        boolean allFound = true;
        for (String caseId : expectedCaseIds) {
            boolean found = extractedNames.contains(caseId);
            log.info("Case ID '{}' {} in extracted folder", caseId, found ? "found" : "NOT found");
            if (!found) {
                allFound = false;
            }
        }

        if (allFound) {
            log.info("All {} expected case IDs found in extracted folder", expectedCaseIds.size());
        } else {
            log.error("Not all expected case IDs were found. Expected: {}, Found: {}",
                    expectedCaseIds, extractedNames);
        }
        return allFound;
    }

    /**
     * Validates ZIP download, extracts it, and checks that all expected case IDs
     * are present in the extracted content.
     *
     * @param partialFileName partial name of the ZIP file to download
     * @param timeoutSeconds  max seconds to wait for download
     * @param expectedCaseIds case IDs expected to be inside the ZIP
     * @return true if all validation steps pass, false otherwise
     */
    public boolean validateZipDownloadAndContents(String partialFileName, int timeoutSeconds,
            List<String> expectedCaseIds) {
        log.info("Starting ZIP validation for partial name: {}", partialFileName);

        File zipFile = waitAndGetDownloadedZip(partialFileName, timeoutSeconds);
        if (zipFile == null) {
            return false;
        }

        File extractedDir = unzipToDirectory(zipFile);
        if (extractedDir == null) {
            return false;
        }

        return validateExtractedFoldersContainCaseIds(extractedDir, expectedCaseIds);
    }

    private String removeExtension(String fileName) {
        int lastDot = fileName.lastIndexOf('.');
        return (lastDot > 0) ? fileName.substring(0, lastDot) : fileName;
    }
}