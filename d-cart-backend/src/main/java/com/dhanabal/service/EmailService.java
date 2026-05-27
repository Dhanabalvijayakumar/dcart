package com.dhanabal.service;

public interface EmailService {

	void sendRegistrationMail(String toEmail, String userName, String mobile, String password);

}
