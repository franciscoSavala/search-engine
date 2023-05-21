package com.search.searchengine.crawler;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class WikipediaSanitizerTest {

    @Autowired
    @Qualifier("wikipediaSanitizer")
    private SanitizeStrategy sanitizeStrategy;

    private static final String html = "<div> Esto no debería ir <div class=\"mw-content-container\">" +
            "<h2><span class=\"mw-headline\" id=\"Conceptos_relacionados\">Conceptos relacionados</span><span class=\"mw-editsection\"><span class=\"mw-editsection-bracket\">[</span>" +
            "<a href=\"/w/index.php?title=Comunicaci%C3%B3n_institucional&amp;action=edit&amp;section=2\" title=\"Editar sección: Conceptos relacionados\">editar</a>" +
            "<span class=\"mw-editsection-bracket\">]</span></span></h2>" +
            "<p>Es stitucional de las " +
            "<a href=\"/wiki/Relaciones_p%C3%BAblicas\" title=\"Relaciones públicas\">relaciones públicas</a>" +
            ", el " +
            "<a href=\"/wiki/Marketing\" class=\"mw-redirect\" title=\"Marketing\">marketing</a>" +
            ", " +
            "<a href=\"/wiki/Comunicaci%C3%B3n_social\" class=\"mw-redirect\" title=\"Comunicación social\">comunicación social</a>" +
            "  y la " +
            "<a href=\"/wiki/Publicidad\" title=\"Publicidad\">publicidad</a>. El marketing estudia.\n" +
            "</p></div></div>";

    private static final String content = "Conceptos relacionados[editar] Es stitucional de las relaciones públicas, el marketing, comunicación social y la publicidad. El marketing estudia.";

    @Test
    public void shouldObtainContentText(){
        String sanitized = sanitizeStrategy.getSanitizedContent(html);
        assertEquals(content, sanitized);
    }


    @BeforeEach
    void setUp() {
    }
}