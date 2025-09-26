package com.searchengine.reranking.model;

import java.util.Map;

/**
 * Model for evaluation API response
 */
public class EvaluationResponse {
    
    private double score;
    private String status;
    private Map<String, Object> details;
    private String message;
    
    // Constructors
    public EvaluationResponse() {}
    
    public EvaluationResponse(double score, String status) {
        this.score = score;
        this.status = status;
    }
    
    // Getters and Setters
    public double getScore() { return score; }
    public void setScore(double score) { this.score = score; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public Map<String, Object> getDetails() { return details; }
    public void setDetails(Map<String, Object> details) { this.details = details; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    @Override
    public String toString() {
        return String.format("EvaluationResponse{score=%.3f, status='%s', message='%s'}", 
                           score, status, message);
    }
}