package vn.sunbuy.storyapi.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.sunbuy.storyapi.service.UserDetailsImpl;

public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsImpl userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwtToken = authorizationHeader.substring(7);
            username = jwtTokenUtil.getUsernameFromToken(jwtToken);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        chain.doFilter(request, response);
    }
}






















//import java.io.IOException;
//
////import javax.servlet.FilterChain;
////import javax.servlet.ServletException;
////import javax.servlet.http.HttpServletRequest;
////import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.util.StringUtils;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import vn.sunbuy.storyapi.service.MongoUserDetailsService;
//
//public class AuthTokenFilter extends OncePerRequestFilter {
//	
//	@Autowired
//	private MongoUserDetailsService userDetailsService;
//	
//	@Autowired
//	private JwtUtils jwtUtils;
//
//
//	@Override
////	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
////			throws ServletException, IOException {
////	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain fileChain) 
////			throws ServletException, IOException{
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//		try {
//	      String jwt = parseJwt(request);
//	      if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
//	        String username = jwtUtils.getUserNameFromJwtToken(jwt);
//
//	        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//	        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
//	            userDetails.getAuthorities());
//	        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//	        SecurityContextHolder.getContext().setAuthentication(authentication);
//	      }
//	    } catch (Exception e) {
//	      logger.error("Cannot set user authentication: {}", e);
//	    }
//
//	    filterChain.doFilter(request, response);
//		
//	}
//	
//	private String parseJwt(HttpServletRequest request) {
//	    String headerAuth = request.getHeader("Authorization");
//	
//	    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
//	      return headerAuth.substring(7, headerAuth.length());
//	    }
//	
//	    return null;
//  }
//
//}
