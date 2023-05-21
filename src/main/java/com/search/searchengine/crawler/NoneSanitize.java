package com.search.searchengine.crawler;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Qualifier("noneSanitize")
public class NoneSanitize implements SanitizeStrategy {

    private NoneSanitize(){
    }

    @Override
    public Optional<String> getURLSanitized(String url) {
        return Optional.of(url);
    }

    @Override
    public String getSanitizedContent(String html) {
        return html;
    }
}
