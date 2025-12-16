package com.study.app_services.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import com.study.app_services.security.JwtAuthenticationFilter;
import com.study.app_services.security.JwtUtil;

// Use JWT for authentication, stateless
// Define who can access which endpoints
// CORS configuration

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //Password Encoder
    //Use BCryptPasswordEncoder for password encoding
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //Authentication Manager
    //This bean provides the authentication manager, which is used to authenticate users
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    //JWT Util
    //This bean provides the JWT utility, which is used to generate and validate JWT tokens
    @Bean
    public JwtUtil jwtUtil() {
        String env = System.getenv("JWT_SECRET");
        byte[] secret = env != null ? env.getBytes() : java.util.UUID.randomUUID().toString().getBytes();
        long expiration = 3600000L;
        return new JwtUtil(secret, expiration);
    }

    //Security Filter Chain
    //This bean configures the security filter chain, which defines how requests are processed
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            //enable CORS
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            //disable CSRF protection for simplicity
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            //this section defines who can access which endpoints
            .authorizeHttpRequests(auth -> auth
                //allow login endpoint without authentication
                .requestMatchers("/auth/login").permitAll()
                //roles based authorization
                .requestMatchers(HttpMethod.GET, "/topics/**").hasAnyRole("ADMIN", "MODERATOR", "USER")
                .requestMatchers(HttpMethod.POST, "/topics/**").hasAnyRole("ADMIN", "MODERATOR")
                .requestMatchers(HttpMethod.PUT, "/topics/**").hasAnyRole("ADMIN", "MODERATOR")
                .requestMatchers(HttpMethod.DELETE, "/topics/**").hasAnyRole("ADMIN", "MODERATOR")
                .requestMatchers("/users/**").hasRole("ADMIN")
                //any request not matched above will be authenticated
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter(jwtUtil()), UsernamePasswordAuthenticationFilter.class);
        //build the security filter chain
        return http.build();
    }
    //CORS configuration bean
    //This control which frontend origins are allowed to access the backend
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        //React dev server origin on localhost:3000
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        //Allowed HTTP methods
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        //Allowed headers
        configuration.setAllowedHeaders(List.of("*"));
        //Allow credentials (cookies, authorization headers, etc.)
        configuration.setAllowCredentials(true);
        //Expose headers (e.g., Authorization) to the frontend
        configuration.setExposedHeaders(List.of("Authorization"));
        //Register the CORS configuration for all endpoints
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //Apply the CORS configuration to all endpoints
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtUtil jwtUtil) {
        return new JwtAuthenticationFilter(jwtUtil);
    }
}
