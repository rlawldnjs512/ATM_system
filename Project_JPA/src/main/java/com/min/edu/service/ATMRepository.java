package com.min.edu.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.min.edu.vo.ATM;

@RepositoryRestResource(path = "machines")
public interface ATMRepository extends JpaRepository<ATM, Long> {

//	List<ATM> findByBrand(String brand);
//	List<ATM> findByModel(String model);
	List<ATM> findByProductYear(Integer productYear);
	
	List<ATM> findByBrandAndModel(String brand, String model);
	List<ATM> findByBrandOrModel(String brand, String model);
	
	List<ATM> findByBrandOrderByProductYearAsc(String brand);
	
	@Query("select a from ATM a where a.brand=?1")
	List<ATM> findByBrandQuery(String findBrand);
	
	@Query("select a from ATM a where a.brand like %?1")
	List<ATM> findByBrandEndsWith(String brand);
	
	@Query("select a from ATM a where a.brand=:brand")
	List<ATM> findByBrand(@Param("brand") String brand);
	
	@Query("select a from ATM a where a.model=:model")
	List<ATM> findByModel(@Param("model") String model);
	
}
