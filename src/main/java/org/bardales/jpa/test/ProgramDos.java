package org.bardales.jpa.test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import lombok.extern.log4j.Log4j2;
import org.bardales.jpa.domain.MascotaPersona;

@Log4j2
public class ProgramDos {

    private EntityManagerFactory entityManagerFactory;

    public ProgramDos() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("PersonaPU");
    }

    public static void main(String... args) {
        ProgramDos p = new ProgramDos();

        EntityManager entityManager = null;
        EntityTransaction tx = null;
        try {
            entityManager = p.entityManagerFactory.createEntityManager();
            //tx = entityManager.getTransaction();
            //tx.begin();

            /*MascotaPersonaId mascotaPersonaId = MascotaPersonaId.of(MascotaId.of(2, 2), 6);
            MascotaPersona mascotaPersona = entityManager
                    .find(MascotaPersona.class, mascotaPersonaId);
            LOG.info("mascotaPersona : {}", mascotaPersona);
            */

            //tx.commit();

            TypedQuery<MascotaPersona> mascotaPersonaTypedQuery = entityManager.createNamedQuery(
                    "MascotaPersona.findByPrecio",
                    MascotaPersona.class);
            mascotaPersonaTypedQuery.setParameter("precio", BigDecimal.valueOf(600.5D));

            List<MascotaPersona> mascotaPersonaList = mascotaPersonaTypedQuery.getResultList();
            mascotaPersonaList.forEach(System.out::println);


        } catch (Exception e) {
            if (Objects.nonNull(tx)) {
                tx.rollback();
            }
            LOG.error(e.getMessage());
            e.printStackTrace(System.out);
        } finally {
            if (Objects.nonNull(entityManager)) {
                entityManager.close();
            }
        }

    }

}