package com.marketplace.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="PRODUCTS")
@NoArgsConstructor
@Getter @Setter
public class Products {
	@Id
	@GeneratedValue
	private int id;
	private String productname;
	private int quantity;
	private int price;
	private String location;
	private int userId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getImage() {
		return location;
	}
	public void setImage(String image) {
		this.location = image;
	}
	@Override
	public String toString() {
		return "Products [id=" + id + ", productname=" + productname + ", quantity=" + quantity + ", price=" + price
				+ ", image=" + location + "]";
	}
	
}

