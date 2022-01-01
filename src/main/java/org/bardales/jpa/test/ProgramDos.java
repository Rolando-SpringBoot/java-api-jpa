package org.bardales.jpa.test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.util.Objects;
import lombok.extern.log4j.Log4j2;
import org.bardales.jpa.domain.MascotaId;
import org.bardales.jpa.domain.MascotaPersona;
import org.bardales.jpa.domain.MascotaPersonaId;

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
            tx = entityManager.getTransaction();
            tx.begin();

            MascotaPersonaId mascotaPersonaId = MascotaPersonaId.of(MascotaId.of(2, 2), 6);
            MascotaPersona mascotaPersona = entityManager
                    .find(MascotaPersona.class, mascotaPersonaId);
            LOG.info("mascotaPersona : {}", mascotaPersona);

            tx.commit();

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