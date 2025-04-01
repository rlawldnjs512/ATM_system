package com.min.edu.service;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.min.edu.vo.Customer;

@RepositoryRestResource(exported = false)
public interface CustomerRepository extends CrudRepository<Customer, Long> {

	Optional<Customer> findByUsername(String username);
	
}
