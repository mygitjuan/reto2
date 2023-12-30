package com.banana.bananawhatsapp.config;

import com.banana.bananawhatsapp.persistencia.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

@Configuration
@PropertySource("classpath:application.properties")
public class ReposConfig {
    @Value("${db_url}")
    String connUrl;


    @Bean
    public IUsuarioRepository createIUsuarioRepository() throws Exception {
        UsuarioJDBCRepository repo = new UsuarioJDBCRepository();
        repo.setDb_url(connUrl);
        return repo;
    }


}
