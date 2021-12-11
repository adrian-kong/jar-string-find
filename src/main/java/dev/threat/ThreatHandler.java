package dev.threat;

import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ThreatHandler {

    private static ThreatHandler threatHandler;
    private static final File configFile = new File("words.txt");
    private static final Logger logger = Logger.getLogger("threats");

    private final List<String> searchStrings = new ArrayList<>();

    public ThreatHandler() {
        load();

        threatHandler = this;
    }

    private void load() {
        if (!configFile.exists()) {
            logger.log(Level.CONFIG, "No words.txt found. Searching for urls.");
            searchStrings.add("url");
            return;
        }
        try (BufferedReader reader = Files.newBufferedReader(configFile.toPath())) {
            reader.lines().forEach(searchStrings::add);
        } catch (Exception ex) {
            // handle this exception or nothing to search
            ex.printStackTrace();
        }
    }

    public boolean isSuspicious(String input) {
        // change to equals for case sensitive
        return searchStrings.stream().map(String::toLowerCase).anyMatch(input.toLowerCase()::contains);
    }

    public static ThreatHandler getInstance() {
        if (threatHandler == null) {
            return threatHandler = new ThreatHandler();
        }
        return threatHandler;
    }

}
