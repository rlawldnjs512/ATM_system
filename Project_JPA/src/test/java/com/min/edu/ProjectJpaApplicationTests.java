package com.min.edu;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.min.edu.service.ATMRepository;
import com.min.edu.vo.ATM;

@SpringBootTest
class ProjectJpaApplicationTests {

	@Autowired
	private ATMRepository atmRepository;
	
//	@Test
	void atmRepository_test() {
		List<ATM> find = atmRepository.findByBrand("ATEC");
		System.out.println(find);
		assertEquals(1, find.size());
	}

}
