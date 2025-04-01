package com.min.edu.service;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.min.edu.vo.Booth;

public interface BoothRepository extends JpaRepository<Booth, Long> {

//	@Query("select b from Booth b join fetch b.atms")
//	List<Booth> findAllwithAtms();
	
	@EntityGraph(attributePaths = "atms")
	List<Booth> findAll();
	
	
}
