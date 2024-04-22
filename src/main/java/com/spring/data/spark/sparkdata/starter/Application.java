package com.spring.data.spark.sparkdata.starter;

import com.spring.data.spark.sparkdata.starter.model.Speaker;
import com.spring.data.spark.sparkdata.starter.repository.SpeakerRepo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

// In this project I made starter and the lib in one project, only during beta-test to make testing easier

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(Application.class, args);

		SpeakerRepo speakerRepo = configurableApplicationContext.getBean(SpeakerRepo.class);
		List<Speaker> byAgeBetween = speakerRepo.findByAgeBetween(50, 150);
		byAgeBetween.forEach(System.out::println);

	}

}
