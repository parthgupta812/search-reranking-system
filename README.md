# Search Re-Ranking System

A comprehensive Java 21 Spring Boot application for advanced candidate search and re-ranking using vector embeddings and multiple re-ranking strategies.

## Overview

This system provides:
- **Vector-based search** using TurboPuffer with 1024-dimensional embeddings
- **Multiple re-ranking strategies**: Hybrid, Cross-Encoder, Learning-to-Rank, Rule-Based
- **Integration with Voyage AI** for generating semantic embeddings  
- **Evaluation API** for scoring search result quality
- **RESTful API** for search operations

## Architecture

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   REST API      │    │  Search Service │    │  TurboPuffer    │
│  (Controller)   │───▶│                 │───▶│   Vector DB     │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                                │
                                ▼
                       ┌─────────────────┐
                       │ Re-Ranking      │
                       │ Service         │
                       └─────────────────┘
                                │
                    ┌───────────┼───────────┐
                    ▼           ▼           ▼
           ┌─────────────┐ ┌──────────┐ ┌──────────┐
           │   Hybrid    │ │Cross-    │ │Learning- │
           │ Re-Ranker   │ │Encoder   │ │to-Rank   │
           └─────────────┘ └──────────┘ └──────────┘
```

## Features

### 🔍 Search Capabilities
- **Semantic Vector Search**: Uses 1024-dim Voyage-3 embeddings
- **Hard Criteria Filtering**: Must-have requirements (education, experience)
- **Soft Criteria Scoring**: Nice-to-have preferences
- **Batch Processing**: Multiple queries simultaneously

### 🎯 Re-Ranking Strategies
1. **Hybrid Re-Ranker** (Default): Combines multiple scoring approaches
2. **Cross-Encoder**: Uses transformer models for semantic similarity
3. **Learning-to-Rank**: Feature-based ranking with learned weights
4. **Rule-Based**: Domain-specific heuristics and business rules

### 📊 Evaluation
- External API integration for result quality scoring
- Support for multiple evaluation configurations
- Batch evaluation capabilities

## Quick Start

### Prerequisites
- Java 21+
- Maven 3.8+
- API keys for TurboPuffer, Voyage AI, and OpenAI

### Environment Variables
```bash
export TURBOPUFFER_API_KEY=your_turbopuffer_key
export VOYAGE_API_KEY=your_voyage_key  
export OAI_KEY=your_openai_key
```

### Run the Application
```bash
# Clone the repository
git clone https://github.com/your-username/search-reranking-system.git
cd search-reranking-system

# Build and run
mvn clean install
mvn spring-boot:run

# Or run with Docker
docker-compose up --build
```

The application will start on `http://localhost:8080`

## API Endpoints

### Search Operations
- `POST /api/search/execute` - Execute single search query
- `POST /api/search/batch` - Execute multiple search queries  
- `POST /api/search/similar` - Find similar candidates
- `POST /api/search/rerank` - Re-rank with different strategy

### Evaluation  
- `POST /api/search/evaluate` - Evaluate search results

### Utility
- `GET /api/search/health` - Health check
- `GET /api/search/strategies` - List available re-ranking strategies

## Example Usage

### Basic Search Request
```json
{
  "naturalLanguageDescription": "Experienced tax lawyer with JD degree",
  "hardCriteria": [
    "JD degree from an accredited U.S. law school",
    "3+ years of experience practicing law"
  ],
  "softCriteria": [
    "Experience with IRS audits",
    "Corporate tax expertise"
  ],
  "maxResults": 10
}
```

### Evaluation Request
```json
{
  "config_path": "tax_lawyer.yml",
  "object_ids": [
    "67970d138a14699f1614c6b6",
    "679508a7a1a09a48feaadf0c"
  ]
}
```

## Configuration

### Query Configurations
Pre-configured search templates are available in `src/main/resources/query-configs/`:
- `tax_lawyer.yml`
- `junior_corporate_lawyer.yml` 
- `radiology.yml`
- `doctors_md.yml`
- `biology_expert.yml`

### Application Properties
See `application.yml` for configuration options including:
- API endpoints and keys
- Logging levels
- Performance tuning
- Re-ranking weights

## Data Model

### User Profile Schema
Profiles contain rich metadata including:
- Personal info (name, email, country)
- 1024-dimensional vector embeddings
- Education history with degrees, schools, fields of study
- Experience history with titles, companies, duration
- Consolidated text summary for re-ranking

### Search Query Schema
Queries support:
- Natural language descriptions
- Hard criteria (must-have requirements)
- Soft criteria (preferences)
- Custom filters and parameters

## Testing

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=SearchServiceTest

# Run integration tests
mvn test -Dtest=*IntegrationTest
```

## Development

### Project Structure
```
src/
├── main/
│   ├── java/com/searchengine/reranking/
│   │   ├── config/          # Configuration classes
│   │   ├── controller/      # REST controllers
│   │   ├── model/           # Data models
│   │   ├── reranker/        # Re-ranking implementations
│   │   ├── service/         # Business logic
│   │   └── util/            # Utility classes
│   └── resources/
│       ├── application.yml  # Main configuration
│       └── query-configs/   # Query templates
└── test/                    # Test classes
```

### Adding New Re-Rankers
1. Implement the `ReRanker` interface
2. Add `@Component` annotation  
3. Register in `ReRankingService`
4. Update strategy enum

## Performance

### Optimization Features
- **Async processing** for batch operations
- **Connection pooling** for external APIs
- **Caching** for frequently accessed data
- **Configurable timeouts** and retry logic

### Monitoring
- Spring Boot Actuator endpoints
- Comprehensive logging
- Performance metrics
- Health checks

## Deployment

### Docker
```bash
docker build -t search-reranking-system .
docker run -p 8080:8080 search-reranking-system
```

### Docker Compose
```bash
docker-compose up -d
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make changes and add tests  
4. Submit a pull request

## License

This project is licensed under the MIT License - see LICENSE file for details.

## Support

For questions or issues:
- Create a GitHub issue
- Contact: nirmitgoyal.goyal@gmail.com

---

Built with ❤️ using Java 21 and Spring Boot