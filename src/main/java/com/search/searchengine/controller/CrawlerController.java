package com.search.searchengine.controller;

import com.search.searchengine.service.CrawlerService;
import com.search.searchengine.util.IndexadoRespuesta;
import com.search.searchengine.util.ResponseBodyCrawler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
public class CrawlerController {

    @Autowired
    private CrawlerService crawlerService;

    @GetMapping("health")
    public ResponseEntity<String> health(){
        return ResponseEntity.ok("OK!");
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity indexUrl(@RequestParam String url,
                                           @RequestParam Integer depth){
        crawlerService.indexUrl(url, depth);
        return new ResponseEntity<>(new ResponseBodyCrawler(HttpStatus.OK, "indexado"), HttpStatus.OK);
    }
}
