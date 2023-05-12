package com.search.searchengine.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "page")
public class Page {
    @Id
    @Column(length = 255)
    private String id;


    @Column(unique = true, name = "url")
    private String url;

    @Column(columnDefinition = "TEXT")
    private String content;
}
