package com.ecommerce.orderService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@EnableTransactionManagement
@OpenAPIDefinition(
		info= @Info(
				title="Order Service API Documentation",
				description="Used to place orders",
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
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

}
