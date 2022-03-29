package com.example.learn.api.master.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan("com.example.learn.api.master.entity")
public class EntityConfig {

}
