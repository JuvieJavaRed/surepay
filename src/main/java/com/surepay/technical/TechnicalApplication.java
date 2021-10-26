package com.surepay.technical;

import com.surepay.technical.services.FileCheckingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;
import java.io.IOException;


@SpringBootApplication
@Slf4j
@ComponentScan("com.surepay.technical")
public class TechnicalApplication {

	@Autowired
	private FileCheckingService fileCheckingService;

	@PostConstruct
	public void listen() throws IOException, InterruptedException {
		log.info("Intializing the listener for the file....");
		fileCheckingService.checkFolder();
	}

	public static void main(String[] args)  {
		SpringApplication.run(TechnicalApplication.class, args);
	}


}
