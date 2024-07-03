package com.teste.daviugtsic.repository;

import com.teste.daviugtsic.domain.Formulario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface que define o repositório para a entidade Formulario.
 * Estende JpaRepository para fornecer métodos CRUD e de paginação.
 */
@Repository
public interface FormularioRepository extends JpaRepository<Formulario, Long> {

    boolean existsByEmail(String email);

}
