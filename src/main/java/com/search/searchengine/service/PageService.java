package com.search.searchengine.service;

import com.search.searchengine.crawler.SanitizeStrategy;
import com.search.searchengine.entity.Page;
import com.search.searchengine.repository.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PageService {
    @Autowired
    private PageRepository repository;

    /**
     * Guarda una página html junto con su url. Si la página ya fue indexda, la ignora.
     * Si el url existe, borra el registro que lo contiene. Obtiene contenido de la
     * página y la guarda en el registro.
     * @param html el html a indexar
     * @param url el url donde se obtuvo por útlima vez el html
     * @return la página guardada
     */
    @Transactional
    public Page savePage(String html, String url, SanitizeStrategy strategy){
        String hash = getHash(html);

        Optional<Page> op = repository.findById(hash);
        if(op.isPresent()) return op.get();

        Optional<String> id = repository.getIdByUrl(url);
        if(id.isPresent()) repository.deleteById(id.get());
        String content = strategy.getSanitizedContent(html);
        Page page = Page.builder()
                .id(hash)
                .content(content)
                .url(url)
                .build();
        return repository.save(page);
    }


    public boolean exists(String html){
        return repository.findById(getHash(html)).isPresent();
    }

    public String getHash(String text){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md.update(text.getBytes());
        byte[] hash = md.digest();

        StringBuilder sb = new StringBuilder();
        for(byte b : hash){
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

}
