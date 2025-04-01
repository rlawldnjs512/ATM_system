package com.min.edu.ctrl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.min.edu.service.ATMRepository;
import com.min.edu.vo.ATM;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ATMController {

	private final ATMRepository atmRepository;
	
	@RequestMapping("/atms")
	public Iterable<ATM> getATMs() {
		log.info("전체 자동차(Car) 테이블 조회");
		return atmRepository.findAll();
	}
	
}
