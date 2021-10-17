package org.bardales.jpa.servicio;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.bardales.jpa.datos.PersonaDao;
import org.bardales.jpa.datos.PersonaDaoImpl;
import org.bardales.jpa.domain.Persona;

import java.util.ArrayList;
import java.util.List;

public class PersonaServiceImpl implements PersonaService {

    private PersonaDao personaDao;
    private EntityManager entityManager;

    public PersonaServiceImpl() {
        this.personaDao = new PersonaDaoImpl();
        this.personaDao.setEntityManager(entityManager);
    }

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Persona> listarPersonas() {
        EntityTransaction tx = this.entityManager.getTransaction();
        List<Persona> personaList = new ArrayList<>();
        try {
            tx.begin();
            personaList = this.personaDao.findAllPersonas();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
        return personaList;
    }

    @Override
    public Persona encontrarPersonaPorId(Persona persona) {
        EntityTransaction tx = this.entityManager.getTransaction();
        Persona personaObtenida = new Persona();
        try {
            tx.begin();
            personaObtenida = this.personaDao.findPersonaById(persona);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
        return personaObtenida;
    }

    @Override
    public Persona encontrarPersonaPorEmail(Persona persona) {
        EntityTransaction tx = this.entityManager.getTransaction();
        Persona personaObtenida = new Persona();
        try {
            tx.begin();
            personaObtenida = this.personaDao.findPersonaById(persona);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
        return personaObtenida;
    }

    @Override
    public void registrarPersona(Persona persona) {
        EntityTransaction tx = this.entityManager.getTransaction();
        try {
            tx.begin();
            this.personaDao.insertPersona(persona);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
    }

    @Override
    public void modificarPersona(Persona persona) {
        EntityTransaction tx = this.entityManager.getTransaction();
        try {
            tx.begin();
            this.personaDao.updatePersona(persona);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
    }

    @Override
    public void eliminarPersona(Persona persona) {
        EntityTransaction tx = this.entityManager.getTransaction();
        try {
            tx.begin();
            this.personaDao.deletePersona(persona);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
    }
}
