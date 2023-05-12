package com.search.searchengine.repository;

import com.search.searchengine.entity.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PageRepository extends JpaRepository<Page, String> {
    @Query(value = "SELECT p.id FROM page p WHERE p.url=?1", nativeQuery = true)
    Optional<String> getIdByUrl(String url);

}
