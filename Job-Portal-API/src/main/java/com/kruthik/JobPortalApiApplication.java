package com.kruthik;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@SpringBootApplication
@EnableScheduling
@OpenAPIDefinition(
	    info = @Info(
	        title = "Job Portal API",
	        version = "1.0.0",
	        description = "REST API for a Job Portal application allowing users to register, login, post jobs, apply for jobs, and manage applications.",
	        contact = @Contact(
	            name = "Kruthik SP",
	            email = "kruthiksp62@gmail.com",
	            url = "https://github.com/Kruthiksp"
	        ),
	        license = @License(
	            name = "Apache 2.0",
	            url = "http://www.apache.org/licenses/LICENSE-2.0.html"
	        )
	    ),
	    servers = {
	        @Server(url = "http://localhost:8080", description = "Local development server")/*,
	        @Server(url = "https://api.jobportal.com", description = "Production server")*/
	    }
	)
@PropertySource({"classpath:/cloudinary.properties", "classpath:/documentation.properties"})
public class JobPortalApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobPortalApiApplication.class, args);
	}

}
