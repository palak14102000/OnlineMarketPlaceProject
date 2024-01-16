package com.marketplace.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="ORDERS")
@Getter @Setter
public class Order {
	@Id
	@GeneratedValue
	private int id;
	private int userId;
	private int productId;
	private String name;
	private int quantity;
	private int totalAmount;

}
