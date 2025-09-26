package com.searchengine.reranking.util;

import java.util.List;
import java.util.stream.IntStream;

/**
 * Utility class for vector operations and calculations
 */
public class VectorUtils {
    
    private VectorUtils() {
        // Utility class - prevent instantiation
    }
    
    /**
     * Calculate cosine similarity between two vectors
     */
    public static double cosineSimilarity(List<Double> vectorA, List<Double> vectorB) {
        if (vectorA.size() != vectorB.size()) {
            throw new IllegalArgumentException("Vectors must have same dimensions");
        }
        
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        
        for (int i = 0; i < vectorA.size(); i++) {
            double a = vectorA.get(i);
            double b = vectorB.get(i);
            
            dotProduct += a * b;
            normA += a * a;
            normB += b * b;
        }
        
        if (normA == 0.0 || normB == 0.0) {
            return 0.0;
        }
        
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }
    
    /**
     * Calculate Euclidean distance between two vectors
     */
    public static double euclideanDistance(List<Double> vectorA, List<Double> vectorB) {
        if (vectorA.size() != vectorB.size()) {
            throw new IllegalArgumentException("Vectors must have same dimensions");
        }
        
        double sum = 0.0;
        for (int i = 0; i < vectorA.size(); i++) {
            double diff = vectorA.get(i) - vectorB.get(i);
            sum += diff * diff;
        }
        
        return Math.sqrt(sum);
    }
    
    /**
     * Calculate average vector from a list of vectors
     */
    public static List<Double> calculateAverageVector(List<List<Double>> vectors) {
        if (vectors.isEmpty()) {
            throw new IllegalArgumentException("Cannot calculate average of empty vector list");
        }
        
        int dimensions = vectors.get(0).size();
        
        return IntStream.range(0, dimensions)
                .mapToDouble(i -> vectors.stream()
                        .mapToDouble(vector -> vector.get(i))
                        .average()
                        .orElse(0.0))
                .boxed()
                .toList();
    }
    
    /**
     * Normalize a vector to unit length
     */
    public static List<Double> normalize(List<Double> vector) {
        double norm = Math.sqrt(vector.stream()
                .mapToDouble(x -> x * x)
                .sum());
        
        if (norm == 0.0) {
            return vector; // Return original if zero vector
        }
        
        return vector.stream()
                .map(x -> x / norm)
                .toList();
    }
    
    /**
     * Calculate dot product of two vectors
     */
    public static double dotProduct(List<Double> vectorA, List<Double> vectorB) {
        if (vectorA.size() != vectorB.size()) {
            throw new IllegalArgumentException("Vectors must have same dimensions");
        }
        
        return IntStream.range(0, vectorA.size())
                .mapToDouble(i -> vectorA.get(i) * vectorB.get(i))
                .sum();
    }
    
    /**
     * Check if a vector is valid (non-null, non-empty, correct dimensions)
     */
    public static boolean isValidVector(List<Double> vector, int expectedDimensions) {
        return vector != null && 
               vector.size() == expectedDimensions && 
               vector.stream().noneMatch(x -> x == null || Double.isNaN(x) || Double.isInfinite(x));
    }
    
    /**
     * Calculate L2 norm of a vector
     */
    public static double l2Norm(List<Double> vector) {
        return Math.sqrt(vector.stream()
                .mapToDouble(x -> x * x)
                .sum());
    }
}