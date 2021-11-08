package org.bardales.jpa.ciclovidajpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import lombok.extern.log4j.Log4j2;
import org.bardales.jpa.domain.Persona;

import java.util.Objects;

@Log4j2
public class PersistirObjetoJPA {

    private EntityManagerFactory entityManagerFactory;

    public PersistirObjetoJPA() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("PersonaPU");
    }

    public static void main(String... args) {
        PersistirObjetoJPA p = new PersistirObjetoJPA();

        EntityManager entityManager = null;
        EntityTransaction tx = null;
        try {
            entityManager = p.entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();

            //Inicia la transaccion

            //Paso1. Crea nuevo objeto
            //Objeto en estado transitivo
            Persona personaUno = Persona.of("Pedro", "Luna", "pluna@mail.com", "131135566");

            //Paso2. Inicia transaccion
            tx.begin();

            //Paso3. Ejecutamos el SQL
            entityManager.persist(personaUno);

            //Paso4. commit/rollback
            tx.commit();

            //Objeto en estado detach
            LOG.info("Objeto persistido - estado detached: " + personaUno);

            //Cerramos el objeto entity manager
        } catch (Exception e) {
            if (Objects.nonNull(tx)) tx.rollback();
        } finally {
            if (Objects.nonNull(entityManager)) entityManager.close();
        }


    }


}
