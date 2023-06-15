package vn.sunbuy.storyapi.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
//import org.springdoc.core.SwaggerUiConfigProperties.Csrf;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
//import org.springframework.stereotype.Component;
//import vn.sunbuy.storyapi.security.AuthEntryPointJwt;
//import vn.sunbuy.storyapi.security.AuthTokenFilter;
//import vn.sunbuy.storyapi.service.MongoUserDetailsService;
@Configuration
@EnableWebSecurity
@Profile("local")
@PropertySource("classpath:application-local.yml")
@Slf4j
public class SecurityConfiguration {
	 @Value("${server.port}")
	 private String serverPort;
	 @Bean
	    PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	 @Bean
	    WebMvcConfigurer corsConfigurer() {
	        return new WebMvcConfigurer() {
	         @Override
	         public void addCorsMappings(CorsRegistry registry) {
	          log.info("=======================");
//	          log.info("server ip: " + serverIP);
	          log.info("server port: " + serverPort);
	          log.info("=======================");
	          registry.addMapping("/**")
	           .allowedOrigins("*")
	           .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
	           .allowedHeaders("*")
	           .allowCredentials(false).maxAge(3600);
	         }
	        };
	    }

	@Bean
	 SecurityFilterChain configure(HttpSecurity http) throws Exception {
	     http.csrf().disable()
      		.cors()/*configurationSource(request -> {
	            var cors = new CorsConfiguration();
	            cors.setAllowedOrigins(List.of("http://localhost:3000", "http://localhost:8080"));
	            cors.setAllowedMethods(List.of("GET","POST", "PUT", "DELETE", "OPTIONS"));
	            cors.setAllowedHeaders(List.of("*"));
	            return cors;
	       }).and()*/
      		 .and()
	         .authorizeHttpRequests()
	         .requestMatchers("/api/v1/login", "/api/v1/register", "/api/v1/resetPassword").permitAll()
	         .requestMatchers(HttpMethod.GET).permitAll()
	         .anyRequest().authenticated()
	         .and()
	         .logout()
	         .logoutRequestMatcher(new AntPathRequestMatcher("/api/v1/logout"))
	         .and()
	         .exceptionHandling()
	         .accessDeniedPage("/accessDenied");
//	         .and()
//	         .authenticationProvider(authenticationProvider());
////	      Thêm một lớp Filter kiểm tra jwt
//	        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	     return http.build();
	}	
	
	
	
	
	
//	@Autowired
//    @Qualifier("authenticationManagerBean")
//    private AuthenticationManager authenticationManager;

//	@Autowired
//	MongoUserDetailsService userDetailsService;
//
//	@Autowired
//	private AuthEntryPointJwt unauthorizedHandler;

//	@Bean
//	public AuthTokenFilter authenticationJwtTokenFilter() {
//		return new AuthTokenFilter();
//	}
//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//    	CorsConfiguration configuration = new CorsConfiguration();
//    	configuration.setAllowedOrigins(Arrays.asList("*"));
//    	configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","PATCH"));
//    	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//    	source.registerCorsConfiguration("/**", configuration);
//    	return source;
//    }
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//    	http
//	  		.authorizeHttpRequests((authorize) -> authorize
//	                .requestMatchers("/**").permitAll()
//	                .anyRequest().authenticated()
//	  		)
//	  		.headers((headers) -> headers
//	  		        .frameOptions().disable()
//	  		        .addHeaderWriter((request, response) -> {
//	  		            response.setHeader("Access-Control-Allow-Origin", "*");
//	  		        })
//	  		)
//	      	.csrf((csrf) ->csrf.disable())
//			.logout((logout) -> logout.permitAll()
//			.logoutSuccessHandler((request, response, authentication) -> {
//				response.setStatus(HttpServletResponse.SC_OK);
//			 }));
//	  		return http.build();
//  }
	

//	@Override
//	@Bean
//	public AuthenticationManager authenticationManagerBean() throws Exception {
//	    return super.authenticationManagerBean();
//	}
	
//	  @Bean
//	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		  
//		  http.csrf().disable();
//			http.authorizeRequests().requestMatchers(HttpMethod.GET).permitAll()
//					.requestMatchers(HttpMethod.POST, "/**").permitAll()
//					.requestMatchers(HttpMethod.POST).permitAll()
//					.requestMatchers(HttpMethod.PUT).permitAll()
//					.requestMatchers(HttpMethod.DELETE).permitAll();
////	        http.csrf()
////	          .disable();
////	        http
////	          .authorizeRequests()
////	          .requestMatchers("/**").permitAll()
////	          .and().httpBasic();
////
////	        http.formLogin().disable();
//	        return http.build();
//	    }
	  
//	  @Bean
//	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//	        return http.authorizeHttpRequests(authz -> authz.requestMatchers("/**")
//	            .permitAll()
//	            .anyRequest()
//	            .permitAll()
//	        		)
//        		.headers(headers -> headers.frameOptions().disable())
//	        	.csrf(csrf ->csrf.disable())
//	        	.cors(cors -> cors.disable())
//	            .logout(logout -> logout.permitAll()
//	                .logoutSuccessHandler((request, response, authentication) -> {
//	                    response.setStatus(HttpServletResponse.SC_OK);
//	                }))
//	        	.build();
//	    }

//	@Bean
//	protected void configure(HttpSecurity http) throws Exception {
//	  http
//	    .csrf().disable()
//	    //.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
//	    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//	    .authorizeRequests()
//	    .requestMatchers("/", "/api/auth/login", "/api/auth/signup", "/logout").permitAll()
//	    .requestMatchers("/user" , "/user/**").permitAll()
//	    .requestMatchers("/story" , "/story/**").permitAll()
//	    .anyRequest()
//	    .authenticated()
//	    .and().httpBasic()
//	    .and().sessionManagement().disable();
//	  //http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//	}



//	@Bean
//	public WebSecurityCustomizer webSecurityCustomizer() {
//		return (web) -> web.ignoring().requestMatchers(HttpMethod.GET, "/v2/api-docs", "/webjars/**",
//				"/*.html", "/favicon.ico", "/**/*.css", "/**/*.js", "/v2/api-docs/**", "/swagger-resources/**","/swagger-ui/**");
//	}
}