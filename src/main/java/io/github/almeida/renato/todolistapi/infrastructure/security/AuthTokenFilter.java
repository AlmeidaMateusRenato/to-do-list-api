package io.github.almeida.renato.todolistapi.infrastructure.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import io.github.almeida.renato.todolistapi.infrastructure.persistence.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    @Value("${spring.application.name}")
    private String appName;

    private final AuthTokenService tokenService;
    private final UserRepository userRepository;

    public AuthTokenFilter(AuthTokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = this.recoverToken(request);
        if (token != null) {
            String login = tokenService.validateToken(token);
            AuthUser authUser = userRepository.findByUsername(login).map(AuthUser::new).orElseThrow(() -> new JWTVerificationException("Username not found"));
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(authUser, null, authUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        filterChain.doFilter(request,response);
    }

    private String recoverToken(HttpServletRequest request) {
        String authHeader = request.getHeader(appName);
        if (authHeader == null) {
            return null;
        }
        return authHeader.replace("Bearer ", "");
    }
}
