package com.example.authbackend.scheduler;

import org.apache.commons.io.FileUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class LogClearScheduler {

    private static final String LOG_DIRECTORY = "logs";
    private static final String LOG_FILE = "Main.log";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd_HHmmss");

    @Scheduled(fixedRate = 600_000) // 10 minutes in milliseconds
    public void zipLogs() {
        File logDirectory = new File(LOG_DIRECTORY);
        File logFile = new File(logDirectory, LOG_FILE);
        if (logFile.exists()) {
            try {
                String timestamp = DATE_FORMAT.format(new Date());
                String zipFileName = String.format("%s_%s.zip", LOG_FILE, timestamp);
                File zipFile = new File(logDirectory, zipFileName);

//                zipLogFile(logFile, zipFile);
                clearLogFile(logFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void zipLogFile(File sourceFile, File zipFile) throws IOException {
        try (ZipOutputStream zipOut = new ZipOutputStream(FileUtils.openOutputStream(zipFile))) {
            ZipEntry zipEntry = new ZipEntry(sourceFile.getName());
            zipOut.putNextEntry(zipEntry);
            FileUtils.copyFile(sourceFile, zipOut);
            zipOut.closeEntry();
        }
    }
    private void clearLogFile(File logFile) throws IOException {
        try (FileWriter writer = new FileWriter(logFile)) {
            writer.write("");
        }
    }
}

