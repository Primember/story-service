package vn.sunbuy.storyapi.security;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;



@Component
public class JwtTokenUtil {
	@Value("${jwt.signInKey}")
    private String SIGNINKEY;
    private static final long JWT_TOKEN_VALIDITY = 10 * 60 * 60;
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername(), userDetails.getAuthorities().toString());
    }

    @SuppressWarnings("deprecation")
	private String createToken(Map<String, Object> claims, String subject, String authorities) {
        return Jwts.builder().setClaims(claims).setSubject(subject).claim("authorities", authorities)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, SIGNINKEY).compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    @SuppressWarnings("deprecation")
	private Date getExpirationDateFromToken(String token) {
        return Jwts.parser().setSigningKey(SIGNINKEY).parseClaimsJws(token).getBody().getExpiration();
    }

    @SuppressWarnings("deprecation")
	public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(SIGNINKEY).parseClaimsJws(token).getBody().getSubject();
    }

    public List<SimpleGrantedAuthority> getAuthoritiesFromToken(String token) {
        List<String> stringAuthorities = (List<String>)Jwts.parser().setSigningKey(SIGNINKEY).parseClaimsJws(token)
                .getBody().get("authorities", List.class);

       List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (String stringAuthority : stringAuthorities) {
            authorities.add(new SimpleGrantedAuthority(stringAuthority));
        }

        return authorities;
    }
}


//package vn.sunbuy.storyapi.security;
//
//import java.util.Date;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.MalformedJwtException;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.SignatureException;
//import io.jsonwebtoken.UnsupportedJwtException;
//
//@Component
//public class JwtUtils {
//	
//	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
//
//	@Value("${jwt.clientSecret}")
//	private String jwtSecret;
//
//	@Value("${jwt.expire}")
//	private int jwtExpirationMs;
//
//	public String generateJwtToken(Authentication authentication) {
//
//		UserDetails userDetails = (UserDetails)authentication.getPrincipal();
//
//
//		return Jwts.builder()
//				.setSubject((userDetails.getUsername()))
//				.setIssuedAt(new Date())
//				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
//				.signWith(SignatureAlgorithm.HS512, jwtSecret)
//				.compact();
//	}
//
//	public String getUserNameFromJwtToken(String token) {
//		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
//	}
//
//	public boolean validateJwtToken(String authToken) {
//		try {
//			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
//			return true;
//		} catch (SignatureException e) {
//			logger.error("Invalid JWT signature: {}", e.getMessage());
//		} catch (MalformedJwtException e) {
//			logger.error("Invalid JWT token: {}", e.getMessage());
//		} catch (ExpiredJwtException e) {
//			logger.error("JWT token is expired: {}", e.getMessage());
//		} catch (UnsupportedJwtException e) {
//			logger.error("JWT token is unsupported: {}", e.getMessage());
//		} catch (IllegalArgumentException e) {
//			logger.error("JWT claims string is empty: {}", e.getMessage());
//		}
//
//		return false;
//	}
//
//}

