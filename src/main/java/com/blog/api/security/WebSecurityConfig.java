package com.blog.api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    

    private final AuthenticationProvider UserAuthenticationService;

    public WebSecurityConfig(AuthenticationProvider userAuthenticationService) {
        UserAuthenticationService = userAuthenticationService;
    }

    @Bean 
    public static BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers(HttpMethod.POST, "/api/user")
            .permitAll()
                .anyRequest().authenticated()
                .and().httpBasic().and().formLogin()
                .usernameParameter("username")
                           .passwordParameter("password");
    }

    @Override 
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.authenticationProvider(UserAuthenticationService);
        // inMemoryAuthentication().withUser("Pedro").password(passwordEncoder().encode("123")).roles("ADMIN");
    }
    
}
