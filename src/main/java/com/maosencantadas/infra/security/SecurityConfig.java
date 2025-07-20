package com.maosencantadas.infra.security;

import com.maosencantadas.model.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(authorizationService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/v1/image/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/v1/image/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/v1/image/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/v1/image/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/v1/features/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/v1/check-feature/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/files/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/upload").permitAll()
                        .requestMatchers(HttpMethod.GET, "/upload").permitAll()
                        .requestMatchers(HttpMethod.POST, "/uploads/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/uploads/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/v1/artists/**").hasAnyRole("ADMIN", "ARTIST")
                        .requestMatchers(HttpMethod.PUT, "/v1/artists/**").hasAnyRole("ADMIN", "ARTIST")
                        .requestMatchers(HttpMethod.GET, "/v1/artists/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/v1/customers/**").hasAnyRole("ADMIN", "CUSTOMER")
                        .requestMatchers(HttpMethod.PUT, "/v1/customers/**").hasAnyRole("ADMIN", "CUSTOMER")
                        //todos os métodos DELETE são restritos a ADMIN
                        .requestMatchers(HttpMethod.DELETE, "/v1/artists/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/v1/artist-categories/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/v1/artist-social-medias/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/v1/budgets/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/v1/customers/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/v1/medias/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/v1/product-assessments/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/v1/product-categories/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/v1/products/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/v1/product-tags/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/v1/quote-messages/**").hasRole("ADMIN")
                        .anyRequest().authenticated()


                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.accessDeniedHandler(new CustomAccessDeniedHandler())
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}