package com.backend.mvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.WebContentInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration     //<mvc:annotation-driven></mvc:annotation-driven>
@EnableWebMvc      //scan the components for creating the beans - controllers, services and repository
@ComponentScan("com.backend.mvc")
public class WebAppConfig extends WebMvcConfigurerAdapter{
	
	//----------- Bean configuration of view resolver to read view pages  -------------//
	
	@Bean
	public InternalResourceViewResolver viewResolver(){
		InternalResourceViewResolver viewResolver=new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

	//----------- Bean configuration for resource handler  -------------//

	public void addResourceHandlers(ResourceHandlerRegistry registry){
		registry.addResourceHandler("/resources/**").addResourceLocations("/WEB-INF/resources/");
	}

	//----------- Bean configuration of CommonsMultipartResolver for images -------------//

	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver getCommonsMultipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(20971520); // 20MB
		multipartResolver.setMaxInMemorySize(1048576);	// 1MB
		return multipartResolver;
	}
	
	//----------- Bean configuration of WebContentInterceptor for web-socket (or chat)  -------------//

	@Bean
	  public WebContentInterceptor webContentInterceptor() {
	    WebContentInterceptor interceptor = new WebContentInterceptor();
	    interceptor.setCacheSeconds(0);
	    interceptor.setUseExpiresHeader(true);
	    interceptor.setUseCacheControlHeader(true);
	    interceptor.setUseCacheControlNoStore(true);

	    return interceptor;
	  }

}
