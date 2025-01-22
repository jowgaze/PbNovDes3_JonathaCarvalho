package br.com.compass.eventmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsEventManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsEventManagementApplication.class, args);
	}

}
