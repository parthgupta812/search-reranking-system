package com.searchengine.reranking.model;

import java.util.List;
import java.util.Map;

/**
 * Model representing search results with candidates and metadata
 */
public class SearchResult {
    
    private List<UserProfile> candidates;
    private int totalResults;
    private double executionTimeMs;
    private String queryId;
    private Map<String, Object> metadata;
    private ReRankingMetrics reRankingMetrics;
    
    // Constructors
    public SearchResult() {}
    
    public SearchResult(List<UserProfile> candidates, int totalResults) {
        this.candidates = candidates;
        this.totalResults = totalResults;
    }
    
    // Inner class for re-ranking metrics
    public static class ReRankingMetrics {
        private String strategy;
        private double averageReRankScore;
        private double maxScoreImprovement;
        private int candidatesReRanked;
        
        // Constructors, getters, setters
        public ReRankingMetrics() {}
        
        public String getStrategy() { return strategy; }
        public void setStrategy(String strategy) { this.strategy = strategy; }
        
        public double getAverageReRankScore() { return averageReRankScore; }
        public void setAverageReRankScore(double averageReRankScore) {
            this.averageReRankScore = averageReRankScore;
        }
        
        public double getMaxScoreImprovement() { return maxScoreImprovement; }
        public void setMaxScoreImprovement(double maxScoreImprovement) {
            this.maxScoreImprovement = maxScoreImprovement;
        }
        
        public int getCandidatesReRanked() { return candidatesReRanked; }
        public void setCandidatesReRanked(int candidatesReRanked) {
            this.candidatesReRanked = candidatesReRanked;
        }
    }
    
    // Getters and Setters
    public List<UserProfile> getCandidates() { return candidates; }
    public void setCandidates(List<UserProfile> candidates) { this.candidates = candidates; }
    
    public int getTotalResults() { return totalResults; }
    public void setTotalResults(int totalResults) { this.totalResults = totalResults; }
    
    public double getExecutionTimeMs() { return executionTimeMs; }
    public void setExecutionTimeMs(double executionTimeMs) { this.executionTimeMs = executionTimeMs; }
    
    public String getQueryId() { return queryId; }
    public void setQueryId(String queryId) { this.queryId = queryId; }
    
    public Map<String, Object> getMetadata() { return metadata; }
    public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }
    
    public ReRankingMetrics getReRankingMetrics() { return reRankingMetrics; }
    public void setReRankingMetrics(ReRankingMetrics reRankingMetrics) {
        this.reRankingMetrics = reRankingMetrics;
    }
}