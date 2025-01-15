package com.akshay.urlshortner.repository;

import com.akshay.urlshortner.entity.UrlMapping;
import com.akshay.urlshortner.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UrlMappingRepository extends JpaRepository<UrlMapping,Long> {

    UrlMapping findByShortUrl(String shortUrl);
    List<UrlMapping> findByUser(User user);
}
