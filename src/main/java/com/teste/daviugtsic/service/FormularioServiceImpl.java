package com.teste.daviugtsic.service;

import com.teste.daviugtsic.domain.Formulario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.teste.daviugtsic.repository.FormularioRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class FormularioServiceImpl implements FormularioService {

    private static final String UPLOADED_FOLDER = "uploads/";

    @Autowired
    private FormularioRepository formularioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
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
            // Salva a entidade no banco de dados
            return formularioRepository.save(formulario);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Falha ao enviar o arquivo: " + e.getMessage());
        }
    }

    @Override
    public List<Formulario> listarTodos() {
        return formularioRepository.findAll();
    }

    @Override
    public Formulario buscarPorId(Long id) {
        return formularioRepository.findById(id).orElseThrow(() -> new RuntimeException("Formulário não encontrado"));
    }
}