package com.teste.daviugtsic.service;

import com.teste.daviugtsic.domain.Formulario;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FormularioService {
    Formulario salvarFormulario(Formulario.DtoRequest dtoRequest, MultipartFile arquivo, String ipAddress);
    List<Formulario> listarTodos();
    Formulario buscarPorId(Long id);
}