package com.proyecto.demo.security.jwt;

import com.proyecto.demo.security.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    private final UserDetailsServiceImpl userDetailsService;

    public JwtRequestFilter(JwtTokenProvider jwtTokenProvider, UserDetailsServiceImpl userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //1. sacamos el jwt de la petición
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if ((authHeader == null) || !(authHeader.startsWith("Bearer "))){
            filterChain.doFilter(request,response);
            return;
        }

        //2. obtenemos el jwt
        String jwt = authHeader.split(" ")[1];

        //3. obtener el sub/username del jwt
        String userName = jwtTokenProvider.extractorUserName(jwt);

        //4. settear el objeto de authenticación dentro del contexto de spring security
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
          userDetails,
          null,
          userDetails.getAuthorities()
        );
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request,response);
    }
}
