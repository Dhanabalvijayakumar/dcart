package com.dhanabal.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmailServiceImpl implements EmailService {

	@Value("${BREVO_API_KEY}")
	private String brevoApiKey;

	@Override
	public void sendRegistrationMail(String toEmail, String userName, String mobile, String password) {

		RestTemplate restTemplate = new RestTemplate();

		String url = "https://api.brevo.com/v3/smtp/email";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		headers.set("api-key", brevoApiKey);

		Map<String, Object> requestBody = Map.of("sender", Map.of("name", "DCart", "email", "dproductions8228@gmail.com"),
				"to", List.of(Map.of("email", toEmail)), "subject", "Welcome to DCart", "htmlContent",
				"<h2>Welcome to DCart</h2>" + "<p>Your account has been created successfully.</p>" + "<p><b>Email:</b> "
						+ toEmail + "</p>" + "<p><b>Mobile:</b> " + mobile + "</p>"
						+ "<p><b>Password:</b> " + password + "</p>"
				);

		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

		restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
	}
}