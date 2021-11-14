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
        List<Persona> personaList;
        try {
            personaList = this.personaDao.findAllPersonas();
        } catch (Exception e) {
            throw new JPAException(e.getMessage());
        }
        return personaList;
    }

    @Override
    public Persona encontrarPersonaPorId(Integer idPersona) throws JPAException {
        Persona personaObtenida;
        try {
            personaObtenida = this.personaDao.findPersonaById(idPersona);
        } catch (Exception e) {
            throw new JPAException(e.getMessage());
        }
        return personaObtenida;
    }

    @Override
    public Persona encontrarPersonaPorEmail(String email) throws JPAException {
        Persona personaObtenida;
        try {
            personaObtenida = this.personaDao.findPersonaByEmail(email);
        } catch (Exception e) {
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
