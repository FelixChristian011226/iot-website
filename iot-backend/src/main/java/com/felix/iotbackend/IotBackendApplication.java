package com.felix.iotbackend;

import com.felix.iotbackend.anno.EnableCommonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableCommonConfig
public class IotBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(IotBackendApplication.class, args);
	}

}
