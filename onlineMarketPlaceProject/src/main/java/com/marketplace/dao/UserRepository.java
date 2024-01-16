package com.marketplace.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marketplace.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	

}
