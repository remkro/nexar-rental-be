package com.nexar.infrastructure.config;

import com.mailersend.sdk.MailerSend;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailerSendConfig {
    @Bean
    public MailerSend mailerSend(@Value("${mailersend.token}") String token) {
        MailerSend result = new MailerSend();
        result.setToken(token);
        return result;
    }
}