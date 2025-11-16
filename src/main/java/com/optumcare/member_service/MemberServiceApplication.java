package com.optumcare.member_service;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@EnableKafka
@SpringBootApplication
@EnableDiscoveryClient
public class MemberServiceApplication {

	@Bean
	public NewTopic memberEventsTopic() {
		return TopicBuilder.name("member-events")
				.partitions(1)
				.replicas(1)
				.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(MemberServiceApplication.class, args);
	}

}
