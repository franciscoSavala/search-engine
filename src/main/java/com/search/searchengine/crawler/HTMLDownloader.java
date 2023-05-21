package com.search.searchengine.crawler;

import org.springframework.stereotype.Component;

import javax.swing.text.html.HTML;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

@Component
public class HTMLDownloader{
    public static String readHtml(String urlPath){
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(urlPath);
            URLConnection urlConnection = url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while((line = reader.readLine()) != null){
                sb.append(line);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

}
