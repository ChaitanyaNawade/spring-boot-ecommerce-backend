package com.chaitanya.jayganesh.config;

import com.chaitanya.jayganesh.service.CustomUserDetailService;
import com.chaitanya.jayganesh.service.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig
{
    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security)throws Exception
    {
        return security.
                csrf(csrf-> csrf.disable()).
                authorizeHttpRequests(auth->auth.
                        requestMatchers("/api/auth/**").permitAll().
                        requestMatchers(HttpMethod.POST,"/api/products/**").hasAuthority("ADMIN").
                        requestMatchers(HttpMethod.PUT,"/api/products/**").hasAuthority("ADMIN").
                        requestMatchers(HttpMethod.DELETE,"/api/products/**").hasAuthority("ADMIN").
                        requestMatchers(HttpMethod.POST,"/api/categories/**").hasAuthority("ADMIN").
                        requestMatchers(HttpMethod.PUT,"/api/categories/**").hasAuthority("ADMIN").
                        requestMatchers(HttpMethod.DELETE,"/api/categories/**").hasAuthority("ADMIN").
                        requestMatchers(HttpMethod.GET,"/api/orders/all").hasAuthority("ADMIN").
                        requestMatchers(HttpMethod.PUT,"/api/orders/status/**").hasAuthority("ADMIN").
                        anyRequest().authenticated()
                ).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(customUserDetailService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception
    {
        return config.getAuthenticationManager();
    }

    @Bean
    public FilterRegistrationBean<JwtAuthFilter> jwtFilterRegistration(JwtAuthFilter filter)
    {
        FilterRegistrationBean<JwtAuthFilter> registrationBean = new FilterRegistrationBean<>(filter);
        registrationBean.setEnabled(false);
        return registrationBean;
    }
}
