package com.searchengine.reranking.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Model for evaluation API request
 */
public class EvaluationRequest {
    
    @JsonProperty("config_path")
    private String configPath;
    
    @JsonProperty("object_ids")
    private List<String> objectIds;
    
    // Constructors
    public EvaluationRequest() {}
    
    public EvaluationRequest(String configPath, List<String> objectIds) {
        this.configPath = configPath;
        this.objectIds = objectIds;
    }
    
    // Getters and Setters
    public String getConfigPath() { return configPath; }
    public void setConfigPath(String configPath) { this.configPath = configPath; }
    
    public List<String> getObjectIds() { return objectIds; }
    public void setObjectIds(List<String> objectIds) { this.objectIds = objectIds; }
    
    @Override
    public String toString() {
        return String.format("EvaluationRequest{configPath='%s', objectIds=%s}", 
                           configPath, objectIds);
    }
}