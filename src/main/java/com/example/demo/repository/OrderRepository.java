package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.bean.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	
	public List<Order> findAll();
	
	
	@Transactional(isolation=Isolation.DEFAULT, propagation= Propagation.REQUIRED)
	public void delete(Long id);
	
	
	
	@SuppressWarnings("unchecked")
	@Transactional(isolation=Isolation.DEFAULT, propagation= Propagation.REQUIRED)
	public Order save(Order order);
	
	
	
	@SuppressWarnings("unchecked")
	@Transactional(isolation=Isolation.DEFAULT, propagation= Propagation.REQUIRED)
	public Order saveAndFlush(Order order);
	
	
	

}
