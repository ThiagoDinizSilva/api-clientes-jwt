package br.com.neoapp.mvpClientes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@EnableSpringDataWebSupport
@SpringBootApplication
public class MvpClientesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MvpClientesApplication.class, args);
		
	}

}
