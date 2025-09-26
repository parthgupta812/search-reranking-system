package com.searchengine.reranking.model;

import java.util.List;
import java.util.Map;

/**
 * Model representing a search query with hard/soft criteria
 */
public class SearchQuery {
    
    private String naturalLanguageDescription;
    private List<String> hardCriteria;
    private List<String> softCriteria;
    private String configPath;
    private Map<String, Object> filters;
    private int maxResults = 100;
    
    // Vector similarity search parameters
    private List<Double> queryVector;
    private double vectorSimilarityThreshold = 0.7;
    
    // Constructors
    public SearchQuery() {}
    
    public SearchQuery(String description, List<String> hardCriteria, List<String> softCriteria) {
        this.naturalLanguageDescription = description;
        this.hardCriteria = hardCriteria;
        this.softCriteria = softCriteria;
    }
    
    // Getters and Setters
    public String getNaturalLanguageDescription() { return naturalLanguageDescription; }
    public void setNaturalLanguageDescription(String naturalLanguageDescription) {
        this.naturalLanguageDescription = naturalLanguageDescription;
    }
    
    public List<String> getHardCriteria() { return hardCriteria; }
    public void setHardCriteria(List<String> hardCriteria) { this.hardCriteria = hardCriteria; }
    
    public List<String> getSoftCriteria() { return softCriteria; }
    public void setSoftCriteria(List<String> softCriteria) { this.softCriteria = softCriteria; }
    
    public String getConfigPath() { return configPath; }
    public void setConfigPath(String configPath) { this.configPath = configPath; }
    
    public Map<String, Object> getFilters() { return filters; }
    public void setFilters(Map<String, Object> filters) { this.filters = filters; }
    
    public int getMaxResults() { return maxResults; }
    public void setMaxResults(int maxResults) { this.maxResults = maxResults; }
    
    public List<Double> getQueryVector() { return queryVector; }
    public void setQueryVector(List<Double> queryVector) { this.queryVector = queryVector; }
    
    public double getVectorSimilarityThreshold() { return vectorSimilarityThreshold; }
    public void setVectorSimilarityThreshold(double vectorSimilarityThreshold) {
        this.vectorSimilarityThreshold = vectorSimilarityThreshold;
    }
}