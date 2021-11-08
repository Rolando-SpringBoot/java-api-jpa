package org.bardales.jpa.servicio;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.bardales.jpa.datos.PersonaDAO;
import org.bardales.jpa.datos.PersonaDAOImpl;
import org.bardales.jpa.domain.Persona;
import org.bardales.jpa.exception.JPAException;

import java.util.ArrayList;
import java.util.List;

public class PersonaServiceImpl implements PersonaService {

    private PersonaDAO personaDao;
    private EntityManager entityManager;

    public PersonaServiceImpl() {
        this.personaDao = new PersonaDAOImpl();
    }

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.personaDao.setEntityManager(entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public List<Persona> listarPersonas() throws JPAException {
        EntityTransaction tx = this.entityManager.getTransaction();
        List<Persona> personaList = new ArrayList<>();
        try {
            tx.begin();
            personaList = this.personaDao.findAllPersonas();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw new JPAException(e.getMessage());
        }
        return personaList;
    }

    @Override
    public Persona encontrarPersonaPorId(Persona persona) throws JPAException {
        EntityTransaction tx = this.entityManager.getTransaction();
        Persona personaObtenida = new Persona();
        try {
            tx.begin();
            personaObtenida = this.personaDao.findPersonaById(persona);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw new JPAException(e.getMessage());
        }
        return personaObtenida;
    }

    @Override
    public Persona encontrarPersonaPorEmail(Persona persona) throws JPAException {
        EntityTransaction tx = this.entityManager.getTransaction();
        Persona personaObtenida = new Persona();
        try {
            tx.begin();
            personaObtenida = this.personaDao.findPersonaById(persona);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw new JPAException(e.getMessage());
        }
        return personaObtenida;
    }

    @Override
    public void registrarPersona(Persona persona) throws JPAException {
        EntityTransaction tx = this.entityManager.getTransaction();
        try {
            tx.begin();
            this.personaDao.insertPersona(persona);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw new JPAException(e.getMessage());
        }
    }

    @Override
    public void modificarPersona(Persona persona) throws JPAException {
        EntityTransaction tx = this.entityManager.getTransaction();
        try {
            tx.begin();
            this.personaDao.updatePersona(persona);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw new JPAException(e.getMessage());
        }
    }

    @Override
    public void eliminarPersona(Persona persona) throws JPAException {
        EntityTransaction tx = this.entityManager.getTransaction();
        try {
            tx.begin();
            this.personaDao.deletePersona(persona);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw new JPAException(e.getMessage());
        }
    }
}
