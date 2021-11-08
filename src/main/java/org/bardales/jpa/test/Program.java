package org.bardales.jpa.test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import lombok.extern.log4j.Log4j2;
import org.bardales.jpa.domain.Persona;
import org.bardales.jpa.exception.JPAException;
import org.bardales.jpa.servicio.PersonaService;
import org.bardales.jpa.servicio.PersonaServiceImpl;

import javax.swing.*;
import java.util.Locale;
import java.util.Objects;

@Log4j2
public class Program {

    private EntityManagerFactory entityManagerFactory;

    public Program() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("PersonaPU");
    }

    public static void main(String... args) {
        Program p = new Program();
        PersonaService personaService = new PersonaServiceImpl();

        EntityManager entityManager = null;
        EntityTransaction tx = null;
        try {
            entityManager = p.entityManagerFactory.createEntityManager();

            Persona personaUno = entityManager.find(Persona.class, 97);

            LOG.info("persona: " + personaUno);
        } catch(Exception e) {
            System.out.println(String.format(Locale.US, "Error: %s",e.getMessage()));
            if(Objects.nonNull(tx)) tx.rollback();
        } finally {
            if(Objects.nonNull(entityManager)) entityManager.close();
        }

    }

}
