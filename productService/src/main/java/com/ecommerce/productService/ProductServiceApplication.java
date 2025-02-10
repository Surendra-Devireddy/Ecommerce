package com.ecommerce.productService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@OpenAPIDefinition(
	info= @Info(
			title="Product Service API Documentation",
			description="Used to fetch create products",
			version="v1",
			contact=@Contact(
					name="Surendra",
					email="devireddysurendra10@gmail.com"
					),
			license=@License(
					name="Apache 2.0",
					url=""
					)
			),
	externalDocs=@ExternalDocumentation(
					description="Extra Docs",
					url="Url for Docs"
					)
	)
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}

}
