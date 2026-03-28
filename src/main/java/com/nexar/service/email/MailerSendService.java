package com.nexar.service.email;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.emails.Email;
import com.nexar.dao.model.customer.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailerSendService implements EmailService {
    @Value("${mailersend.sender-email}")
    private String senderEmail;

    @Value("${mailersend.sender-name}")
    private String senderName;

    @Value("${mailersend.welcome-message-template-id}")
    private String welcomeTemplateId;

    private final MailerSend mailerSend;

    @Override
    public void sendWelcomeMessage(Customer customer) {
        String recipientEmail = customer.getUser().getEmail();
        String recipientName = customer.getUser().getFirstName() + " " + customer.getUser().getLastName();

        var email = buildWelcomeEmail(recipientName, recipientEmail);

        try {
            MailerSendResponse response = mailerSend.emails().send(email);
            log.info("Email sent successfully, to: {}, messageId: {}", recipientEmail, response.messageId);
        } catch (Exception exception) {
            log.error("Failed to send email to {}: {}", recipientEmail, exception.getMessage(), exception);
            throw new RuntimeException("Email sending failed", exception);
        }
    }

    private Email buildWelcomeEmail(String recipientName, String recipientEmail) {
        var email = new Email();
        email.setFrom(senderName, senderEmail);
        email.addRecipient(recipientName, recipientEmail);
        email.setTemplateId(welcomeTemplateId);
        email.addPersonalization("name", recipientName);
        return email;
    }
}