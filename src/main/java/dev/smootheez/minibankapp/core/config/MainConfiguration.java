package dev.smootheez.minibankapp.core.config;

import dev.smootheez.minibankapp.security.config.*;
import org.springframework.context.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.config.*;

import java.util.*;

@Configuration
@EnableJpaAuditing
@Import({SecurityConfig.class})
public class MainConfiguration {
    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.of("system");
    }
}
