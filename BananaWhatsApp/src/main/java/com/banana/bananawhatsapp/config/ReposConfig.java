package com.banana.bananawhatsapp.config;

import com.banana.bananawhatsapp.persistencia.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class ReposConfig {
    @Value("${db_url}")
    String connUrl;


    @Bean
    public IUsuarioRepository createIUsuarioRepository() throws Exception {
        UsuarioJDBCRepository repo = new UsuarioJDBCRepository();
        repo.setConnUrl(connUrl);
        return repo;
    }


}
