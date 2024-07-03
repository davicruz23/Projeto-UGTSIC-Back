package com.teste.daviugtsic.service;

import com.teste.daviugtsic.domain.Formulario;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * Classe de serviço para envio de e-mails.
 */
@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String remetente;

    /**
     * Envia um e-mail de texto simples.
     *
     * @param formulario O formulário relacionado ao e-mail.
     * @param destinatario O destinatário do e-mail.
     * @param assunto O assunto do e-mail.
     * @param mensagem O conteúdo da mensagem do e-mail.
     * @return Uma mensagem indicando o sucesso ou falha do envio.
     */
    public String enviarEmailTexto(Formulario formulario, String destinatario, String assunto, String mensagem) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(remetente);
            simpleMailMessage.setTo(destinatario);
            simpleMailMessage.setSubject(assunto);
            simpleMailMessage.setText(mensagem);
            javaMailSender.send(simpleMailMessage);
            return "Email enviado";
        } catch (Exception e) {
            return "Erro ao tentar enviar email " + e.getLocalizedMessage();
        }
    }

    /**
     * Envia um e-mail com um anexo.
     *
     * @param destinatario O destinatário do e-mail.
     * @param assunto O assunto do e-mail.
     * @param mensagem O conteúdo da mensagem do e-mail.
     * @param bytesArquivo O conteúdo do arquivo em bytes.
     * @param nomeArquivo O nome do arquivo.
     * @throws MessagingException Se ocorrer um erro ao enviar o e-mail.
     */
    public void enviarEmailComAnexo(String destinatario, String assunto, String mensagem, byte[] bytesArquivo, String nomeArquivo) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(remetente);
        helper.setTo(destinatario);
        helper.setSubject(assunto);
        helper.setText(mensagem);

        // Adiciona o anexo
        ByteArrayResource arquivoAnexo = new ByteArrayResource(bytesArquivo);
        helper.addAttachment(nomeArquivo, arquivoAnexo);

        javaMailSender.send(message);
    }

}
