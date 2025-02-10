package com.ecommerce.productService.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(
		name="Product",
		description="Contains product related data"
		)
public class productDTO {

	private Integer id;
	@Schema(
			name="Product Name",
			example="Car"
			)
	private String name;
	@Schema(
			name="Product description",
			example="Extra Desc"
			)
	private String description;
	@Schema(
			name="Product Price",
			example="7.99"
			)
	private double price;
}
