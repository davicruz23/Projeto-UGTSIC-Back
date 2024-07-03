package com.teste.daviugtsic.service;

import com.teste.daviugtsic.domain.AbstractEntity;
import com.teste.daviugtsic.repository.IGenericRepository;

import jakarta.persistence.EntityNotFoundException; // Exceção para indicar que uma entidade não foi encontrada
import java.util.List;
import java.util.Optional;

/**
 * Classe abstrata que define um serviço genérico para operações CRUD em entidades.
 *
 * @param <E> Tipo da entidade que será manipulada pelo serviço
 * @param <R> Tipo do repositório que estende IGenericRepository e manipula a entidade
 */
public abstract class GenericService<E extends AbstractEntity, R extends IGenericRepository> implements IGenericService<E> {

    R repository; // Repositório utilizado para acessar e manipular dados das entidades

    /**
     * Construtor que inicializa o serviço com um repositório específico.
     *
     * @param repository O repositório que será utilizado pelo serviço para acesso a dados
     */
    public GenericService(R repository) {
        this.repository = repository;
    }

    /**
     * Obtém uma entidade pelo seu ID.
     *
     * @param id ID da entidade a ser recuperada
     * @return A entidade encontrada, se existir
     * @throws EntityNotFoundException Se a entidade com o ID fornecido não existir no repositório
     */
    @Override
    public E getById(Long id) {
        Optional<E> optionalEntity = repository.findById(id);
        if (optionalEntity.isPresent()) {
            return optionalEntity.get();
        } else {
            throw new EntityNotFoundException("Entidade com o ID " + id + " não encontrada.");
        }
    }

    /**
     * Cria uma nova entidade.
     *
     * @param e A entidade a ser criada
     * @return A entidade criada
     */
    @Override
    public E create(E e) {
        return (E) this.repository.save(e);
    }

    /**
     * Atualiza uma entidade existente pelo seu ID.
     *
     * @param updatedEntity A entidade com os dados atualizados
     * @param id ID da entidade a ser atualizada
     * @return A entidade atualizada
     * @throws EntityNotFoundException Se a entidade com o ID fornecido não existir no repositório
     */
    @Override
    public E update(E updatedEntity, Long id) {
        Optional<E> entity = repository.findById(id);
        if (entity.isPresent()) {
            E e = entity.get();
            e.partialUpdate(updatedEntity); // Método para atualizar parcialmente a entidade
            return (E) this.repository.save(e);
        } else {
            throw new EntityNotFoundException("Entidade com o ID " + id + " não encontrada para atualização.");
        }
    }

    /**
     * Deleta uma entidade pelo seu ID.
     *
     * @param id ID da entidade a ser deletada
     */
    @Override
    public void delete(Long id) {
        this.repository.deleteById(id);
    }

    /**
     * Lista todas as entidades do tipo E.
     *
     * @return Lista de todas as entidades presentes no repositório
     */
    @Override
    public List<E> list() {
        return (List<E>) this.repository.findAll();
    }
}
