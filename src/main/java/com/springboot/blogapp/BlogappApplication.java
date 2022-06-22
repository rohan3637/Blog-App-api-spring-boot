package com.springboot.blogapp;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.springboot.blogapp.dao.RoleRepository;
import com.springboot.blogapp.entities.Role;
import com.springboot.blogapp.helper.ApiConstants;

@SpringBootApplication
public class BlogappApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(BlogappApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.passwordEncoder.encode("Rohan@11"));
		try {
			Role role1 = new Role();
			role1.setId(ApiConstants.ADMIN_USER);
			role1.setRole("ROLE_ADMIN");

			Role role2 = new Role();
			role2.setId(ApiConstants.NORMAL_USER);
			role2.setRole("ROLE_NORMAL");

			List<Role> roles = List.of(role1, role2);
			List<Role> results =  roleRepository.saveAll(roles);
			results.forEach(role -> {
				System.out.println(role);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	

}
