package com.maosencantadas.infra.security;

import com.maosencantadas.model.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

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
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity

                .cors()
                .and()
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                                        .requestMatchers(HttpMethod.POST, "/v1/users").permitAll()
                                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                                        .requestMatchers(HttpMethod.GET, "/auth/register").permitAll()
                                        .requestMatchers(HttpMethod.GET, "/v1/artists").permitAll()
                                        .requestMatchers(HttpMethod.GET, "/v1/artists/**").permitAll()
                                        .requestMatchers(HttpMethod.POST, "/v1/artists").permitAll()
                                        .requestMatchers(HttpMethod.PUT, "/v1/artists/**").hasAnyRole("ADMIN", "ARTIST")
                                        .requestMatchers(HttpMethod.DELETE, "/v1/artists/**").hasAnyRole("ADMIN", "ARTIST")
                                        .requestMatchers(HttpMethod.GET, "/v1/products").permitAll()
                                        .requestMatchers(HttpMethod.GET, "/v1/products/**").permitAll()
                                        .requestMatchers(HttpMethod.POST, "/v1/products").hasAnyRole("ADMIN", "ARTIST")
                                        .requestMatchers(HttpMethod.PUT, "/v1/products/**").hasAnyRole("ADMIN", "ARTIST")
                                        .requestMatchers(HttpMethod.DELETE, "/v1/products/**").hasAnyRole("ADMIN", "ARTIST")
                                        .requestMatchers(HttpMethod.GET, "/v1/image/**").permitAll()
                                        .requestMatchers(HttpMethod.POST, "/v1/image/**").permitAll()
                                        .requestMatchers(HttpMethod.PUT, "/v1/image/**").permitAll()
                                        .requestMatchers(HttpMethod.DELETE, "/v1/image/**").permitAll()
                                        .requestMatchers(HttpMethod.GET, "/files/**").permitAll()
                                        .requestMatchers(HttpMethod.POST, "/upload").permitAll()
                                        .requestMatchers(HttpMethod.GET, "/upload").permitAll()
                                        .requestMatchers(HttpMethod.POST, "/uploads/**").permitAll()
                                        .requestMatchers(HttpMethod.GET, "/uploads/**").permitAll()
                                        .requestMatchers(HttpMethod.GET, "/v1/categories").permitAll()
                                        .requestMatchers(HttpMethod.GET, "/v1/categories/**").permitAll()
                                        .requestMatchers(HttpMethod.POST, "/v1/categories").hasRole("ADMIN")
                                        .requestMatchers(HttpMethod.PUT, "/v1/categories/**").hasRole("ADMIN")
                                        .requestMatchers(HttpMethod.DELETE, "/v1/categories/**").hasRole("ADMIN")
                                        .requestMatchers(HttpMethod.GET, "/v1/customers").permitAll()
                                        .requestMatchers(HttpMethod.GET, "/v1/customers/**").permitAll()
                                        .requestMatchers(HttpMethod.POST, "/v1/customers").permitAll()
                                        .requestMatchers(HttpMethod.PUT, "/v1/customers/**").hasAnyRole("ADMIN", "CUSTOMER")
                                        .requestMatchers(HttpMethod.DELETE, "/v1/customers/**").hasAnyRole("ADMIN", "CUSTOMER")
                                        .requestMatchers(HttpMethod.GET, "/v1/budgets").permitAll()
                                        .requestMatchers(HttpMethod.GET, "/v1/budgets/**").permitAll()
                                        .requestMatchers(HttpMethod.POST, "/v1/budgets").permitAll()
                                        .requestMatchers(HttpMethod.PUT, "/v1/budgets/**").authenticated()
                                        .requestMatchers(HttpMethod.DELETE, "/v1/budgets/**").hasRole("ADMIN")
                                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                                .anyRequest().authenticated()
                        )
                        .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
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