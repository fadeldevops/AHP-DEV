package com.example.learn.api.master.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(value = "com.example.learn.api.master.repository")
public class JpaConfig {

}
