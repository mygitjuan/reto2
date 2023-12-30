package com.banana.bananawhatsapp.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {"com.banana.bananawhatsapp.persistencia", "com.banana.bananawhatsapp.servicios",
"com.banana.bananawhatsapp.controladores"})
@Import({ReposConfig.class})

public class SpringConfig {
}
