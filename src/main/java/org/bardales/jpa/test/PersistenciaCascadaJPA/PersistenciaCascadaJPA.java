package org.bardales.jpa.test.PersistenciaCascadaJPA;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import lombok.extern.log4j.Log4j2;
import org.bardales.jpa.domain.Persona;
import org.bardales.jpa.domain.Usuario;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

@Log4j2
public class PersistenciaCascadaJPA {

    private EntityManagerFactory entityManagerFactory;

    public PersistenciaCascadaJPA() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("PersonaPU");
    }

    public static void main(String... args) {
        PersistenciaCascadaJPA p = new PersistenciaCascadaJPA();

        EntityManager entityManager = null;
        EntityTransaction tx = null;
        try {
            entityManager = p.entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();

            tx.begin();

            //Paso1. Crea nuevo objeto
            //objeto en estado transitivo
            Persona personaUno = Persona.of("Hugo", "Hernandez", "hhernandez@mail.com", "567745636");

            //Crear objeto usuario (tiene dependenciacon el objeto personaUno)
            Usuario usuarioUno = Usuario.of("hhernandez", "123");
            usuarioUno.setPersona(personaUno);

            personaUno.setUsuarioList(new ArrayList<>(Arrays.asList(new Usuario[]{usuarioUno})));

            //Paso3 persistimos el objeto persona junto los usarios asociados a este
            entityManager.persist(personaUno);

            //Paos4. commit de la transaccion
            tx.commit();

            //objetos en estado de detached
            LOG.info("objeto persistido persona: {}", personaUno);
            LOG.info("objeto persistido usuario: {}", usuarioUno);

        } catch(Exception e) {
            if(Objects.nonNull(tx)) tx.rollback();
        } finally {
            if(Objects.nonNull(entityManager)) entityManager.close();
        }

    }

}
