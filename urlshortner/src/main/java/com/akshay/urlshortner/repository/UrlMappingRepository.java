package com.akshay.urlshortner.repository;

import com.akshay.urlshortner.entity.UrlMapping;
import com.akshay.urlshortner.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UrlMappingRepository extends JpaRepository<UrlMapping,Long> {
    @Query("SELECT u FROM UrlMapping u WHERE  u.shortUrl = :shortUrl")
    UrlMapping findByShortUrl(@Param("shortUrl") String shortUrl);
    List<UrlMapping> findByUser(User user);
}
