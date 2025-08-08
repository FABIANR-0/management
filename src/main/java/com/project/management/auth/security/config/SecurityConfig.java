package com.project.management.auth.security.config;

import com.project.management.auth.security.jwt.JwtRequestFilter;
import com.project.management.auth.security.service.PermissionRoleEvaluator;
import com.project.management.auth.service.CustomUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

/**
 * Configuración de seguridad de la aplicación.
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    private final String[] ROUTES_ALLOWED_WITHOUT_AUTHENTICATION = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/secure/diagnostic/update_generated_reports",
            "/secure/report/battery_data",
            "/secure/report/battery_report",
            "/secure/logs/**",
    };

    private final String[] ROUTES_GET_ALLOWED_WITHOUT_AUTHENTICATION = {
            "/secure/auth/resend_email_code/{id}",
            "/secure/user/exist_by_document/{user_document}",
            "/secure/user/exist_by_name/{user_name}",
            "/secure/user/exist_by_email/{user_email}"
    };

    private final String[] ROUTES_POST_ALLOWED_WITHOUT_AUTHENTICATION = {
            "/secure/auth/login",
            "/secure/auth/microsoft/login",
            "/secure/auth/mfa",
            "/secure/user/forgot_password",
            "/secure/user/reset_password",
            "/secure/auth/refresh_token"
    };

    /**
     * Constructor de la configuración de seguridad.
     *
     * @param customUserDetailsService El servicio de detalles del usuario personalizado.
     * @param jwtRequestFilter         El filtro de solicitud JWT.
     */
    public SecurityConfig(CustomUserDetailsService customUserDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    /**
     * Configura la cadena de filtros de seguridad.
     *
     * @return La cadena de filtros de seguridad configurada.
     * @throws Exception Si ocurre algún error durante la configuración.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, CorsConfig corsConfig) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfig.corsConfiguration()))
                .sessionManagement(sessionManagementConfig -> sessionManagementConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(this.authenticationProvider())
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(registry -> {
                    registry.requestMatchers(ROUTES_ALLOWED_WITHOUT_AUTHENTICATION).permitAll();
                    registry.requestMatchers(HttpMethod.GET, ROUTES_GET_ALLOWED_WITHOUT_AUTHENTICATION).permitAll();
                    registry.requestMatchers(HttpMethod.POST, ROUTES_POST_ALLOWED_WITHOUT_AUTHENTICATION).permitAll();
                    registry.anyRequest().authenticated();
                }).exceptionHandling(httpSecurityExceptionHandlingConfigurer -> {
                    httpSecurityExceptionHandlingConfigurer.accessDeniedHandler((request, response, accessDeniedException) -> {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("message", "Acceso denegado");
                        ObjectMapper mapper = new ObjectMapper();
                        response.setStatus(403);
                        response.setHeader("Content-Type", "application/json");
                        mapper.writeValueAsString(map);
                        response.getWriter().write(mapper.writeValueAsString(map));
                    }).authenticationEntryPoint((request, response, authException) -> {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("message", "Acceso no autorizado");
                        ObjectMapper mapper = new ObjectMapper();
                        response.setStatus(401);
                        response.setHeader("Content-Type", "application/json");
                        response.getWriter().write(mapper.writeValueAsString(map));
                    });
                });

        return httpSecurity.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowBackSlash(true);
        return (web) -> web.httpFirewall(firewall);
    }

    /**
     * Crea y configura el proveedor de autenticación DAO.
     *
     * @return El proveedor de autenticación DAO configurado.
     */
    private AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(customUserDetailsService);
        authenticationProvider.setPasswordEncoder(this.passwordEncoder());
        return authenticationProvider;
    }

    /**
     * Crea y configura el manejador de expresiones de seguridad de métodos.
     *
     * @return El manejador de expresiones de seguridad de métodos configurado.
     */
    @Bean
    public MethodSecurityExpressionHandler methodSecurityExpressionHandler(HttpSession httpSession) {
        var expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(new PermissionRoleEvaluator(httpSession));
        return expressionHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Crea y configura el administrador de autenticación.
     *
     * @param authConfiguration La configuración de autenticación.
     * @return El administrador de autenticación configurado.
     * @throws Exception sí ocurre algún error al obtener el administrador de autenticación.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
        return authConfiguration.getAuthenticationManager();
    }
}
