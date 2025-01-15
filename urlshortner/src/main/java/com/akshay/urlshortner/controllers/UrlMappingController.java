package com.akshay.urlshortner.controllers;

import com.akshay.urlshortner.dto.ClickEventDTO;
import com.akshay.urlshortner.dto.UrlMappingDTO;
import com.akshay.urlshortner.entity.User;
import com.akshay.urlshortner.services.UrlMappingService;
import com.akshay.urlshortner.services.UserService;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/urls")
public class UrlMappingController {

    public UrlMappingController(UrlMappingService urlMappingService, UserService userService) {
        this.urlMappingService = urlMappingService;
        this.userService = userService;
    }

    private UrlMappingService urlMappingService;
    private UserService userService;


    @PostMapping("/shorten")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createShortUrl(@RequestBody Map<String,String> request
    , Principal principal){
        String originalUrl = request.get("originalUrl");
        User user = userService.findByUsername(principal.getName());
        UrlMappingDTO urlMappingDTO =  urlMappingService.createShortUrl(originalUrl,user);
        return ResponseEntity.ok(urlMappingDTO);
    }
    @GetMapping("/myurls")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUserUrls(Principal principal)
    {
    User user = userService.findByUsername(principal.getName());
    List<UrlMappingDTO> listUrls =  urlMappingService.getUrlsByUser(user);
    return ResponseEntity.ok(listUrls);
    }

    @GetMapping("/analytics/{shortUrl}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ClickEventDTO>> getUrlsAnalytics(
            @PathVariable String shortUrl,@RequestParam ("startDate") String startDate,@RequestParam ("endDate") String endDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime start = LocalDateTime.parse(startDate,formatter);
        LocalDateTime end = LocalDateTime.parse(endDate,formatter);
       List<ClickEventDTO> list =  urlMappingService.getClickEventsByDate(shortUrl,start,end);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/totalClicks")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Map<LocalDate,Long>> getTotalClickByDate(
            Principal principal,@RequestParam ("startDate") String startDate,@RequestParam ("endDate") String endDate) {
        User user = userService.findByUsername(principal.getName());
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate start = LocalDate.parse(startDate,formatter);
        LocalDate end = LocalDate.parse(endDate,formatter);
        Map<LocalDate,Long> totalClicks =  urlMappingService.getTotalClicksByUserAndDate(user,start,end);
        return ResponseEntity.ok(totalClicks);
    }

}
