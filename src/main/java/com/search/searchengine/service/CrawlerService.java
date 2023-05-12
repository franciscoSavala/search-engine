package com.search.searchengine.service;

import com.search.searchengine.crawler.PageReader;
import com.search.searchengine.crawler.URLNoneSanitize;
import com.search.searchengine.crawler.URLSanitizeStrategy;
import com.search.searchengine.crawler.URLWikipediaSanitizer;
import com.search.searchengine.dto.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Optional;

@Service
public class CrawlerService {

    private HashMap<String, URLSanitizeStrategy> sanitizers;
    @Autowired
    private PageService pageService;


    public CrawlerService(){
        sanitizers = new HashMap<>();
        sanitizers.put("wikipedia", URLWikipediaSanitizer.getInstance());
    }


    public void indexUrl(String url, int depth){
        try {

            // saber q sanitizer usar dependiendo el url q le hayan pasado
            URL urlObject = new URL(url);
            String host = urlObject.getHost();
            String[] domains = host.split("\\.");
            URLSanitizeStrategy strategy = URLNoneSanitize.getInstance();
            for(String domain : domains){
                if(sanitizers.containsKey(domain)){
                    strategy = URLWikipediaSanitizer.getInstance();
                    break;
                }
            }

            // comenzar el indexado
            PageReader pageReader = new PageReader(url, depth, strategy);
            Optional<PageDTO> p = pageReader.readOne();
            while(p.isPresent()){
                PageDTO page = p.get();
                pageService.savePage(page.getHtml(), page.getPath());
                p = pageReader.readOne();
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

    }
}
