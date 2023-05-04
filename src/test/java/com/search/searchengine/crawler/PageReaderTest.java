package com.search.searchengine.crawler;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PageReaderTest {

    private PageReader pageReader;

    public PageReaderTest(){
        pageReader = new PageReader("https://es.wikipedia.org/wiki/Red_de_computadoras", 1, URLWikipediaSanitizer.getInstance());
    }

    @Test
    void shouldReturnValidURL(){
        pageReader.initializeRead();
        Collection<String> l = pageReader.getPathQueue().stream().limit(10).toList();
        for(String s : l){
            try{
                URL url = new URL(s);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                String um = urlConnection.getResponseMessage();
                um.length();
            }catch(Exception e){
                fail("No se pudo establecer conexión con el url " + s);
            }
        }
    }

    @Test
    void initializeRead() {
        pageReader.initializeRead();
        Collection l = pageReader.getPathQueue();
        for(Object page : l){
            System.out.println("[x]" + page);
        }
    }

    @Test
    void read() {
    }
}