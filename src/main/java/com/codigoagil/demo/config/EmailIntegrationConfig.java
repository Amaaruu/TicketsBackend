package com.codigoagil.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mail.ImapMailReceiver;
import org.springframework.integration.mail.MailReceiver;
import org.springframework.integration.mail.MailReceivingMessageSource;
import org.springframework.messaging.MessageChannel;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Configuration
public class EmailIntegrationConfig {

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Bean
    public MessageChannel receiveEmailChannel() {
        return new DirectChannel();
    }

    @Bean
    @InboundChannelAdapter(channel = "receiveEmailChannel", poller = @Poller(fixedDelay = "60000"))
    public MailReceivingMessageSource mailMessageSource(MailReceiver mailReceiver) {
        return new MailReceivingMessageSource(mailReceiver);
    }

    @Bean
    public MailReceiver imapMailReceiver() {
        String encodedPassword = URLEncoder.encode(password, StandardCharsets.UTF_8);
        String storeUrl = "imaps://" + username + ":" + encodedPassword + "@" + host + ":993/inbox";
        
        ImapMailReceiver receiver = new ImapMailReceiver(storeUrl);
        receiver.setShouldMarkMessagesAsRead(true);
        receiver.setShouldDeleteMessages(false);
        
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.imap.ssl.enable", "true");
        javaMailProperties.put("mail.imaps.partialfetch", "false");
        receiver.setJavaMailProperties(javaMailProperties);
        
        return receiver;
    }
}