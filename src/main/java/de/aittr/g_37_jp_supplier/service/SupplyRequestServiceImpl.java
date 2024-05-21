package de.aittr.g_37_jp_supplier.service;

import de.aittr.g_37_jp_supplier.service.interfaces.SupplyRequestService;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class SupplyRequestServiceImpl implements SupplyRequestService {

    private JavaMailSender sender;
    private Configuration mailConfig;
    private String fromEmail;
    private String supplierEmail;

    public SupplyRequestServiceImpl(
            JavaMailSender sender,
            Configuration mailConfig,
            @Value("${spring.mail.username}") String fromEmail,
            @Value("${supplier.email}") String supplierEmail) {
        this.sender = sender;
        this.mailConfig = mailConfig;
        this.fromEmail = fromEmail;
        this.supplierEmail = supplierEmail;

        mailConfig.setDefaultEncoding("UTF-8");
        mailConfig.setTemplateLoader(
                new ClassTemplateLoader(
                        SupplyRequestServiceImpl.class, "/mail/"));
    }

    @Override
    public void sendSupplyRequest(Map<String, Integer> supplyRequest) {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
        String text = generateEmail(supplyRequest);

        try {
            helper.setFrom(fromEmail);
            helper.setTo(supplierEmail);
            helper.setSubject("Supply request");
            helper.setText(text, true);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        sender.send(message);
    }

    private String generateEmail(Map<String, Integer> supplyRequest) {
        try {
            Template template = mailConfig.getTemplate("supply-request-mail.ftlh");

            Map<String, Object> model = new HashMap<>();
            model.put("request", supplyRequest);

            return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}