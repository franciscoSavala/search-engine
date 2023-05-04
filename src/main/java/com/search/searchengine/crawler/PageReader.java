package com.search.searchengine.crawler;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
public class PageReader{
    private Queue<String> pathQueue;
    private Integer depth;

    private URLSanitizeStrategy strategy;

    public PageReader(String rootPath, Integer depth){

        this(rootPath, depth, URLNoneSanitize.getInstance());
    }


    public PageReader(String rootPath, Integer depth, URLSanitizeStrategy strategy) {
        this.pathQueue = new LinkedList<>();
        this.pathQueue.add(rootPath);
        this.depth = depth;
        this.strategy = strategy;
    }

    public void initializeRead(){
        int levelSize = 1;
        while(!pathQueue.isEmpty()){
            read(pathQueue.poll());
            levelSize--;
            if(levelSize == 0){
                depth--;
                levelSize = pathQueue.size();
            }
            if(depth == 0) break;

        }
    }

    public void read(String path){
        String html = HTMLDownloader.readHtml(path);
        Matcher matcher = Pattern.compile("href=[\"']([^\"']*)[\"']").matcher(html);
        int pos = 0;
        while(matcher.find(pos)){
            String match = matcher.group();
            // Se elimina el href="--" de la substring
            Optional<String> url = strategy.getURLSanitized(match.substring(6, match.length() - 1));
            if(url.isPresent()){
                pathQueue.add(url.get());
            }
            pos = matcher.start() + match.length();
        }
    }
}
