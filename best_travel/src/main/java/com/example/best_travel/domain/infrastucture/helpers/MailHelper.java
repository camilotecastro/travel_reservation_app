package com.example.best_travel.domain.infrastucture.helpers;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class MailHelper {

  private final JavaMailSender emailSender;

  public void sendEmail(String to, String name, String product) {
    log.info("Sending email to: {}", to);
    // send email
    MimeMessage mimeMessage = emailSender.createMimeMessage();
    // set message
    String htmlContent = this.readHTMLTemplate(name, product);
    // send email
    log.info("Email sent to: {}", to);
    try {
      mimeMessage.setFrom(new InternetAddress("camilox545@gmail.com"));
      mimeMessage.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(to));
      mimeMessage.setContent(htmlContent, "text/html");
      emailSender.send(mimeMessage);
    } catch (MessagingException e) {
      log.error("Error sending email to: {}", to);
    }
  }

  private String readHTMLTemplate(String name, String product) {
    try (var lines = Files.lines(Path.of("src/main/resources/templates/email_template.html"))) {
      var html = lines.collect(Collectors.joining());
      return html.replace("{{name}}", name).replace("{{product}}", product);
    } catch (IOException e) {
      log.error("Error reading email template");
      throw new RuntimeException(e);
    }

  }

}
