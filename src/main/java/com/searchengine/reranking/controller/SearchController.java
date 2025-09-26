package com.searchengine.reranking.controller;

import com.searchengine.reranking.model.*;
import com.searchengine.reranking.service.SearchService;
import com.searchengine.reranking.service.EvaluationService;
import com.searchengine.reranking.service.ReRankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * REST controller for search and re-ranking operations
 */
@RestController
@RequestMapping("/api/search")
@CrossOrigin(origins = "*")
public class SearchController {
    
    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);
    
    @Autowired
    private SearchService searchService;
    
    @Autowired
    private EvaluationService evaluationService;
    
    @Autowired
    private ReRankingService reRankingService;
    
    /**
     * Execute search with re-ranking
     */
    @PostMapping("/execute")
    public ResponseEntity<SearchResult> executeSearch(@RequestBody SearchQuery query) {
        try {
            logger.info("Received search request: {}", query.getNaturalLanguageDescription());
            
            SearchResult result = searchService.search(query);
            
            logger.info("Search completed successfully, returning {} results", 
                       result.getCandidates().size());
            
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            logger.error("Search execution failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Execute batch search for multiple queries
     */
    @PostMapping("/batch")
    public ResponseEntity<List<SearchResult>> executeBatchSearch(@RequestBody List<SearchQuery> queries) {
        try {
            logger.info("Received batch search request for {} queries", queries.size());
            
            List<SearchResult> results = searchService.batchSearch(queries);
            
            logger.info("Batch search completed successfully");
            
            return ResponseEntity.ok(results);
            
        } catch (Exception e) {
            logger.error("Batch search execution failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Get similar candidates based on reference profiles
     */
    @PostMapping("/similar")
    public ResponseEntity<List<UserProfile>> getSimilarCandidates(
            @RequestBody List<String> profileIds,
            @RequestParam(defaultValue = "10") int maxResults) {
        
        try {
            logger.info("Finding similar candidates to {} reference profiles", profileIds.size());
            
            List<UserProfile> similarCandidates = searchService.getSimilarCandidates(profileIds, maxResults);
            
            logger.info("Found {} similar candidates", similarCandidates.size());
            
            return ResponseEntity.ok(similarCandidates);
            
        } catch (Exception e) {
            logger.error("Similar candidates search failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Evaluate search results using external API
     */
    @PostMapping("/evaluate")
    public ResponseEntity<EvaluationResponse> evaluateResults(
            @RequestBody EvaluationRequest request,
            @RequestHeader("User-Email") String userEmail) {
        
        try {
            logger.info("Evaluating results for config: {}", request.getConfigPath());
            
            EvaluationResponse response = evaluationService.evaluateResults(
                request.getConfigPath(), 
                request.getObjectIds(), 
                userEmail
            );
            
            logger.info("Evaluation completed with score: {}", response.getScore());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Evaluation failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new EvaluationResponse(0.0, "error"));
        }
    }
    
    /**
     * Re-rank existing candidates with different strategy
     */
    @PostMapping("/rerank")
    public ResponseEntity<List<UserProfile>> reRankCandidates(
            @RequestBody Map<String, Object> request) {
        
        try {
            @SuppressWarnings("unchecked")
            List<UserProfile> candidates = (List<UserProfile>) request.get("candidates");
            SearchQuery query = (SearchQuery) request.get("query");
            String strategyName = (String) request.getOrDefault("strategy", "HYBRID");
            
            ReRankingService.ReRankingStrategy strategy = 
                ReRankingService.ReRankingStrategy.valueOf(strategyName);
            
            logger.info("Re-ranking {} candidates with {} strategy", 
                       candidates.size(), strategy);
            
            List<UserProfile> reRankedCandidates = reRankingService.reRank(
                candidates, query, strategy);
            
            return ResponseEntity.ok(reRankedCandidates);
            
        } catch (Exception e) {
            logger.error("Re-ranking failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        return ResponseEntity.ok(Map.of(
            "status", "OK",
            "service", "Search Re-Ranking System",
            "version", "1.0.0",
            "timestamp", String.valueOf(System.currentTimeMillis())
        ));
    }
    
    /**
     * Get supported re-ranking strategies
     */
    @GetMapping("/strategies")
    public ResponseEntity<List<String>> getSupportedStrategies() {
        List<String> strategies = List.of(
            "HYBRID",
            "CROSS_ENCODER", 
            "LEARNING_TO_RANK",
            "RULE_BASED"
        );
        
        return ResponseEntity.ok(strategies);
    }
}