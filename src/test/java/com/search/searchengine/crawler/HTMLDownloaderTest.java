package com.search.searchengine.crawler;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class HTMLDownloaderTest {
    @Test
    void readHtml() {
        Process process = null;
        try{
            process = Runtime.getRuntime().exec("python -u \"C:\\Users\\tobit\\OneDrive - frc.utn.edu.ar\\FranUni\\frameworks\\spring\\search-engine\\src\\test\\java\\com\\search\\searchengine\\crawler\\resources\\simpleapi.py\"");
            process.waitFor(3000, TimeUnit.MILLISECONDS);
            String path = "http://localhost:5000/";
            String htmlGenerated = HTMLDownloader.readHtml(path);
            assertEquals("<b style='color: red;'> HOLAAAAA! </b>", htmlGenerated);
            process.destroy();

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
           if(process != null) process.destroy();
        }
        
        
    }

}