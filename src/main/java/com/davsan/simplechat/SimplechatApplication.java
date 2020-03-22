package com.davsan.simplechat;

import com.davsan.simplechat.utils.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties(AppProperties.class)
public class SimplechatApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimplechatApplication.class, args);
	}

}
