package com.example.mes_backend;

import com.example.mes_backend.entity.Employee;
import com.example.mes_backend.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;

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
				worker.setEmployeeNm("김작업");

				// 필수 필드들 설정
				worker.setDepartmentId(1);              // 예: 1번 부서 (존재하는 부서 ID여야 함)
				worker.setPositionId(1);                // 예: 1번 직책 (존재하는 포지션 ID여야 함)
				worker.setHireDate(Date.valueOf("2023-01-01")); // java.sql.Date 로 설정
				worker.setPassword(passwordEncoder.encode("1234"));
				worker.setRole("WORKER");

				employeeRepository.save(worker);
				System.out.println("테스트 계정 'worker01'이 생성되었습니다.");
			}
		};
	}

}
