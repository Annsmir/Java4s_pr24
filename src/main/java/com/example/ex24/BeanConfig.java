package com.example.ex24;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

@ComponentScan
@Configuration
@EnableAsync
@EnableAspectJAutoProxy
@EnableJpaRepositories
@EnableScheduling
@EnableWebSecurity
@EnableJdbcHttpSession
public class BeanConfig {

    @Bean
    PasswordEncoder encoder(){
        BCryptPasswordEncoder pe = new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2B);
        // return NoOpPasswordEncoder.getInstance(); 
        return pe;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity h) throws Exception {
        h.authorizeHttpRequests()
        .antMatchers("/","/index.html","/user/register","/user/logout").permitAll()
        .anyRequest().authenticated().and().httpBasic().and().csrf().disable().cors().disable();

        return h.build();
    }
}
