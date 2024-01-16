package com.marketplace.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter
public class ProductDto {
	
	private int id;
	private String productname;
	private int quantity;
	private int price;
	private MultipartFile file;
	private int userId;

}
