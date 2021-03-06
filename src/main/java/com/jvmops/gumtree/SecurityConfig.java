package com.jvmops.gumtree;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

@Profile("web")
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/subscribe").permitAll()
                    .antMatchers("/unsubscribe").permitAll()
                    .antMatchers("/static/**").permitAll()
                    .anyRequest().authenticated()
                .and().formLogin()
                    .loginPage("/login")
                .defaultSuccessUrl("/subscriptions")
                    .permitAll()
                .and().logout()
                    .logoutSuccessUrl("/");
    }

    @Bean
    public SpringSecurityDialect springSecurityDialect(){
        return new SpringSecurityDialect();
    }
}
