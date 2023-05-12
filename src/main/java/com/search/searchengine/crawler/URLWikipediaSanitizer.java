package com.search.searchengine.crawler;


import java.util.Optional;
import java.util.regex.Pattern;

public final class URLWikipediaSanitizer implements URLSanitizeStrategy{

    private static final URLWikipediaSanitizer instance = new URLWikipediaSanitizer();

    private URLWikipediaSanitizer(){
    }

    public static URLSanitizeStrategy getInstance() {
        return instance;
    }

    @Override
    public Optional<String> getURLSanitized(String url) {
        if(!Pattern.compile("^/wiki/\\S*").matcher(url).matches()){
            return Optional.empty();
        }
        return Optional.of("https://es.wikipedia.org" + url);
    }
}
