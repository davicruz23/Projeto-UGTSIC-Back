package com.teste.daviugtsic.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Classe abstrata que define campos comuns para entidades persistentes.
 * Serve como uma superclasse mapeada para outras entidades.
 */
@MappedSuperclass
@Getter
@Setter
@RequiredArgsConstructor
public abstract class AbstractEntity {

    /**
     * Identificador único da entidade.
     * Gerado automaticamente pelo banco de dados.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Data e hora em que a entidade foi excluída.
     */
    @JsonIgnore
    private LocalDateTime deletedAt;

    /**
     * Data e hora em que a entidade foi criada.
     * Preenchido automaticamente quando a entidade é salva pela primeira vez.
     */
    @CreationTimestamp
    private LocalDateTime createdAt;

    /**
     * Data e hora em que a entidade foi atualizada pela última vez.
     * Preenchido automaticamente a cada atualização da entidade.
     */
    @JsonIgnore
    @UpdateTimestamp
    private LocalDateTime updateAt;

    /**
     * Endereço IP do usuário ou sistema que criou/atualizou a entidade.
     */
    private String ipAddress; // Novo campo para armazenar o IP

    /**
     * Método para verificar a igualdade entre objetos.
     * @param o Objeto a ser comparado.
     * @return true se os objetos são iguais, false caso contrário.
     */
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AbstractEntity that = (AbstractEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    /**
     * Gera o código hash da entidade com base na classe.
     * @return O código hash da entidade.
     */
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    /**
     * Método abstrato para atualizar parcialmente a entidade.
     * Ignorado durante a serialização JSON.
     * @param e Entidade com os valores a serem atualizados.
     */
    @JsonIgnore
    public abstract void partialUpdate(AbstractEntity e);
}
