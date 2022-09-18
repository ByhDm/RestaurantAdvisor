//package com.example.user_service;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class MySecurityConfig {
//    @Bean
//    PasswordEncoder passwordEncoder() {
//// test push
////        return new BCryptPasswordEncoder(10);
//        return NoOpPasswordEncoder.getInstance();
//    }
//
//    @Bean
//    @Order(Ordered.HIGHEST_PRECEDENCE)
//    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/test/user**").permitAll()
//                .and()
//                .formLogin(Customizer.withDefaults())
//                .csrf();
//        return http.build();
//    }
//}
