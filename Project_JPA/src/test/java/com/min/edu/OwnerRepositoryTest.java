package com.min.edu;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.min.edu.service.BoothRepository;
import com.min.edu.vo.Booth;

import jakarta.transaction.Transactional;

@SpringBootTest
class OwnerRepositoryTest {

	@Autowired
	private BoothRepository boothRepository;

//	@Transactional // getAtms() : ATM 필드를 접근할 때 세션이 열려있기 때문에 지연로딩이 가능하도록 한다.
//	@Test
	public void boothRepository_Test() {
		
//		List<Booth> find = boothRepository.findAll();
//		List<Booth> find = boothRepository.findAllwithAtms();
//		
		List<Booth> find = boothRepository.findAll();
		for (Booth b : find) {
			System.out.println(b.getColor()+"/"+b.getType());
			System.out.println(b.getAtms()); // Booth에 해당하는 ATM을 조회해보자.
		}
		assertNotNull(find);
		
	}
	
	
	
	
}
