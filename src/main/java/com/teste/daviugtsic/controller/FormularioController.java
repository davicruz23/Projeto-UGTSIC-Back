package com.teste.daviugtsic.controller;

import com.teste.daviugtsic.domain.Formulario;
import com.teste.daviugtsic.service.EmailService;
import com.teste.daviugtsic.service.FormularioService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * Controlador REST para gerenciar os formulários.
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/formulario")
public class FormularioController {

    private static final Logger logger = LoggerFactory.getLogger(FormularioController.class);

    @Autowired
    private FormularioService formularioService;

    @Autowired
    private EmailService emailService;

    // Lista de tipos MIME permitidos para o upload de arquivos
    private final List<String> allowedMimeTypes = Arrays.asList(
            "application/pdf",
            "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
    );

    /**
     * Manipula o upload de arquivos e o envio do formulário.
     *
     * @param nome          Nome do candidato
     * @param email         Email do candidato
     * @param telefone      Telefone do candidato
     * @param cargoDesejado Cargo desejado pelo candidato
     * @param escolaridade  Escolaridade do candidato
     * @param observacao    Observações adicionais
     * @param arquivo       Arquivo enviado (currículo)
     * @param request       HttpServletRequest para capturar o endereço IP do cliente
     * @return ResponseEntity com o resultado do processamento
     */
    @PostMapping
    public ResponseEntity<?> handleFileUpload(@RequestParam("nome") String nome,
                                              @RequestParam("email") String email,
                                              @RequestParam("telefone") String telefone,
                                              @RequestParam("cargoDesejado") String cargoDesejado,
                                              @RequestParam("escolaridade") String escolaridade,
                                              @RequestParam("observacao") String observacao,
                                              @RequestParam("arquivo") MultipartFile arquivo,
                                              HttpServletRequest request) {

        // Valida o tipo de conteúdo do arquivo
        if (!allowedMimeTypes.contains(arquivo.getContentType())) {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                    .body("Arquivo não suportado. Apenas .doc, .docx e .pdf são permitidos.");
        }

        // Verifica se o email ou telefone já existem
        if (formularioService.existeEmail(email)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Email em uso.");
        }

        // Cria um objeto DTO para capturar os dados do formulário
        Formulario.DtoRequest dtoRequest = new Formulario.DtoRequest();
        dtoRequest.setNome(nome);
        dtoRequest.setEmail(email);
        dtoRequest.setTelefone(telefone);
        dtoRequest.setCargoDesejado(cargoDesejado);
        dtoRequest.setEscolaridade(escolaridade);
        dtoRequest.setObservacao(observacao);

        // Capturar o endereço IP do cliente
        String clientIpAddress = request.getRemoteAddr();

        try {
            // Salva o formulário e obtém a entidade salva
            Formulario formulario = formularioService.salvarFormulario(dtoRequest, arquivo, clientIpAddress);

            // Enviar e-mail de confirmação para o usuário
            String assuntoUsuario = "Confirmação de recebimento do formulário";
            String mensagemUsuario = "Olá " + nome + ",\n\n"
                    + "Seu formulário foi recebido com sucesso. Em breve entraremos em contato.";
            emailService.enviarEmailTexto(formulario, email, assuntoUsuario, mensagemUsuario);

            // Enviar e-mail com as informações do formulário e currículo para o e-mail do administrador
            String assuntoAdmin = "Novo formulário de candidatura recebido";
            String mensagemAdmin = "Um novo formulário foi recebido:\n\n"
                    + "Nome: " + nome + "\n"
                    + "Email: " + email + "\n"
                    + "Telefone: " + telefone + "\n"
                    + "Cargo Desejado: " + cargoDesejado + "\n"
                    + "Escolaridade: " + escolaridade + "\n"
                    + "Observação: " + observacao + "\n";
            String destinatarioAdmin = "emaildevteste60@gmail.com";

            // Anexar o arquivo (currículo)
            byte[] bytesArquivo = arquivo.getBytes();
            String nomeArquivo = arquivo.getOriginalFilename();
            emailService.enviarEmailComAnexo(destinatarioAdmin, assuntoAdmin, mensagemAdmin, bytesArquivo, nomeArquivo);

            // Retorna a resposta com o formulário convertido para DTO
            return ResponseEntity.ok(Formulario.DtoResponse.convertToDto(formulario, new ModelMapper()));
        } catch (Exception e) {
            logger.error("Erro ao enviar formulário: ", e);
            return ResponseEntity.status(500).body("Falha ao enviar o arquivo: " + e.getMessage());
        }
    }
}
