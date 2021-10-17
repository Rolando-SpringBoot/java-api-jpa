package org.bardales.jpa.test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import lombok.extern.log4j.Log4j2;
import org.bardales.jpa.domain.Persona;

@Log4j2
public class ClienteEntidadPersona {

    public static void main(String... args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PersonaPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        //Inicia la transaccion
        tx.begin();

        //No se debe especificar el ID de la base de datos
        Persona persona = Persona.of("Martins","mgutierrez@mail.com", "889911999");
        persona.setApellido("Gutierrez");

        log.info("Objeto a persistir: {}", persona);

        //Persistir el objeto
        em.persist(persona);
        em.persist(Persona.of("susan","smendez@mail.com","889911999"));
        em.persist(Persona.of("pedro","psuarez@mail.com","889911999"));
        em.persist(Persona.of("matias","mzabr@mail.com","889911999"));
        em.persist(Persona.of("sebas","szabr@mail.com","889911999"));

        //terminamos la transaccion
        tx.commit();

        log.info("Objeto persistido" + persona);
        em.close();
        emf.close();
    }

}
