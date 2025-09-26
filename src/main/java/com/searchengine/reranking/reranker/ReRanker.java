package com.searchengine.reranking.reranker;

import com.searchengine.reranking.model.SearchQuery;
import com.searchengine.reranking.model.UserProfile;
import java.util.List;

/**
 * Base interface for all re-ranking strategies
 */
public interface ReRanker {
    
    /**
     * Re-rank the given candidates based on the query
     */
    List<UserProfile> reRank(List<UserProfile> candidates, SearchQuery query);
    
    /**
     * Get the name of this re-ranking strategy
     */
    String getStrategyName();
}