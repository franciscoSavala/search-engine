package com.search.searchengine.service;

import com.search.searchengine.crawler.PageReader;
import com.search.searchengine.crawler.NoneSanitize;
import com.search.searchengine.crawler.SanitizeStrategy;
import com.search.searchengine.crawler.WikipediaSanitizer;
import com.search.searchengine.dto.PageDTO;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Optional;

@Service
public class CrawlerService {

    private HashMap<String, SanitizeStrategy> sanitizers;
    @Autowired
    private PageService pageService;

    @Autowired
    private ApplicationContext context;


    @PostConstruct
    public void init(){
        sanitizers = new HashMap<>();
        sanitizers.put("wikipedia", context.getBean(WikipediaSanitizer.class));
    }


    public void indexUrl(String url, int depth){
        try {

            // saber q sanitizer usar dependiendo el url q le hayan pasado
            URL urlObject = new URL(url);
            String host = urlObject.getHost();
            String[] domains = host.split("\\.");


            SanitizeStrategy strategy = context.getBean(NoneSanitize.class);
            for(String domain : domains){
                if(sanitizers.containsKey(domain)){
                    strategy = sanitizers.get(domain);
                    break;
                }
            }

            // comenzar el indexado
            PageReader pageReader = new PageReader(url, depth, strategy);
            Optional<PageDTO> p = pageReader.readOne();
            while(p.isPresent()){
                PageDTO page = p.get();
                pageService.savePage(page.getHtml(), page.getPath(), strategy);
                p = pageReader.readOne();
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

    }
}
