package com.codigoagil.demo.services;

import jakarta.mail.internet.MimeMessage;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class EmailProcessingService {

    @ServiceActivator(inputChannel = "receiveEmailChannel")
    public void processEmail(Message<?> message) {
        try {
            Object payload = message.getPayload();
            if (payload instanceof MimeMessage) {
                MimeMessage mimeMessage = (MimeMessage) payload;
                System.out.println("Nuevo correo detectado. Asunto: " + mimeMessage.getSubject());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al leer el correo entrante", e);
        }
    }
}