package com.teste.daviugtsic.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.modelmapper.ModelMapper;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe que representa uma entidade de formulário.
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
@Entity
public class Formulario extends AbstractEntity {

    /**
     * O nome da pessoa que está submetendo o formulário.
     */
    private String nome;

    /**
     * O endereço de email da pessoa que está submetendo o formulário.
     * Deve ser único e estar em um formato de email válido.
     */
    @Email(message = "Email deve ser válido")
    @Column(unique = true)
    private String email;

    /**
     * O número de telefone da pessoa que está submetendo o formulário.
     * Deve seguir o padrão (00)00000-0000.
     */
    @Pattern(regexp = "\\(\\d{2}\\)\\d{5}-\\d{4}", message = "Telefone deve estar no formato (00)00000-0000")
    private String telefone;

    /**
     * O cargo desejado pela pessoa que está submetendo o formulário.
     */
    private String cargoDesejado;

    /**
     * A escolaridade da pessoa que está submetendo o formulário.
     */
    private String escolaridade;

    /**
     * Observações adicionais ou notas.
     */
    private String observacao;

    /**
     * O arquivo anexado da submissão do formulário.
     */
    private String arquivo;

    /**
     * Este método atualiza a entidade atual com valores de outra entidade.
     * Atualmente, não está implementado.
     *
     * @param e A entidade com valores atualizados.
     */
    @Override
    public void partialUpdate(AbstractEntity e) {}

    /**
     * Um DTO (Data Transfer Object) para os dados de requisição de Formulario.
     */
    @Data
    public static class DtoRequest {

        /**
         * O nome da pessoa que está submetendo o formulário.
         * Não deve estar em branco.
         */
        @NotBlank(message = "Nome em branco")
        private String nome;

        /**
         * O endereço de email da pessoa que está submetendo o formulário.
         * Deve estar em um formato de email válido e não estar em branco.
         */
        @Email(message = "Email deve ser válido")
        @NotBlank(message = "Email em branco")
        private String email;

        /**
         * O número de telefone da pessoa que está submetendo o formulário.
         * Deve seguir o padrão (00)00000-0000 e não estar em branco.
         */
        @Pattern(regexp = "\\(\\d{2}\\)\\d{5}-\\d{4}", message = "Telefone deve estar no formato (00)00000-0000")
        @NotBlank(message = "Telefone em branco")
        private String telefone;

        /**
         * O cargo desejado pela pessoa que está submetendo o formulário.
         * Não deve estar em branco.
         */
        @NotBlank(message = "Cargo desejado em branco")
        private String cargoDesejado;

        /**
         * A escolaridade da pessoa que está submetendo o formulário.
         * Não deve estar em branco.
         */
        @NotBlank(message = "Escolaridade em branco")
        private String escolaridade;

        /**
         * Observações adicionais ou notas.
         */
        private String observacao;

        /**
         * Converte o DtoRequest para uma entidade Formulario.
         *
         * @param dto    O DtoRequest a ser convertido.
         * @param mapper O model mapper a ser usado para a conversão.
         * @return A entidade Formulario convertida.
         */
        public static Formulario convertToEntity(DtoRequest dto, ModelMapper mapper) {
            return mapper.map(dto, Formulario.class);
        }
    }

    /**
     * Um DTO (Data Transfer Object) para os dados de resposta de Formulario.
     */
    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class DtoResponse extends RepresentationModel<DtoResponse> {

        private Long id;
        /**
         * O nome da pessoa que está submetendo o formulário.
         */
        private String nome;

        /**
         * O endereço de email da pessoa que está submetendo o formulário.
         */
        private String email;

        /**
         * O número de telefone da pessoa que está submetendo o formulário.
         */
        private String telefone;

        /**
         * O cargo desejado pela pessoa que está submetendo o formulário.
         */
        private String cargoDesejado;

        /**
         * A escolaridade da pessoa que está submetendo o formulário.
         */
        private String escolaridade;

        /**
         * Observações adicionais ou notas.
         */
        private String observacao;

        /**
         * O arquivo anexado da submissão do formulário.
         */
        private String arquivo;

        /**
         * Converte a entidade Formulario para um DtoResponse.
         *
         * @param f      A entidade Formulario a ser convertida.
         * @param mapper O model mapper a ser usado para a conversão.
         * @return O DtoResponse convertido.
         */
        public static DtoResponse convertToDto(Formulario f, ModelMapper mapper) {
            return mapper.map(f, DtoResponse.class);
        }
        public static List<DtoResponse> convertToDtoList(List<Formulario> formularios, ModelMapper modelMapper) {
            return formularios.stream()
                    .map(formulario -> convertToDto(formulario, modelMapper))
                    .collect(Collectors.toList());
        }
    }
}
