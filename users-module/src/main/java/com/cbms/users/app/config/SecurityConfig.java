package com.cbms.users.app.config;

import com.cbms.users.app.service.JpaUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final String[] allowedUrls = {"/api/users/signIn", "/api/users/forgetPassword", "/api/users/signUp", "/api/test/welcome"};

    private final JpaUserDetailsService jpaUserDetailsService;

    private final RsaKeyProperties rsaKeyProperties;

    public SecurityConfig(JpaUserDetailsService jpaUserDetailsService, RsaKeyProperties rsaKeyProperties) {
        this.jpaUserDetailsService = jpaUserDetailsService;
        this.rsaKeyProperties = rsaKeyProperties;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(allowedUrls).permitAll();
                    auth.requestMatchers("/api/test/testSecurity").hasRole("ADMIN");
                    auth.anyRequest().authenticated();
                })
                .userDetailsService(jpaUserDetailsService)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder.withPublicKey(rsaKeyProperties.publicKey()).build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
