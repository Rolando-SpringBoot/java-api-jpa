package org.bardales.jpa.ciclovidajpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import lombok.extern.log4j.Log4j2;
import org.bardales.jpa.domain.Persona;

import java.util.Objects;

@Log4j2
public class EncontrarObjetoJPA {


    private EntityManagerFactory entityManagerFactory;

    public EncontrarObjetoJPA() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("PersonaPU");
    }

    public static void main(String... args) {
        EncontrarObjetoJPA e = new EncontrarObjetoJPA();

        EntityManager entityManager = null;
        EntityTransaction tx = null;
        try {
            entityManager = e.entityManagerFactory.createEntityManager();

            //Inicia la transaccion

            //Paso1. Iniciar una transaccion
            tx = entityManager.getTransaction();
            tx.begin();

            //Paso2. Ejecutar SQL de tipo select
            Persona personaUno = entityManager.find(Persona.class, 97);

            //Paso3. termina la transaccion
            tx.commit();

            //Objeto en estado de detached
            LOG.info("Objeto recuperado: " + personaUno);
        } catch (Exception ex) {
            if (Objects.nonNull(tx)) tx.rollback();
        } finally {
            if (Objects.nonNull(entityManager)) entityManager.close();
        }


    }

}
