package vn.sunbuy.storyapi.security;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.sunbuy.storyapi.entity.User;
import vn.sunbuy.storyapi.repository.UsersRepository;

public class AuthFilter extends BasicAuthenticationFilter {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UsersRepository userRepository;

    public AuthFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (token != null) {
            String username = jwtTokenUtil.getUsernameFromToken(token.replace("Bearer ", ""));

            if (username != null) {
                User user = userRepository.findByUsername(username).orElse(null);

                if (user != null) {
                    List<SimpleGrantedAuthority> authorities = jwtTokenUtil.getAuthoritiesFromToken(token.replace("Bearer ", ""));
                    return new UsernamePasswordAuthenticationToken(user, null, authorities);
                }

                return null;
            }
        }

        return null;
    }
}
