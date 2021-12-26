package org.bardales.jpa.test.asocioacionlazyeager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import lombok.extern.log4j.Log4j2;
import org.bardales.jpa.domain.Persona;

import java.util.List;
import java.util.Objects;

@Log4j2
public class ClienteAsociacionLazyEagerJPA {

    private EntityManagerFactory entityManagerFactory;

    public ClienteAsociacionLazyEagerJPA() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("PersonaPU");
    }

    public static void main(String... args) {
        ClienteAsociacionLazyEagerJPA c = new ClienteAsociacionLazyEagerJPA();

        EntityManager entityManager = null;
        EntityTransaction tx = null;
        try {
            entityManager = c.entityManagerFactory.createEntityManager();

            List<Persona> personas = entityManager.createNamedQuery("Persona.findAll", Persona.class).getResultList();

            //Imprimir la lista de personas
            personas.stream().forEach(persona -> {
                LOG.info("Persona: {}", persona);
                //recuperamos los usuarios de cada persona
                persona.getUsuarioList().stream().forEach(usuario -> LOG.info("Usuario: {}", usuario));
            });

        } catch(Exception e) {

        } finally {
            if(Objects.nonNull(entityManager)) entityManager.close();
        }

    }

}
