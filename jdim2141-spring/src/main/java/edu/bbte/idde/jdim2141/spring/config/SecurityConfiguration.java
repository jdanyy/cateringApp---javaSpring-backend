package edu.bbte.idde.jdim2141.spring.config;

import edu.bbte.idde.jdim2141.spring.constants.UserRole;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        return http
            .securityMatcher("/api/**")
            .cors(cors -> cors.configurationSource(corsConfiguration()))
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(
                auth -> auth
                    .requestMatchers("/api/auth/introspect").authenticated()
                    .requestMatchers("/api/auth/**").permitAll()
            )
            .authorizeHttpRequests(
                auth -> auth
                    .requestMatchers(HttpMethod.GET, "/api/dishes/**")
                    .hasAnyRole(UserRole.ADMIN.name(), UserRole.CHEF.name())
                    .requestMatchers("/api/dishes/**").hasRole(UserRole.ADMIN.name())
            )
            .authorizeHttpRequests(
                auth -> auth
                    .requestMatchers(HttpMethod.GET, "/api/menus/**").authenticated()
                    .requestMatchers("/api/menus/**").hasRole(UserRole.CHEF.name())
            )
            .authorizeHttpRequests(
                auth -> auth
                    .requestMatchers("/api/users/{userId}/theme")
                    .hasAnyRole(UserRole.ADMIN.name(), UserRole.CHEF.name(), UserRole.CLIENT.name())
                    .requestMatchers("/api/users/{userId}/lang")
                    .hasAnyRole(UserRole.ADMIN.name(), UserRole.CHEF.name(), UserRole.CLIENT.name())
                    .requestMatchers("/api/users/**").hasRole(UserRole.ADMIN.name())
                    .requestMatchers("/api/**").authenticated()
            )
            .sessionManagement(
                session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .oauth2ResourceServer(
                oauth2 -> oauth2
                    .jwt(Customizer.withDefaults())
            )
            .build();
    }

    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE)
    public SecurityFilterChain publicFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(Customizer.withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/error").permitAll()
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html")
                .permitAll()
                .anyRequest().denyAll()
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfiguration() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
