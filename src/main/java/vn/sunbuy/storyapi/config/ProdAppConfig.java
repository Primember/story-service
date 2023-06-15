//package vn.sunbuy.storyapi.config;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import lombok.extern.slf4j.Slf4j;
//@Configuration
//@Profile("local")
//@PropertySource("classpath:application-local.yml")
//@Slf4j
//public class ProdAppConfig {
// @Value("${server.port}")
// private String serverPort;
// 
// @Bean
//    PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
// @Bean
//    WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//         @Override
//         public void addCorsMappings(CorsRegistry registry) {
//          log.info("=======================");
//          log.info("server port: " + serverPort);
//          log.info("=======================");
//          registry.addMapping("/**")
//           .allowedOrigins("*")
//           .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
//           .allowedHeaders("*")
//           .allowCredentials(false).maxAge(3600);
//         }
//        };
//    }
//}
