package org.bardales.jpa.ciclovidajpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import lombok.extern.log4j.Log4j2;
import org.bardales.jpa.domain.Persona;

import java.util.Objects;

@Log4j2
public class ActualizarObjetoSesionLarga {

    private EntityManagerFactory entityManagerFactory;

    public ActualizarObjetoSesionLarga() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("PersonaPU");
    }

    public static void main(String... args) {
        ActualizarObjetoSesionLarga a = new ActualizarObjetoSesionLarga();

        EntityManager entityManager = null;
        EntityTransaction tx = null;
        try {
            entityManager = a.entityManagerFactory.createEntityManager();

            //Inicia la transaccion
            //Paso1. Iniciar una transaccion
            tx = entityManager.getTransaction();
            tx.begin();

            //Paso2. ejecutamos SQL de tipo select
            Persona personaUno = entityManager.find(Persona.class, 97);

            LOG.info("Objecto encontrado: " + personaUno);

            //Paso3. setValue(nuevo valor)
            personaUno.setEmail("p.juarez@mail.com");

            //Paso4. Termina la transaccion
            tx.commit();

            //objeto en estado de detached
            LOG.info("objeto modificado: " + personaUno);

        } catch(Exception e) {
            if(Objects.nonNull(tx)) tx.rollback();
        } finally {
            if(Objects.nonNull(entityManager)) entityManager.close();
        }

    }

}
