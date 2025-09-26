package com.searchengine.reranking.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class for API keys and external service endpoints
 */
@Configuration
@ConfigurationProperties(prefix = "search.api")
public class ApiConfiguration {
    
    private String turboPufferApiKey;
    private String voyageApiKey;
    private String openAiApiKey;
    private String turboPufferBaseUrl = "https://api.turbopuffer.com";
    private String voyageBaseUrl = "https://api.voyageai.com";
    private String evaluationEndpoint = "https://api.evaluation.com/score";
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
    // Getters and Setters
    public String getTurboPufferApiKey() { return turboPufferApiKey; }
    public void setTurboPufferApiKey(String turboPufferApiKey) { this.turboPufferApiKey = turboPufferApiKey; }
    
    public String getVoyageApiKey() { return voyageApiKey; }
    public void setVoyageApiKey(String voyageApiKey) { this.voyageApiKey = voyageApiKey; }
    
    public String getOpenAiApiKey() { return openAiApiKey; }
    public void setOpenAiApiKey(String openAiApiKey) { this.openAiApiKey = openAiApiKey; }
    
    public String getTurboPufferBaseUrl() { return turboPufferBaseUrl; }
    public void setTurboPufferBaseUrl(String turboPufferBaseUrl) { this.turboPufferBaseUrl = turboPufferBaseUrl; }
    
    public String getVoyageBaseUrl() { return voyageBaseUrl; }
    public void setVoyageBaseUrl(String voyageBaseUrl) { this.voyageBaseUrl = voyageBaseUrl; }
    
    public String getEvaluationEndpoint() { return evaluationEndpoint; }
    public void setEvaluationEndpoint(String evaluationEndpoint) { this.evaluationEndpoint = evaluationEndpoint; }
}