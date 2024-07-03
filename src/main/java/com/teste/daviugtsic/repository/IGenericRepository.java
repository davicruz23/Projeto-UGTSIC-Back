package com.teste.daviugtsic.repository;

import com.teste.daviugtsic.domain.AbstractEntity;
import org.springframework.data.repository.ListCrudRepository;

/**
 * Interface genérica para repositórios de entidades que estendem AbstractEntity.
 * Estende ListCrudRepository para fornecer métodos CRUD e operações em lista.
 *
 * @param <E> O tipo da entidade que estende AbstractEntity.
 */
public interface IGenericRepository<E extends AbstractEntity> extends ListCrudRepository<E, Long> {
    // Métodos adicionais de consulta podem ser definidos aqui, se necessário.
}
