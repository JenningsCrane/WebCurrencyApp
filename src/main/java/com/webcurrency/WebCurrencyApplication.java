package com.webcurrency;

import com.webcurrency.parser.XMLParser;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WebCurrencyApplication {
	public static void main(String[] args) {
		SpringApplication.run(WebCurrencyApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
