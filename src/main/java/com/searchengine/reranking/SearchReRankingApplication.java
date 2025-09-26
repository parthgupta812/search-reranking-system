package com.searchengine.reranking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Main Spring Boot Application for Search Re-Ranking System
 * 
 * This application provides:
 * 1. Vector-based search using TurboPuffer
 * 2. Multiple re-ranking strategies (Cross-Encoder, LTR, Rule-based, Hybrid)
 * 3. Integration with Voyage AI embeddings
 * 4. Evaluation via external API
 * 
 * @author Nirmit Goyal
 * @version 1.0
 */
@SpringBootApplication
@EnableConfigurationProperties
@EnableAsync
public class SearchReRankingApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchReRankingApplication.class, args);
    }
}