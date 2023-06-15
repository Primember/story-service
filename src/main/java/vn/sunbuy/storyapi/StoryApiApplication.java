package vn.sunbuy.storyapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@SpringBootApplication
public class StoryApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoryApiApplication.class, args);
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*");
			}
		};
	}
	
//	@Bean
//    public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDbFactory, MongoMappingContext context) {
// 
//        MappingMongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory), context);
//        		//new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory), context);
//        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
//        converter.setCustomConversions(customConversions());
//        converter.afterPropertiesSet();
//        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory, converter);
// 
//        return mongoTemplate;
//
//  }
	
//	@Bean
//	public CustomConversions customConversions() {
//	    List<Converter<?, ?>> converters = new ArrayList<Converter<?, ?>>();
//	    converters.add(new ConvertToDateViaSqlDate());
//	    converters.add(new ConvertToLocalDateViaInstant());
//	    return new CustomConversions(CustomConversions.StoreConversions.NONE, converters);
//	}
	

}
