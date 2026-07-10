package com.solera.global.qa.template.web.behavior.pages.componentpages;

import java.awt.Desktop;
import java.io.File;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DownloadManager {

    public boolean validateDownloadedFileByPartialName(String expectedPartialName, String extension, int timeoutSeconds) {
        List<File> downloadDirs = getCandidateDownloadDirectories();
        int waitedSeconds = 0;

        while (waitedSeconds < timeoutSeconds) {
            File matchedFile = findDownloadedFile(downloadDirs, expectedPartialName, extension);
            if (matchedFile != null && isDownloadCompleted(matchedFile)) {
                log.info("File downloaded correctly: {}", matchedFile.getAbsolutePath());
                return true;
            }

            log.info("Waiting for file containing '{}' with extension '{}' in {}",
                    expectedPartialName, extension, downloadDirs);
            waitOneSecond();
            waitedSeconds++;
        }

        log.error("No completed downloaded file found for partial name '{}' and extension '{}'",
                expectedPartialName, extension);
        return false;
    }

    public void assertDownloadedFileByPartialName(String expectedPartialName, String extension, int timeoutSeconds) {
        boolean downloaded = validateDownloadedFileByPartialName(expectedPartialName, extension, timeoutSeconds);
        if (!downloaded) {
            throw new AssertionError("Downloaded file validation failed for partial name: "
                    + expectedPartialName + " and extension: " + extension);
        }
    }

    public File getDownloadedFileByPartialName(String expectedPartialName, String extension, int timeoutSeconds) {
        return waitForDownloadedFile(expectedPartialName, extension, timeoutSeconds);
    }

    public void openDownloadedFileByPartialName(String expectedPartialName, String extension, int timeoutSeconds) {
        File fileToOpen = waitForDownloadedFile(expectedPartialName, extension, timeoutSeconds);
        if (fileToOpen == null) {
            throw new AssertionError("Could not open downloaded file for partial name: "
                    + expectedPartialName + " and extension: " + extension);
        }

        if (!Desktop.isDesktopSupported()) {
            throw new AssertionError("Desktop API is not supported in this environment.");
        }

        try {
            Desktop.getDesktop().open(fileToOpen);
            log.info("Downloaded file opened: {}", fileToOpen.getAbsolutePath());
        } catch (Exception e) {
            throw new AssertionError("Failed to open downloaded file: " + fileToOpen.getAbsolutePath(), e);
        }
    }

    private File waitForDownloadedFile(String expectedPartialName, String extension, int timeoutSeconds) {
        int waitedSeconds = 0;
        List<File> downloadDirs = getCandidateDownloadDirectories();

        while (waitedSeconds < timeoutSeconds) {
            File matchedFile = findDownloadedFile(downloadDirs, expectedPartialName, extension);
            if (matchedFile != null && isDownloadCompleted(matchedFile)) {
                return matchedFile;
            }
            waitOneSecond();
            waitedSeconds++;
        }
        return null;
    }

    private File findDownloadedFile(List<File> downloadDirs, String expectedPartialName, String extension) {
        for (File directory : downloadDirs) {
            File matchedFile = findDownloadedFile(directory, expectedPartialName, extension);
            if (matchedFile != null) {
                return matchedFile;
            }
        }
        return null;
    }

    private File findDownloadedFile(File downloadDir, String expectedPartialName, String extension) {
        if (!downloadDir.exists() || !downloadDir.isDirectory()) {
            return null;
        }

        String normalizedExpected = normalizeText(expectedPartialName);
        String normalizedExtension = extension.toLowerCase(Locale.ROOT);
        File[] matches = downloadDir.listFiles((dir, name) -> {
            String lowerName = name.toLowerCase(Locale.ROOT);
            if (!lowerName.endsWith(normalizedExtension) || lowerName.endsWith(".crdownload")) {
                return false;
            }

            String normalizedName = normalizeText(name);
            return normalizedName.contains(normalizedExpected);
        });

        if (matches == null || matches.length == 0) {
            return null;
        }

        File latest = matches[0];
        for (File file : matches) {
            if (file.lastModified() > latest.lastModified()) {
                latest = file;
            }
        }
        return latest;
    }

    private List<File> getCandidateDownloadDirectories() {
        List<File> directories = new ArrayList<>();
        directories.add(new File(CommonComponents.getDownloadDir()));

        String userHome = System.getProperty("user.home");
        if (userHome != null && !userHome.isBlank()) {
            directories.add(new File(userHome + "/Downloads"));
            directories.add(new File(userHome + "/Descargas"));
        }
        return directories;
    }

    private String normalizeText(String text) {
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        normalized = normalized.replaceAll("\\p{M}", "");
        normalized = normalized.replace("_", " ");
        normalized = normalized.toLowerCase(Locale.ROOT);
        return normalized.replaceAll("\\s+", " ").trim();
    }

    private boolean isDownloadCompleted(File downloadedFile) {
        File tempFile = new File(downloadedFile.getAbsolutePath() + ".crdownload");
        if (tempFile.exists()) {
            return false;
        }

        long initialSize = downloadedFile.length();
        waitOneSecond();
        long nextSize = downloadedFile.length();
        return initialSize > 0 && initialSize == nextSize;
    }

    private void waitOneSecond() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Interrupted while waiting for download", e);
        }
    }
}
