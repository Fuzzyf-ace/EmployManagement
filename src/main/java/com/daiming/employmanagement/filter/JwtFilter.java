package com.daiming.employmanagement.filter;

import com.daiming.employmanagement.util.JwtUtil;
import com.daiming.employmanagement.model.UserRole;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final String HEADER = "Authorization";
    private final String PREFIX = "Bearer";
    @Autowired
    JwtUtil jwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HEADER);
        //get token
        String token = null;
        if (authorizationHeader == null || !authorizationHeader.startsWith(PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        //validate token
        token = authorizationHeader.substring(PREFIX.length());
        try {
            if (token != null && jwtUtil.validateToken(token) && SecurityContextHolder.getContext().getAuthentication() == null) {
                String username = jwtUtil.extractUsername(token);
                System.out.println(username);
                List<GrantedAuthority> grantedAuthorityList = Arrays.asList(new GrantedAuthority[]{new SimpleGrantedAuthority(UserRole.EMPLOYER.name())});
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, null, grantedAuthorityList);
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(SecurityContextHolder.getContext().getAuthentication());

        filterChain.doFilter(request, response);

    }
}
