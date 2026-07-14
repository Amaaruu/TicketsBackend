package com.codigoagil.demo.services;

import com.codigoagil.demo.dtos.CorreoAdjuntoDTO;
import jakarta.mail.Address;
import jakarta.mail.Multipart;
import jakarta.mail.Part;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.jsoup.Jsoup;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmailProcessingService {

    private final TicketService ticketService;

    public EmailProcessingService(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @ServiceActivator(inputChannel = "receiveEmailChannel")
    public void processEmail(org.springframework.messaging.Message<?> springMessage) {
        try {
            Object payload = springMessage.getPayload();
            if (payload instanceof MimeMessage) {
                MimeMessage mimeMessage = (MimeMessage) payload;
                
                String asunto = mimeMessage.getSubject();
                Address[] froms = mimeMessage.getFrom();
                String email = froms == null ? "desconocido@empresa.com" : ((InternetAddress) froms[0]).getAddress();
                String nombre = froms == null ? "Desconocido" : ((InternetAddress) froms[0]).getPersonal();
                
                if (nombre == null) {
                    nombre = email;
                }

                String descripcion = "";
                List<CorreoAdjuntoDTO> adjuntos = new ArrayList<>();

                Object content = mimeMessage.getContent();
                if (content instanceof String) {
                    descripcion = (String) content;
                } else if (content instanceof Multipart) {
                    Multipart multipart = (Multipart) content;
                    descripcion = extractTextAndAttachments(multipart, adjuntos);
                }

                descripcion = Jsoup.parse(descripcion).text();

                ticketService.crearTicketDesdeCorreo(email, nombre, asunto, descripcion, adjuntos);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar el correo entrante", e);
        }
    }

    private String extractTextAndAttachments(Multipart multipart, List<CorreoAdjuntoDTO> adjuntos) throws Exception {
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < multipart.getCount(); i++) {
            Part part = multipart.getBodyPart(i);
            String disposition = part.getDisposition();

            if (Part.ATTACHMENT.equalsIgnoreCase(disposition) || Part.INLINE.equalsIgnoreCase(disposition)) {
                String fileName = part.getFileName();
                if (fileName != null) {
                    InputStream is = part.getInputStream();
                    byte[] bytes = StreamUtils.copyToByteArray(is);
                    String contentType = part.getContentType();
                    if (contentType != null && contentType.contains(";")) {
                        contentType = contentType.split(";")[0];
                    }
                    adjuntos.add(new CorreoAdjuntoDTO(fileName, contentType, bytes));
                }
            } else if (part.isMimeType("text/plain")) {
                result.append((String) part.getContent());
            } else if (part.isMimeType("text/html")) {
                result.append((String) part.getContent());
            } else if (part.getContent() instanceof Multipart) {
                result.append(extractTextAndAttachments((Multipart) part.getContent(), adjuntos));
            }
        }
        return result.toString();
    }
}