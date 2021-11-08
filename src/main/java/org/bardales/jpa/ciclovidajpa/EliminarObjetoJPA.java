package org.bardales.jpa.ciclovidajpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import lombok.extern.log4j.Log4j2;
import org.bardales.jpa.domain.Persona;

import java.util.Objects;

@Log4j2
public class EliminarObjetoJPA {

    private EntityManagerFactory entityManagerFactory;

    public EliminarObjetoJPA() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("PersonaPU");
    }

    public static void main(String... args) {
        EliminarObjetoJPA e = new EliminarObjetoJPA();

        EntityManager entityManager = null;
        EntityTransaction tx = null;
        EntityTransaction tx2 = null;
        try {
            entityManager = e.entityManagerFactory.createEntityManager();

            //Inicia la transaccion
            //Paso1. Iniciar una transaccion
            tx = entityManager.getTransaction();
            tx.begin();

            //Paso2. Ejecuta SQL de tipo select
            Persona personaUno = entityManager.find(Persona.class, 391);

            //Paso3. termina transacion 1
            tx.commit();

            //objeto en estado detached
            LOG.info("objeto encontrado: " + personaUno);


            //Paso4. Inicia transaccion 2
            tx2 = entityManager.getTransaction();
            tx2.begin();

            //Paso5. Ejecuta SQL que es un delete
            entityManager.remove(entityManager.merge(personaUno));

            //Paso6. termina transaccion 2
            tx2.commit();

            //objeto en estado detached ya eliminado
            LOG.info("objeto eliminado: " + personaUno);

        } catch(Exception ex) {
            if(Objects.nonNull(tx)) tx.rollback();
            if(Objects.nonNull(tx2)) tx2.rollback();
        } finally {
            if(Objects.nonNull(entityManager)) entityManager.close();
        }


    }

}
