package com;

import com.configuration.SimpleMongoConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication(exclude = MongoAutoConfiguration.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(new Class<?>[]{Application.class, SimpleMongoConfig.class}, args);
    }
}
