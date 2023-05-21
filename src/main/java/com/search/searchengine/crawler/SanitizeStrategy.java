package com.search.searchengine.crawler;


import java.util.Optional;

public interface SanitizeStrategy {
    /**
     *
     * @param url
     * @return optional which isPresent==true when url is valid
     */
    Optional<String> getURLSanitized(String url);

    String getSanitizedContent(String html);
}
