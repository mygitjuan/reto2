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
    @Profile("prod")
    public IUsuarioRepository createIUsuarioRepository() throws Exception {
        UsuarioJDBCRepository repo = new UsuarioJDBCRepository();
        repo.setDb_url(connUrl);
        return repo;
    }

    @Bean
    @Profile("prod")
    public IMensajeRepository createIMensajeRepository() throws Exception {
        MensajeJDBCRepository repo = new MensajeJDBCRepository();
        repo.setDb_url(connUrl);
        return repo;
    }

    @Bean
    @Profile("dev")
    public IUsuarioRepository createIUsuarioRepoInMemo() throws Exception {
        UsuarioInMemoryRepository repo = new UsuarioInMemoryRepository();
        return repo;
    }

    @Bean
    @Profile("dev")
    public IMensajeRepository createIMensajeRepoInMemory() throws Exception {
        MensajeInMemoryRepository repo = new MensajeInMemoryRepository();
        return repo;
    }

}
