package com.akshay.urlshortner.controllers;


import com.akshay.urlshortner.entity.UrlMapping;
import com.akshay.urlshortner.services.UrlMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedirectController {

    public RedirectController(UrlMappingService urlMappingService) {
        this.urlMappingService = urlMappingService;
    }

    private UrlMappingService urlMappingService;

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirect(@PathVariable String shortUrl ){
        UrlMapping urlMapping = urlMappingService.getOriginalUrl(shortUrl);
        System.err.println(urlMapping.getOriginalUrl());
        if(urlMapping!=null){
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Location",urlMapping.getOriginalUrl());
            System.err.println(urlMapping.getOriginalUrl());
            return ResponseEntity.status(302).headers(httpHeaders).build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}
