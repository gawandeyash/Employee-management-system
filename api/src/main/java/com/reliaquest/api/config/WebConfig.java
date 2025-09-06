package com.reliaquest.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import com.reliaquest.api.constants.ApiConstants;

@Configuration
public class WebConfig {

	@Bean
	WebClient webClient() {

		WebClient webClient = WebClient.builder().baseUrl(ApiConstants.REST_API_BASE_URL)
				.defaultCookie("cookie-name", "cookie-value")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();

		return webClient;
	}

}
