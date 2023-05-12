package com.search.searchengine.crawler;

import com.search.searchengine.dto.PageDTO;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


//TODO: hacer que funcione en multithreading
@Getter
@Setter
public class PageReader{
    private static final Logger log = LogManager.getLogger(PageReader.class);

    private Queue<String> pathQueue;
    private int depth;
    private int levelSize;


    private URLSanitizeStrategy strategy;

    @Autowired
    public PageReader(String rootPath, Integer depth, URLSanitizeStrategy strategy) {
        this.pathQueue = new LinkedList<>();
        this.pathQueue.add(rootPath);
        this.depth = depth;
        this.strategy = strategy;
        this.levelSize = 1;
    }

    public Optional<PageDTO> readOne(){
        if(pathQueue.isEmpty() || depth == 0) return Optional.empty();
        String next = pathQueue.poll();
        PageDTO p = read(next);
        levelSize--;
        if(levelSize == 0){
            depth--;
            levelSize = pathQueue.size();
        }
        log.info("Readed: " + next);
        return Optional.of(p);
    }

    public PageDTO read(String path){
        String html = HTMLDownloader.readHtml(path);
        Matcher matcher = Pattern.compile("href=[\"']([^\"']*)[\"']").matcher(html);
        while(matcher.find()){
            String match = matcher.group();
            // Se elimina el href="--" de la substring
            Optional<String> url = strategy.getURLSanitized(match.substring(6, match.length() - 1));
            url.ifPresent(s -> pathQueue.add(s));
        }
        return PageDTO.builder().html(html).path(path).build();
    }
}
