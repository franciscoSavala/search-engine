package com.search.searchengine.crawler;

import java.util.Optional;

public class URLNoneSanitize implements URLSanitizeStrategy{
    private static final URLNoneSanitize instance = new URLNoneSanitize();

    private URLNoneSanitize(){
    }

    public static URLSanitizeStrategy getInstance() {
        return instance;
    }

    @Override
    public Optional<String> getURLSanitized(String url) {
        return Optional.of(url);
    }
}
