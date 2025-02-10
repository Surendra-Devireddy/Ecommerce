package com.ecommerce.inventoryService;

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
				title="Inventory Service API Documentation",
				description="Used to check inventory",
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
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

}
