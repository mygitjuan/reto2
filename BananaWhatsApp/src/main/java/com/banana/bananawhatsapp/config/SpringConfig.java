package com.banana.bananawhatsapp.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
/*@Import({ReposConfig.class, ServicesConfig.class})*/
@ComponentScan(basePackages = {"com.banana.bananawhatsapp.persistencia", "com.banana.bananawhatsapp.servicios"})
@Import({ReposConfig.class})
/*@ComponentScan(basePackages = {"com.banana.bananawhatsapp.persistencia"})*/
/*@ComponentScan(basePackages = {"com.banana.bananawhatsapp.servicios"})*/
public class SpringConfig {
}
