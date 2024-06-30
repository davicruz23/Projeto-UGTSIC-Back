package com.teste.daviugtsic.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.modelmapper.ModelMapper;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
@Entity
public class Formulario extends AbstractEntity {

    private String nome;
    @Email(message = "Email deve ser válido")
    @Column(unique = true)
    private String email;
    @Pattern(regexp = "\\d{11}", message = "Telefone deve conter apenas 11 dígitos")
    @Column(unique = true)
    private String telefone;

    private String cargoDesejado;
    private String escolaridade;
    private String observacao;
    private String arquivo;

    @Override
    public void partialUpdate(AbstractEntity e) {}

    @Data
    public static class DtoRequest {
        @NotBlank(message = "Nome em branco")
        private String nome;

        @Email(message = "Email deve ser válido")
        @NotBlank(message = "Email em branco")
        private String email;

        @Pattern(regexp = "\\d{11}", message = "Telefone deve conter apenas 11 dígitos")
        @NotBlank(message = "Telefone em branco")
        private String telefone;

        @NotBlank(message = "Cargo desejado em branco")
        private String cargoDesejado;

        @NotBlank(message = "Escolaridade em branco")
        private String escolaridade;

        private String observacao;

        public static Formulario convertToEntity(DtoRequest dto, ModelMapper mapper) {
            return mapper.map(dto, Formulario.class);
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class DtoResponse extends RepresentationModel<DtoResponse> {
        private String nome;
        private String email;
        private String telefone;
        private String cargoDesejado;
        private String escolaridade;
        private String observacao;
        private String arquivo;

        public static DtoResponse convertToDto(Formulario f, ModelMapper mapper) {
            return mapper.map(f, DtoResponse.class);
        }
    }
}
