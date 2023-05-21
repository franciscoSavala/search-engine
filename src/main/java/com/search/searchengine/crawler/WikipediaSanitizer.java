package com.search.searchengine.crawler;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Qualifier("wikipediaSanitizer")
public final class WikipediaSanitizer implements SanitizeStrategy {

    @Override
    public Optional<String> getURLSanitized(String url) {
        if(!Pattern.compile("^/wiki/\\S*").matcher(url).matches()){
            return Optional.empty();
        }
        return Optional.of("https://es.wikipedia.org" + url);
    }

    @Override
    public String getSanitizedContent(String html) {
        Elements content = Jsoup.parse(html).getElementsByClass("mw-content-container");

        StringBuilder sb = new StringBuilder();

        for(Element e : content){
            sb.append(e.text());
        }

        return sb.toString();
    }
}
