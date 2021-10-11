package org.bardales.jpa.test;

import jakarta.persistence.*;
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
        Persona persona = new Persona("Lucas", "Cipriani", "mgutierrez@mail.com", "889911999");
        log.debug("Objeto a persistir: {}", persona);
        //Persistir el objeto
        em.persist(persona);
        //terminamos la transaccion
        tx.commit();
        log.debug("Objeto persistido" + persona);
        em.close();
        emf.close();
    }

}
