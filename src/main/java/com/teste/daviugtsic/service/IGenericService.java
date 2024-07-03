package com.teste.daviugtsic.service;

import com.teste.daviugtsic.domain.AbstractEntity;

import java.util.List;

/**
 * Interface genérica para serviços que oferecem operações CRUD básicas em entidades.
 *
 * @param <E> Tipo da entidade que será manipulada pelo serviço
 */
public interface IGenericService<E extends AbstractEntity> {

    /**
     * Cria uma nova entidade.
     *
     * @param e A entidade a ser criada
     * @return A entidade criada
     */
    public E create(E e);

    /**
     * Atualiza uma entidade existente pelo seu ID.
     *
     * @param e A entidade com os dados atualizados
     * @param id ID da entidade a ser atualizada
     * @return A entidade atualizada
     */
    public E update(E e, Long id);

    /**
     * Deleta uma entidade pelo seu ID.
     *
     * @param id ID da entidade a ser deletada
     */
    public void delete(Long id);

    /**
     * Lista todas as entidades do tipo E.
     *
     * @return Lista de todas as entidades do tipo E
     */
    public List<E> list();

    /**
     * Obtém uma entidade pelo seu ID.
     *
     * @param id ID da entidade a ser recuperada
     * @return A entidade encontrada, se existir
     */
    public E getById(Long id);
}
