package com.company.daniel.lind.karlberg;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {
    private static final Logger log = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        log.info("Main initialized");
        setupLogging();
        new ConsoleInput().startUp();

    }

    private static void setupLogging() {
        log.info("Logging initialized");
        String loggingFilePath = "/Users/daniellindkarlberg/IdeaProjects/AddressBookUltimate/log.properties";
        try (FileInputStream fileInputStream = new FileInputStream(loggingFilePath)) {
            LogManager.getLogManager().readConfiguration(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException("Could not load log properties.", e);
        }
    }

}
