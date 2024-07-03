package com.teste.daviugtsic.service;

import com.teste.daviugtsic.domain.Formulario;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Interface de serviço para operações relacionadas ao Formulário.
 */
public interface FormularioService {

    /**
     * Salva um formulário no sistema.
     *
     * @param dtoRequest Os dados do formulário.
     * @param arquivo O arquivo anexado ao formulário.
     * @param ipAddress O endereço IP do remetente do formulário.
     * @return O formulário salvo.
     */
    Formulario salvarFormulario(Formulario.DtoRequest dtoRequest, MultipartFile arquivo, String ipAddress);

    /**
     * Lista todos os formulários salvos.
     *
     * @return Uma lista de todos os formulários.
     */
    List<Formulario> listarTodos();

    /**
     * Busca um formulário pelo seu ID.
     *
     * @param id O ID do formulário a ser buscado.
     * @return O formulário encontrado.
     */
    Formulario buscarPorId(Long id);
}
