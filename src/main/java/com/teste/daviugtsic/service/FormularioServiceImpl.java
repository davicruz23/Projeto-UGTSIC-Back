package com.teste.daviugtsic.service;

import com.teste.daviugtsic.domain.Formulario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.teste.daviugtsic.repository.FormularioRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Implementação da interface de serviço para operações relacionadas ao Formulário.
 */
@Service
public class FormularioServiceImpl implements FormularioService {

    private static final String UPLOADED_FOLDER = "uploads/";

    @Autowired
    private FormularioRepository formularioRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Salva um formulário no sistema.
     *
     * @param dtoRequest Os dados do formulário.
     * @param arquivo    O arquivo anexado ao formulário.
     * @param ipAddress  O endereço IP do remetente do formulário.
     * @return O formulário salvo.
     */
    @Override
    @Transactional
    public Formulario salvarFormulario(Formulario.DtoRequest dtoRequest, MultipartFile arquivo, String ipAddress) {
        if (arquivo.isEmpty()) {
            throw new IllegalArgumentException("Por favor, selecione um arquivo para fazer upload.");
        }

        try {
            // Cria o diretório se não existir
            Path path = Paths.get(UPLOADED_FOLDER);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            // Salva o arquivo
            byte[] bytes = arquivo.getBytes();
            Path filePath = path.resolve(arquivo.getOriginalFilename());
            Files.write(filePath, bytes);

            // Converte o DTO para a entidade
            Formulario formulario = Formulario.DtoRequest.convertToEntity(dtoRequest, modelMapper);
            formulario.setArquivo(arquivo.getOriginalFilename());
            formulario.setIpAddress(ipAddress);

            // Verifica se o email ou telefone já existem
            if (existeEmail(dtoRequest.getEmail())) {
                throw new IllegalArgumentException("Email em uso.");
            }

            // Salva a entidade no banco de dados
            return formularioRepository.save(formulario);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Falha ao enviar o arquivo: " + e.getMessage());
        }
    }

    /**
     * Verifica se já existe um formulário com o email especificado.
     *
     * @param email O email a ser verificado.
     * @return true se já existe um formulário com o email, false caso contrário.
     */
    @Override
    public boolean existeEmail(String email) {
        return formularioRepository.existsByEmail(email);
    }

    /**
     * Lista todos os formulários salvos.
     *
     * @return Uma lista de todos os formulários.
     */
    @Override
    public List<Formulario> listarTodos() {
        return formularioRepository.findAll();
    }

    /**
     * Busca um formulário pelo seu ID.
     *
     * @param id O ID do formulário a ser buscado.
     * @return O formulário encontrado.
     */
    @Override
    public Formulario buscarPorId(Long id) {
        return formularioRepository.findById(id).orElseThrow(() -> new RuntimeException("Formulário não encontrado"));
    }
}
