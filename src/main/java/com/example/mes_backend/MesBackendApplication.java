package com.example.mes_backend;

import com.example.mes_backend.entity.Employee;
import com.example.mes_backend.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class MesBackendApplication {


	public static void main(String[] args) {

		SpringApplication.run(MesBackendApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
		return (args) -> {
			// 이미 존재하는 계정인지 확인하여 중복 삽입 방지
			if (employeeRepository.findByEmployeeId("worker01").isEmpty()) {
				Employee worker = new Employee();
				worker.setEmployeeId("worker01");
				worker.setPassword(passwordEncoder.encode("1234")); // 비밀번호 암호화
				worker.setRole("WORKER");
				worker.setEmployeeNm("김작업"); // 이 라인을 추가하여 직원 이름을 설정

				employeeRepository.save(worker);
				System.out.println("테스트 계정 'worker01'이 생성되었습니다.");
			}
		};
	}

}
