package com.search.project.backend.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.Suggest.Suggestion;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.search.project.backend.dto.SearchResultDto;
import com.search.project.backend.model.BookDocument;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BookService {

    private RestHighLevelClient client;

    private ObjectMapper objectMapper;

    @Autowired
    public void goTermService(RestHighLevelClient client, ObjectMapper objectMapper) {
        this.client = client;
        this.objectMapper = objectMapper;
    }

    private List<BookDocument> getSearchResult(SearchResponse response) {

        SearchHit[] searchHit = response.getHits().getHits();
        List<BookDocument> bookDocuments = new ArrayList<>();

        if (searchHit.length > 0) {
            Arrays.stream(searchHit)
                    .forEach(hit -> bookDocuments
                            .add(objectMapper
                                    .convertValue(hit.getSourceAsMap(), BookDocument.class))
                    );
        }
        return bookDocuments;
    }

    public List<BookDocument> findAll() throws Exception {
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        return getSearchResult(searchResponse);
    }

    /*
    Find by title
    */
    public List<BookDocument> findByBookTitle(String bookTitle) throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        searchSourceBuilder.query(QueryBuilders
                .matchQuery("title", bookTitle));

        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);

        return getSearchResult(response);
    }

    /*
    @Override
    public SearchResultDto autocomplete(String prefixString, int size) {
        SearchRequest searchRequest = new SearchRequest(INDEX);
        CompletionSuggestionBuilder suggestBuilder = new CompletionSuggestionBuilder(FIELD_COMPLETION); // Note 1

        suggestBuilder.size(size)
                    .prefix(prefixString, Fuzziness.ONE) // Note 2
                    .skipDuplicates(true)
                    .analyzer("standard");
    
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); // _search
        sourceBuilder.suggest(new SuggestBuilder().addSuggestion(SUGGESTION_NAME, suggestBuilder));
        searchRequest.source(sourceBuilder);

        SearchResponse response;
        try {
            response = client.search(searchRequest);
            return getSuggestions(response); // Note 3
        } catch (IOException ex) {
            logger.error("Error in autocomplete search", ex);
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Error in ES search");
        }
    }

    private SearchResultDto getSuggestions(SearchResponse response) {
        SearchResultDto dto = new SearchResultDto();
        Suggest suggest = response.getSuggest();
        Suggestion<Entry<Option>> suggestion = suggest.getSuggestion(SUGGESTION_NAME);
        for(Entry<Option> entry: suggestion.getEntries()) {
              for (Option option: entry.getOptions()) {
                dto.add(option.getText().toString());
              }
        }
        return dto;
    }
    */
}