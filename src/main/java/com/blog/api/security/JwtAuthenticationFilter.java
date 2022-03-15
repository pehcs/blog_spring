package com.blog.api.security;

import com.blog.api.models.PrincipalUser;
import com.blog.api.models.User;
import com.blog.api.utils.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            User usuario = new ObjectMapper()
                    .readValue(request.getInputStream(), User.class);

            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    usuario.getUsername(),
                    usuario.getPassword(),
                    new ArrayList<>()
            ));

        } catch (IOException e) {
            throw new RuntimeException("Falha ao autenticar usuario", e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        PrincipalUser usuarioData = (PrincipalUser) authResult.getPrincipal();
        String username = usuarioData.getUsername();

        String token = new JwtTokenUtil().generateAccessToken(username);

        response.getWriter().write(token);
        response.getWriter().flush();
    }
}


