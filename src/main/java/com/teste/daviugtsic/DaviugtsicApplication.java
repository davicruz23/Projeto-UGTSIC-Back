package com.teste.daviugtsic;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Classe de configuração principal da aplicação Spring Boot.
 */
@SpringBootApplication // Indica que esta classe é a classe principal Spring Boot
@EnableJpaRepositories // Habilita os repositórios JPA na aplicação
public class DaviugtsicApplication {

	/**
	 * Método principal que inicia a aplicação Spring Boot.
	 *
	 * @param args Argumentos de linha de comando
	 */
	public static void main(String[] args) {
		SpringApplication.run(DaviugtsicApplication.class, args);
	}

	/**
	 * Configuração para o ModelMapper, utilizado para mapeamento de objetos.
	 *
	 * @return Instância de ModelMapper configurada
	 */
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	/**
	 * Configuração para o JavaMailSender, utilizado para envio de e-mails.
	 *
	 * @return Instância de JavaMailSender configurada com as propriedades necessárias
	 */
	@Bean
	public JavaMailSender javaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com"); // Configura o host do servidor SMTP
		mailSender.setPort(587); // Configura a porta do servidor SMTP
		mailSender.setUsername("teste@teste.com"); // Configura o nome de usuário do e-mail
		//mailSender.setPassword("cnng bvrw wbdu vkev"); // Configura a senha do e-mail

		// Configurações adicionais para o envio de e-mails
		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.smtp.auth", "true"); // Habilita a autenticação SMTP
		props.put("mail.smtp.starttls.enable", "true"); // Habilita o STARTTLS para segurança na comunicação

		return mailSender;
	}
}
