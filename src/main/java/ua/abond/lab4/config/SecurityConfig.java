package ua.abond.lab4.config;

import ua.abond.lab4.core.annotation.Bean;
import ua.abond.lab4.core.annotation.Component;
import ua.abond.lab4.security.BCryptPasswordEncoder;
import ua.abond.lab4.security.PasswordEncoder;

@Component
public class SecurityConfig {

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
