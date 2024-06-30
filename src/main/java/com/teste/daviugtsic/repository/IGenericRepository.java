package com.teste.daviugtsic.repository;

import com.teste.daviugtsic.domain.AbstractEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface IGenericRepository<E extends AbstractEntity> extends ListCrudRepository<E, Long> {
}
