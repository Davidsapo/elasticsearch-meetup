package com.index.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.RefreshPolicy;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * Elasticsearch Config.
 *
 * @author David Sapozhnik
 */
@Configuration
@ComponentScan(basePackages = "com.index.entity")
@EnableElasticsearchRepositories(basePackages = "com.index.repository")
public class ElasticsearchConfig {

    @Value("${index.elasticsearch.host}")
    private String host;

    @Value("${index.elasticsearch.port}")
    private String port;

    @Bean
    public RestHighLevelClient clientLocal() {
        var hostAndPort = host + ":" + port;
        return RestClients.create(ClientConfiguration.builder().connectedTo(hostAndPort).build()).rest();
    }

    @Bean(name = "elasticsearchTemplate")
    public ElasticsearchRestTemplate elasticsearchTemplateLocal() {
        var template = new ElasticsearchRestTemplate(clientLocal());
        template.setRefreshPolicy(RefreshPolicy.WAIT_UNTIL);
        return template;
    }
}
