package com.project.management.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.management.security.jwt.JwtRequestFilter;
import com.project.management.security.service.PermissionRoleEvaluatorImpl;
import com.project.management.security.service.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import java.util.HashMap;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;

    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(JwtRequestFilter jwtRequestFilter, UserDetailsServiceImpl userDetailsService) {
        this.jwtRequestFilter = jwtRequestFilter;
        this.userDetailsService = userDetailsService;
    }

    private final String[] ROUTES_ALLOWED_WITHOUT_AUTHENTICATION = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
    };

    private final String[] ROUTES_GET_ALLOWED_WITHOUT_AUTHENTICATION = {
            "/security/document_type/",
    };

    private final String[] ROUTES_POST_ALLOWED_WITHOUT_AUTHENTICATION = {
            "/security/authentication/**",
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,CorsConfig corsConfig) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource()))
                .sessionManagement(sessionManagementConfig -> sessionManagementConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(this.authenticationProvider())
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(registry -> {
                    registry.requestMatchers(ROUTES_ALLOWED_WITHOUT_AUTHENTICATION).permitAll();
                    registry.requestMatchers(HttpMethod.GET,ROUTES_GET_ALLOWED_WITHOUT_AUTHENTICATION).permitAll();
                    registry.requestMatchers(HttpMethod.POST,ROUTES_POST_ALLOWED_WITHOUT_AUTHENTICATION).permitAll();
                    registry.anyRequest().authenticated();
                }).exceptionHandling(httpSecurityExceptionHandlingConfigurer -> {
                    httpSecurityExceptionHandlingConfigurer.accessDeniedHandler((request, response, accessDeniedException) -> {
                        HashMap<String,String> map = new HashMap<>();
                        map.put("message","No tienes las credenciales adecuadas para acceder a este recurso.");
                        ObjectMapper mapper = new ObjectMapper();
                        response.setStatus(403);
                        response.setHeader("Content-Type", "application/json");
                        response.addHeader("message","");
                        mapper.writeValueAsString(map);
                        response.getWriter().write(mapper.writeValueAsString(map));
                    }).authenticationEntryPoint((request, response, authException) -> {
                        HashMap<String,String> map = new HashMap<>();
                        map.put("message","Credenciales inválidas. Inicia sesión con un usuario autorizado");
                        ObjectMapper mapper = new ObjectMapper();
                        response.setStatus(401);
                        response.setHeader("Content-Type", "application/json");
                        response.getWriter().write(mapper.writeValueAsString(map));
                    });
                });

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(this.passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowBackSlash(true);
        return (web -> web.httpFirewall(firewall));
    }

    @Bean
    public MethodSecurityExpressionHandler methodSecurityExpressionHandler(HttpSession httpSession){
        var expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(new PermissionRoleEvaluatorImpl(httpSession));
        return expressionHandler;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
