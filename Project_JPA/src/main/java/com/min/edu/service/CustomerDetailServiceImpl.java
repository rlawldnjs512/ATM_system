package com.min.edu.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.min.edu.vo.Customer;

@Service
public class CustomerDetailServiceImpl implements UserDetailsService {

	@Autowired
	private CustomerRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 1) 전달받은 username을 통해서 사용자를 조회해본다.
		Optional<Customer> user = repository.findByUsername(username);
		// 2) security에서 사용자를 판단하기 위한 UserBuilder 객체를 생성한다.
		UserBuilder builder = null;
		if(user.isPresent()) {
			// 3) 사용자가 있다면 Customer으로 변환한다.
			Customer currentUser = user.get();
			// 4) withUsername() 함수는 username을 입력받아 반환값으로 UserBuilder 객체를 반환한다.
			builder = User.withUsername(username);
			// 5) DB에서 검색된 password를 할당한다.
			builder.password(currentUser.getPassword());
			// 6) DB에서 검색된 role을 할당한다.
			builder.roles(currentUser.getRole());
		} else {
			throw new UsernameNotFoundException("사용자가 없습니다.");
		}
		return builder.build();
	}

}
