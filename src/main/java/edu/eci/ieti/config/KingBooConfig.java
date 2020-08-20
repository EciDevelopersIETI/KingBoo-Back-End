package edu.eci.ieti.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class KingBooConfig {

    @Bean
    public MongoDbFactory mongoDbFactory() throws Exception {

        MongoClientURI uri = new MongoClientURI("mongodb+srv://admin:protocolo15@basemongocluster.otant.mongodb.net/kingboodb?retryWrites=true&w=majority");

        MongoClient mongoClient = new MongoClient(uri);

        return new SimpleMongoDbFactory(mongoClient, "kingboodb");
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {

        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());

        return mongoTemplate;
    }
}
