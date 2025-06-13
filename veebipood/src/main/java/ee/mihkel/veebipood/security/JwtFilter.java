package ee.mihkel.veebipood.security;

import ee.mihkel.veebipood.entity.PersonRole;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        // Bearer --> token
        // Basic --> kasutajanimi+parool
        // Digest --> kasutajanimi+parool peidetult
        // OAuth --> Google/FB/Github sisselogimine, kus saab küsida kokkulepitud infot
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.replace("Bearer ", "");
            Claims claims = jwtUtil.parseToken(token);
            String email = claims.getSubject(); // Kui on vale token või on aegunud token, siis siin real visatakse error
            PersonRole role = PersonRole.valueOf(claims.get("role").toString());
            System.out.println(role);
            Authentication authentication = getAuthentication(role, email);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private static Authentication getAuthentication(PersonRole role, String email) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (role.equals(PersonRole.ADMIN)) {
            GrantedAuthority authority = new SimpleGrantedAuthority(role.name());
            authorities.add(authority);
        }
        if (role.equals(PersonRole.SUPERADMIN)) {
            GrantedAuthority authorityAdmin = new SimpleGrantedAuthority("ADMIN");
            authorities.add(authorityAdmin);
            GrantedAuthority authoritySuperAdmin = new SimpleGrantedAuthority(role.name());
            authorities.add(authoritySuperAdmin);
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(email, "", authorities);
        return authentication;
    }
}
