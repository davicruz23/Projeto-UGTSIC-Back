package com.teste.daviugtsic;

import com.teste.daviugtsic.domain.Formulario;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FormularioTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testFormularioValid() {
        Formulario formulario = Formulario.builder()
                .nome("John Doe")
                .email("john.doe@example.com")
                .telefone("(12)34567-8901")
                .cargoDesejado("Developer")
                .escolaridade("Bachelor")
                .observacao("No observations")
                .arquivo("resume.pdf")
                .build();

        Set<ConstraintViolation<Formulario>> violations = validator.validate(formulario);
        assertEquals(0, violations.size(), "Deveria ter 0 violações");
    }

    @Test
    public void testFormularioInvalidEmail() {
        Formulario formulario = Formulario.builder()
                .nome("John Doe")
                .email("invalid-email")
                .telefone("(12)34567-8901")
                .cargoDesejado("Developer")
                .escolaridade("Bachelor")
                .observacao("No observations")
                .arquivo("resume.pdf")
                .build();

        Set<ConstraintViolation<Formulario>> violations = validator.validate(formulario);
        assertEquals(1, violations.size(), "Deveria ter 1 violação");
        ConstraintViolation<Formulario> violation = violations.iterator().next();
        assertEquals("Email deve ser válido", violation.getMessage());
    }

    @Test
    public void testFormularioInvalidTelefone() {
        Formulario formulario = Formulario.builder()
                .nome("John Doe")
                .email("john.doe@example.com")
                .telefone("1234567890")
                .cargoDesejado("Developer")
                .escolaridade("Bachelor")
                .observacao("No observations")
                .arquivo("resume.pdf")
                .build();

        Set<ConstraintViolation<Formulario>> violations = validator.validate(formulario);
        assertEquals(1, violations.size(), "Deveria ter 1 violação");
        ConstraintViolation<Formulario> violation = violations.iterator().next();
        assertEquals("Telefone deve estar no formato (00)00000-0000", violation.getMessage());
    }

    @Test
    public void testFormularioPartialUpdate() {
        Formulario formulario = Formulario.builder()
                .nome("John Doe")
                .email("john.doe@example.com")
                .telefone("(12)34567-8901")
                .cargoDesejado("Developer")
                .escolaridade("Bachelor")
                .observacao("No observations")
                .arquivo("resume.pdf")
                .build();

        formulario.partialUpdate(new Formulario());

        assertEquals("John Doe", formulario.getNome());
        assertEquals("john.doe@example.com", formulario.getEmail());
        assertEquals("(12)34567-8901", formulario.getTelefone());
        assertEquals("Developer", formulario.getCargoDesejado());
        assertEquals("Bachelor", formulario.getEscolaridade());
        assertEquals("No observations", formulario.getObservacao());
        assertEquals("resume.pdf", formulario.getArquivo());
    }
}
