package com.min.edu.sample;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.min.edu.service.ATMRepository;
import com.min.edu.service.BoothRepository;
import com.min.edu.service.CustomerRepository;
import com.min.edu.vo.ATM;
import com.min.edu.vo.Booth;
import com.min.edu.vo.Customer;

@Configuration
public class ATMSample {

	@Bean
	CommandLineRunner commandLineRunner(ATMRepository atmRepository,
			                            BoothRepository bootrepository,
			                            CustomerRepository customerRepository) {
		return args -> {
//			Booth booth1 = new Booth("전면보수형", "Blue");
//			Booth booth2 = new Booth("인출회전형", "Brown");
//			Booth booth3 = new Booth("부스형", "Red");
//			ATM atm1 = new ATM("ATEC", "ATM", "Y", "Y", 30000, 2020, booth1);
//			ATM atm2 = new ATM("HYOSUNG", "ATM", "Y", "N", 55000, 2022, booth2);
//			ATM cd1 = new ATM("PULOON", "CD", "N", "N", 28000, 2024, booth3);
//			ATM cd2 = new ATM("NEOTEC", "CD", "N", "N", 25000, 2021, booth2);
//			
//			bootrepository.saveAll(List.of(booth1,booth2,booth3));
//			atmRepository.saveAll(List.of(atm1,atm2,cd1,cd2));
			
//			Customer c1 = new Customer("user","$2y$04$p/J5h0oUVL.ai.Xk8qh6iOYcfQf6tXBXPoRlEQyNdPquq2dNaAVYC","USER");
//			Customer c2 = new Customer("admin","$2y$04$p/J5h0oUVL.ai.Xk8qh6iOYcfQf6tXBXPoRlEQyNdPquq2dNaAVYC","ADMIN");
//			
//			customerRepository.saveAll(List.of(c1,c2));
		};
	}
	
}
