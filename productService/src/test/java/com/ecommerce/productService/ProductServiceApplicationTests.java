package com.ecommerce.productService;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers //used for the testing by using the containers of databases message queues
class ProductServiceApplicationTests {
	
	@Container
	static MySQLContainer mySQLContainer= new MySQLContainer("mysql:9.2.0");
	
	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
		//:: is a method reference that supplies the value dynamically at runtime 
		dynamicPropertyRegistry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
	}
	
	 
	
	
	@Test
	void contextLoads() {
	}

}
