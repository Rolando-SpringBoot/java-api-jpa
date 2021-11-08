package org.bardales.jpa.ciclovidajpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import lombok.extern.log4j.Log4j2;
import org.bardales.jpa.domain.Persona;

import java.util.Objects;

@Log4j2
public class ActualizarObjetoJPA {


    private EntityManagerFactory entityManagerFactory;

    public ActualizarObjetoJPA() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("PersonaPU");
    }

    public static void main(String... args) {
        ActualizarObjetoJPA a = new ActualizarObjetoJPA();

        EntityManager entityManager = null;
        EntityTransaction tx = null;
        EntityTransaction tx2 = null;
        try {
            entityManager = a.entityManagerFactory.createEntityManager();

            //Inicia la transaccion
            //Paso1. Iniciar una transaccion
            tx = entityManager.getTransaction();
            tx.begin();

            //Paso2. Ejecutar SQL de tipo select
            //el id de la persona debe existir en la base de datos
            Persona personaUno = entityManager.find(Persona.class, 97);

            //Paso3. termina la transaccion 1
            tx.commit();

            //objeto en estado de detach
            LOG.info("Objeto recuperado: " + personaUno);

            //Paso4. setValue (nuevo valor)
            personaUno.setApellido("Juarez");

            //Pao5. Inicia transaccion 2
            tx2 = entityManager.getTransaction();
            tx2.begin();

            //Paso6. Modificamos el objeto
            entityManager.merge(personaUno);

            //Paso7. Termina la transaccion
            tx2.commit();

            //objeto en estado de detached ya modificado
            LOG.info("Objeto recuperado: " + personaUno);
        } catch (Exception e) {
            if (Objects.nonNull(tx)) tx.rollback();
            if (Objects.nonNull(tx2)) tx2.rollback();
        } finally {
            if (Objects.nonNull(entityManager)) entityManager.close();
        }


    }

}
