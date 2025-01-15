package com.akshay.urlshortner.services;


import com.akshay.urlshortner.dto.ClickEventDTO;
import com.akshay.urlshortner.dto.UrlMappingDTO;
import com.akshay.urlshortner.entity.ClickEvent;
import com.akshay.urlshortner.entity.UrlMapping;
import com.akshay.urlshortner.entity.User;
import com.akshay.urlshortner.repository.ClickEventRepository;
import com.akshay.urlshortner.repository.UrlMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UrlMappingService {

    @Autowired
    private UrlMappingRepository urlMappingRepository;
    @Autowired
    private ClickEventRepository clickEventRepository;

    public UrlMappingDTO createShortUrl(String originalUrl, User user) {

        String shortUrl = generateShortUrl();
        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setShortUrl(shortUrl);
        System.err.println(originalUrl);
        urlMapping.setOriginalUrl(originalUrl);
        urlMapping.setUser(user);
        urlMapping.setCreatedDate(LocalDateTime.now());

        UrlMapping savedUrlMapping = urlMappingRepository.save(urlMapping);
        return convertToDTO(savedUrlMapping);
    }

    private UrlMappingDTO convertToDTO(UrlMapping urlMapping){
        UrlMappingDTO urlMappingDTO = new UrlMappingDTO();
        urlMappingDTO.setId(urlMapping.getId());
        urlMappingDTO.setOriginalUrl(urlMapping.getOriginalUrl());
        urlMappingDTO.setShortUrl(urlMapping.getShortUrl());
        urlMappingDTO.setUsername(urlMapping.getUser().getUsername());
        urlMappingDTO.setClickCount(urlMapping.getClickCount());
        urlMappingDTO.setCreatedDate(urlMapping.getCreatedDate());
        return urlMappingDTO;
    }
    private String generateShortUrl() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder shortUrl = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            shortUrl.append(characters.charAt(random.nextInt(characters.length())));
        }
        return shortUrl.toString();
    }

    public List<UrlMappingDTO> getUrlsByUser(User user) {
        return urlMappingRepository.findByUser(user).stream().map(
                this::convertToDTO
        ).collect(Collectors.toUnmodifiableList());
    }

    public List<ClickEventDTO> getClickEventsByDate(String shortUrl, LocalDateTime start, LocalDateTime end) {

        UrlMapping urlMapping = urlMappingRepository.findByShortUrl(shortUrl);
        if (urlMapping!=null){
            return clickEventRepository.findByUrlMappingAndClickDateBetween(urlMapping,start,end)
                    .stream().collect(Collectors.groupingBy(clickEvent -> clickEvent.getClickDate().toLocalDate(),Collectors.counting()))
                    .entrySet().stream().map(entry->{
                        ClickEventDTO clickEventDTO = new ClickEventDTO();
                        clickEventDTO.setClickDate(entry.getKey());
                        clickEventDTO.setCount(entry.getValue());
                        return clickEventDTO;
                    }).collect(Collectors.toUnmodifiableList());

        }
        else {
            return null;
        }
    }

    public Map<LocalDate, Long> getTotalClicksByUserAndDate(User user, LocalDate start, LocalDate end) {
        List<UrlMapping> urlMappings = urlMappingRepository.findByUser(user);
        List<ClickEvent> clickEvents = clickEventRepository.findByUrlMappingInAndClickDateBetween(urlMappings,start.atStartOfDay(),end.plusDays(1).atStartOfDay());
        return  clickEvents.stream()
                .collect(Collectors.groupingBy(clickEvent -> clickEvent.getClickDate().toLocalDate(),Collectors.counting()));
    }

    public UrlMapping getOriginalUrl(String shortUrl) {
        UrlMapping urlMapping = urlMappingRepository.findByShortUrl(shortUrl);
        return urlMapping;
    }
}
