package com.mateusjose98.casadocodigo.entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "tb_autor")
@Getter
@Setter
@ToString
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String email;
    private String descricao;

    @Column(nullable = false)
    private Instant dataCriacao;

    public Autor() {
        this.dataCriacao = Instant.now();
    }

    public Autor(String nome, String email, String descricao) {
        this();
        this.nome = Objects.requireNonNull(nome, "O nome do autor não pode ser nulo");
        this.email = Objects.requireNonNull(email, "O email do autor não pode ser nulo");
        this.descricao = Objects.requireNonNull(descricao, "A descrição do autor não pode ser nula");
    }
}
