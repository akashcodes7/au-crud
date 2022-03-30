package io.akashstack.crudimage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class CrudimageApplication extends SpringBootServletInitializer{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	  return application.sources(CrudimageApplication.class);
	}
	public static void main(String[] args) {
		SpringApplication.run(CrudimageApplication.class, args);
		
	}

}
