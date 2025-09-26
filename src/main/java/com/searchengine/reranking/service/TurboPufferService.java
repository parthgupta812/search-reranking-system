package com.searchengine.reranking.service;

import com.searchengine.reranking.config.ApiConfiguration;
import com.searchengine.reranking.model.UserProfile;
import com.searchengine.reranking.util.VectorUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Service for interacting with TurboPuffer vector database
 */
@Service
public class TurboPufferService {
    
    private static final Logger logger = LoggerFactory.getLogger(TurboPufferService.class);
    private static final String COLLECTION_NAME = "linkedin_data_subset";
    
    @Autowired
    private ApiConfiguration apiConfig;
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    /**
     * Perform vector similarity search in TurboPuffer
     */
    public List<UserProfile> vectorSearch(List<Double> queryVector, int maxResults, Map<String, Object> filters) {
        try {
            String url = String.format("%s/v1/vectors/query", apiConfig.getTurboPufferBaseUrl());
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiConfig.getTurboPufferApiKey());
            
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("collection", COLLECTION_NAME);
            requestBody.put("vector", queryVector);
            requestBody.put("top_k", maxResults);
            
            if (filters != null && !filters.isEmpty()) {
                requestBody.put("filters", filters);
            }
            
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            
            logger.info("Executing vector search with {} dimensions, top_k={}", 
                       queryVector.size(), maxResults);
            
            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
            
            if (response.getStatusCode() == HttpStatus.OK) {
                return parseSearchResults(response.getBody());
            } else {
                logger.error("TurboPuffer search failed with status: {}", response.getStatusCode());
                throw new RuntimeException("TurboPuffer search failed");
            }
            
        } catch (Exception e) {
            logger.error("Error during TurboPuffer vector search", e);
            throw new RuntimeException("Vector search failed", e);
        }
    }
    
    /**
     * Get a specific user profile by ID
     */
    public UserProfile getProfileById(String profileId) {
        try {
            String url = String.format("%s/v1/vectors/get", apiConfig.getTurboPufferBaseUrl());
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiConfig.getTurboPufferApiKey());
            
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("collection", COLLECTION_NAME);
            requestBody.put("id", profileId);
            
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            
            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
            
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return objectMapper.convertValue(response.getBody(), UserProfile.class);
            }
            
            return null;
            
        } catch (Exception e) {
            logger.error("Error retrieving profile by ID: {}", profileId, e);
            return null;
        }
    }
    
    /**
     * Batch get multiple profiles by IDs
     */
    public List<UserProfile> getProfilesByIds(List<String> profileIds) {
        return profileIds.stream()
                .map(this::getProfileById)
                .filter(profile -> profile != null)
                .toList();
    }
    
    @SuppressWarnings("unchecked")
    private List<UserProfile> parseSearchResults(Map<String, Object> responseBody) {
        try {
            List<Map<String, Object>> results = (List<Map<String, Object>>) responseBody.get("data");
            
            return results.stream()
                    .map(this::mapToUserProfile)
                    .toList();
                    
        } catch (Exception e) {
            logger.error("Error parsing TurboPuffer search results", e);
            throw new RuntimeException("Failed to parse search results", e);
        }
    }
    
    private UserProfile mapToUserProfile(Map<String, Object> data) {
        try {
            UserProfile profile = objectMapper.convertValue(data, UserProfile.class);
            
            // Set relevance score if available
            if (data.containsKey("score")) {
                profile.setRelevanceScore(((Number) data.get("score")).doubleValue());
            }
            
            return profile;
            
        } catch (Exception e) {
            logger.error("Error mapping data to UserProfile", e);
            return null;
        }
    }
}