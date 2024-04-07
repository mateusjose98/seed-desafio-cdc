package com.mateusjose98.casadocodigo.controller;

import com.mateusjose98.casadocodigo.controller.request.NovoAutorRequest;
import com.mateusjose98.casadocodigo.entidades.Autor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AutorValidator implements Validator {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean supports(Class<?> clazz) {
        return NovoAutorRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        String email = ((NovoAutorRequest) target).email();
        var resultList =
                entityManager
                        .createQuery("SELECT a.id FROM Autor a WHERE a.email = :email", Integer.class)
                        .setParameter("email", email)
                        .getResultList();

        if (!resultList.isEmpty()) {
            errors.rejectValue("email", null, String.format("(%s) já está sendo utilizado por outro autor.", email));
        }
    }
}
