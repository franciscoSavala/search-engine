package com.search.searchengine.crawler;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PageReaderTest {

    private PageReader pageReader;
    @Autowired
    @Qualifier("wikipediaSanitizer")
    private SanitizeStrategy strategy;
    public PageReaderTest(){
        pageReader = new PageReader("https://es.wikipedia.org/wiki/Red_de_computadoras", 1, strategy);
    }

    @Test
    void shouldReturnValidURL(){
        pageReader.readOne();
        Collection<String> l = pageReader.getPathQueue().stream().limit(10).toList();
        for(String s : l){
            try{
                URL url = new URL(s);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                int responseCode = urlConnection.getResponseCode();
            }catch(Exception e){
                fail("No se pudo establecer conexión con el url " + s);
            }
        }
    }

    @Test
    void initializeRead() {
        pageReader.readOne();
        Collection l = pageReader.getPathQueue();
        for(Object page : l){
            System.out.println("[x]" + page);
        }
    }

    @Test
    void read() {
    }
}