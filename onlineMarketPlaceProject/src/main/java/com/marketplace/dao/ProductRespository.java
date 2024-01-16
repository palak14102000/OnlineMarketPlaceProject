package com.marketplace.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marketplace.dto.ProductDto;
import com.marketplace.entities.Products;

@Repository
public interface ProductRespository extends JpaRepository<Products, Integer> {
	Products findById(int id);

}
