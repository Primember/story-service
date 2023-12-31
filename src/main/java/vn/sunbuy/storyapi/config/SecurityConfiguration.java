package vn.sunbuy.storyapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
//	@Autowired
//	private JwtAuthenicationFilter jwtAuthenicationFilter;
//	@Autowired
//	private AuthenticationProvider authenticationProvider;
	@Bean
	 SecurityFilterChain configure(HttpSecurity http) throws Exception {
//		    http.csrf().disable().authorizeHttpRequests().requestMatchers("/user/signup", "/user/signin").permitAll()
//		            .requestMatchers("/stories/**").hasRole("USER")
//		            .requestMatchers("/admin/**").hasRole("ADMIN").anyRequest().authenticated().and().exceptionHandling()
//		            .authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
//		            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//		    http.addFilterBefore(new JwtAuthenticationFilter(authenticationManagerBean()), UsernamePasswordAuthenticationFilter.class);
//		    http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
//		}
		
		
		
		
		
		
		
		
	     http.csrf().disable();
	     http
	         .authorizeHttpRequests()
	         .requestMatchers("/**").permitAll()
//	         .anyRequest().authenticated()
	         
	    ;
	     return http.build();
	
	  }	

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
//}