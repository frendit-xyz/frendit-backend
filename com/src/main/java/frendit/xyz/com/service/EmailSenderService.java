package frendit.xyz.com.service;

import frendit.xyz.com.model.response.EmailData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSenderService {

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;

    public void sendHtmlMessage(EmailData email) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        Context context = new Context();
        context.setVariables(email.getProperties());
        helper.setTo(email.getTo());
        helper.setSubject(email.getSubject());
        String html = templateEngine.process(email.getTemplate(), context);
        helper.setText(html, true);

        log.info("Sending email: {} with html body: {}", email, html);
        emailSender.send(message);
    }

    public void buildErrorMail(Exception e) throws MessagingException {
        EmailData email = new EmailData();
        email.setTo("kb-md.sayed.ar.rafi@keex.co.jp");
        email.setSubject("Alert from FMH batch importer");
        email.setTemplate("verify_email.html");
        Map<String, Object> properties = new HashMap<>();

        properties.put("date", new Timestamp(System.currentTimeMillis()));
        properties.put("message", e.getMessage());
        properties.put("details", e.getLocalizedMessage());

        email.setProperties(properties);
        sendHtmlMessage(email);
    }
}
