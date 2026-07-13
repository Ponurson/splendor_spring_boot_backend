package pl.coderslab.splendor_angular_connection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import pl.coderslab.splendor_angular_connection.auth.SpringDataUserDetailsService;
import pl.coderslab.splendor_angular_connection.auth.TokenAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

@SpringBootApplication
public class SplendorAngularConnectionApplication {

    public static void main(String[] args) {
        SpringApplication.run(SplendorAngularConnectionApplication.class, args);
    }

    @Configuration
    public static class SecurityConfiguration {

        // Only /login uses HTTP Basic: the client sends credentials once to obtain a token.
        @Bean
        @Order(1)
        public SecurityFilterChain loginFilterChain(HttpSecurity http) throws Exception {
            return http
                    .securityMatcher(PathPatternRequestMatcher.pathPattern("/login"))
                    .cors(Customizer.withDefaults())
                    .httpBasic(Customizer.withDefaults())
                    .authorizeHttpRequests(authorize -> authorize.anyRequest().hasRole("USER"))
                    .csrf(AbstractHttpConfigurer::disable)
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .build();
        }

        // Everything else authenticates via the Authorization: Bearer <token> header.
        @Bean
        @Order(2)
        public SecurityFilterChain apiFilterChain(HttpSecurity http, TokenAuthenticationFilter tokenFilter) throws Exception {
            return http
                    .cors(Customizer.withDefaults())
                    .authorizeHttpRequests(authorize -> authorize
                            .requestMatchers(PathPatternRequestMatcher.pathPattern("/register")).permitAll()
                            .requestMatchers(PathPatternRequestMatcher.pathPattern("/error")).permitAll()
                            .anyRequest().hasRole("USER"))
                    .csrf(AbstractHttpConfigurer::disable)
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)
                    .exceptionHandling(ex -> ex.authenticationEntryPoint(
                            (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED)))
                    .build();
        }
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(@Value("${app.cors.allowed-origins}") String allowedOrigins) {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.stream(allowedOrigins.split(","))
                .map(String::trim)
                .filter(origin -> !origin.isEmpty())
                .collect(Collectors.toList()));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SpringDataUserDetailsService customUserDetailsService() {
        return new SpringDataUserDetailsService();
    }
}
