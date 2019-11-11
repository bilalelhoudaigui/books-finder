package com.search.project.backend.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@EnableElasticsearchRepositories(basePackages = "com.es.basic.elasticspring.repository")
@Configuration
public class ElasticConfiguration {

    @Value("${elasticsearch.host}")
    private String elasticsearchHost;

    @Value("${elasticsearch.port}")
    private int elasticsearchPort;

    @Bean(destroyMethod = "close")
    public RestHighLevelClient client() {

        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder((new HttpHost(elasticsearchHost, elasticsearchPort))));

        return client;
    }

}