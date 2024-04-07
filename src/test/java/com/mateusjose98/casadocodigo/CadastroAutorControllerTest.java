package com.mateusjose98.casadocodigo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mateusjose98.casadocodigo.controller.AutorValidator;
import com.mateusjose98.casadocodigo.controller.CadastrarAutorController;
import com.mateusjose98.casadocodigo.controller.request.NovoAutorRequest;
import com.mateusjose98.casadocodigo.entidades.Autor;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.match.ContentRequestMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.net.URI;
import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class CadastroAutorControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    int randomServerPort;

    @Test
    void cadastrarAutorComDadosValidos() throws Exception {
        final String baseUrl = "http://localhost:"+randomServerPort+"/autores";
        URI uri = new URI(baseUrl);
        NovoAutorRequest autor = new NovoAutorRequest("Mateus", "email@email.com", "descricao");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<NovoAutorRequest> request = new HttpEntity<>(autor, headers);
        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);
        Assertions.assertTrue( result.getStatusCode().is2xxSuccessful());
    }

    @Test
    @DisplayName("Deve falhar ao cadastrar autor com dados inválidos")
    void cadastrarDeveFalhar() throws Exception{
        final String baseUrl = "http://localhost:"+randomServerPort+"/autores";
        URI uri = new URI(baseUrl);

        NovoAutorRequest primeiroAutor = new NovoAutorRequest("Mateus", "email@email.com", "descricao");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<NovoAutorRequest> request = new HttpEntity<>(primeiroAutor, headers);
        ResponseEntity<String> result1 = this.restTemplate.postForEntity(uri, request, String.class);
        Assertions.assertTrue( result1.getStatusCode().is2xxSuccessful());

        NovoAutorRequest autor2 = new NovoAutorRequest("Mateus", "", "");
        HttpHeaders headers2 = new HttpHeaders();
        HttpEntity<NovoAutorRequest> request2 = new HttpEntity<>(autor2, headers2);
        ResponseEntity<ArrayList> result = this.restTemplate.postForEntity(uri, request2, ArrayList.class);
        Assertions.assertTrue( result.getStatusCode().is4xxClientError());
        System.out.println(result.getBody());
        Assertions.assertIterableEquals(result.getBody(), new ArrayList<String>(){{
            add("Campo email não deve estar em branco");
            add("Campo descricao o comprimento deve ser entre 5 e 400");
            add("Campo descricao não deve estar em branco");
        }});


    }


}
