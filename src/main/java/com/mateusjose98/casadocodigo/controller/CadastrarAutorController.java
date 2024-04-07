package com.mateusjose98.casadocodigo.controller;

import com.mateusjose98.casadocodigo.entidades.Autor;
import com.mateusjose98.casadocodigo.controller.request.NovoAutorRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/autores")
@Slf4j
public class CadastrarAutorController {


    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AutorValidator autorValidator;

    @InitBinder
    public void init(WebDataBinder binder) {
        binder.addValidators(autorValidator);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Void> executar(@Valid @RequestBody NovoAutorRequest novoAutorRequest) {
        Autor autor = novoAutorRequest.toModel();
        entityManager.persist(autor);
        log.info("Autor cadastrado com sucesso: {}", autor);
        return ResponseEntity.ok().build();
    }

}
