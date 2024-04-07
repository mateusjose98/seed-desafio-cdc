package com.mateusjose98.casadocodigo.controller.request;

import com.mateusjose98.casadocodigo.entidades.Autor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.time.Instant;

public record NovoAutorRequest(@NotBlank String nome,
                               @Email @NotBlank String email,
                               @NotBlank @Length(min = 5, max = 400) String descricao) {
    public Autor toModel() {
       return new Autor(nome, email, descricao);
    }
}
