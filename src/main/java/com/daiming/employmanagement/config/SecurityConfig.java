package com.daiming.employmanagement.config;

import com.daiming.employmanagement.filter.JwtFilter;
import com.daiming.employmanagement.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    JwtFilter jwtFilter;
    @Autowired
    DataSource dataSource;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf( csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers("/hello").permitAll()
                            .requestMatchers("/employee/login").permitAll()
                            .requestMatchers("/employer/login").permitAll()
                            .requestMatchers("/employer/signup").permitAll()
                            .requestMatchers("/employee/signup").hasRole(UserRole.EMPLOYER.name())
                            .anyRequest().authenticated();
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

//    @Bean
//    public DataSource getDataSource()
//    {
//        DriverManagerDataSource dataSource =  new DriverManagerDataSource();
////        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
////        dataSource.setUrl("jdbc:mysql://daimingyang.couxfnqwwltv.us-east-2.rds.amazonaws.com:3306/employmanagement");
////        dataSource.setUsername("dieyoung");
////        dataSource.setPassword("DDVPismylove456~");
//        return dataSource;
//    }

    @Bean
    public UserDetailsManager users() {
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        return users;
    }
}

