package com.searchengine.reranking.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Model representing a user profile from TurboPuffer collection
 * Schema matches the document specification with 1024-dim vectors
 */
public class UserProfile {
    
    @JsonProperty("_id")
    private String id;
    
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
    
    @JsonProperty("linkedin_id")
    private String linkedinId;
    
    private String email;
    private String name;
    private String country;
    
    // 1024-dimensional vector from Voyage-3 embedding
    private List<Double> vector;
    
    @JsonProperty("rerankSummary")
    private String rerankSummary;
    
    private List<String> education;
    private List<String> experience;
    
    // Education fields
    @JsonProperty("deg_start_years")
    private List<String> degStartYears;
    
    @JsonProperty("deg_end_years")
    private List<String> degEndYears;
    
    @JsonProperty("deg_years")
    private List<String> degYears;
    
    @JsonProperty("deg_degrees")
    private List<String> degDegrees;
    
    @JsonProperty("deg_schools")
    private List<String> degSchools;
    
    @JsonProperty("deg_fos")
    private List<String> degFieldsOfStudy;
    
    // Experience fields
    @JsonProperty("exp_titles")
    private List<String> expTitles;
    
    @JsonProperty("exp_companies")
    private List<String> expCompanies;
    
    @JsonProperty("exp_start_years")
    private List<String> expStartYears;
    
    @JsonProperty("exp_end_years")
    private List<String> expEndYears;
    
    @JsonProperty("exp_years")
    private List<String> expYears;
    
    // Search relevance score (calculated during search/reranking)
    private double relevanceScore = 0.0;
    
    // Constructors
    public UserProfile() {}
    
    public UserProfile(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public String getLinkedinId() { return linkedinId; }
    public void setLinkedinId(String linkedinId) { this.linkedinId = linkedinId; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    
    public List<Double> getVector() { return vector; }
    public void setVector(List<Double> vector) { this.vector = vector; }
    
    public String getRerankSummary() { return rerankSummary; }
    public void setRerankSummary(String rerankSummary) { this.rerankSummary = rerankSummary; }
    
    public List<String> getEducation() { return education; }
    public void setEducation(List<String> education) { this.education = education; }
    
    public List<String> getExperience() { return experience; }
    public void setExperience(List<String> experience) { this.experience = experience; }
    
    public double getRelevanceScore() { return relevanceScore; }
    public void setRelevanceScore(double relevanceScore) { this.relevanceScore = relevanceScore; }
    
    // Additional getters/setters for all other fields...
    public List<String> getDegStartYears() { return degStartYears; }
    public void setDegStartYears(List<String> degStartYears) { this.degStartYears = degStartYears; }
    
    public List<String> getDegEndYears() { return degEndYears; }
    public void setDegEndYears(List<String> degEndYears) { this.degEndYears = degEndYears; }
    
    public List<String> getDegDegrees() { return degDegrees; }
    public void setDegDegrees(List<String> degDegrees) { this.degDegrees = degDegrees; }
    
    public List<String> getDegSchools() { return degSchools; }
    public void setDegSchools(List<String> degSchools) { this.degSchools = degSchools; }
    
    public List<String> getExpTitles() { return expTitles; }
    public void setExpTitles(List<String> expTitles) { this.expTitles = expTitles; }
    
    public List<String> getExpCompanies() { return expCompanies; }
    public void setExpCompanies(List<String> expCompanies) { this.expCompanies = expCompanies; }
    
    @Override
    public String toString() {
        return String.format("UserProfile{id='%s', name='%s', email='%s', relevanceScore=%.3f}", 
                           id, name, email, relevanceScore);
    }
}