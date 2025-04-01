package com.min.edu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.min.edu.vo.ATM;
import com.min.edu.vo.Booth;

@Configuration
public class RestConfig implements RepositoryRestConfigurer {

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
		RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config.exposeIdsFor(ATM.class,Booth.class), cors);
		cors.addMapping("/api/**")
		.allowedOrigins("http://localhost:3000")
		.allowedMethods("GET","POST","PUT","DELETE","TRACE")
		.allowedHeaders("*")
		.allowCredentials(true)
		.maxAge(3600);
	}

	
	
}
