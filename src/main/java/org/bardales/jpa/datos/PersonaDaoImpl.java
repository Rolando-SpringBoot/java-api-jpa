package org.bardales.jpa.datos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.bardales.jpa.domain.Persona;

import java.util.List;

public class PersonaDaoImpl implements PersonaDao {

    private EntityManager entityManager;

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Persona> findAllPersonas() {
        return this.entityManager.createNamedQuery("Persona.findAll", Persona.class).getResultList();
    }

    @Override
    public Persona findPersonaById(Persona persona) {
        return this.entityManager.find(Persona.class, persona.getIdPersona());
    }

    @Override
    public Persona findPersonaByEmail(Persona persona) {
        TypedQuery<Persona> query = entityManager.createQuery("SELECT p FROM persona p WHERE p.email = :email", Persona.class);
        query.setParameter("email", persona.getEmail());
        return query.getSingleResult();
    }

    @Override
    public void insertPersona(Persona persona) {
        this.entityManager.persist(persona);
    }

    @Override
    public void updatePersona(Persona persona) {
        this.entityManager.merge(persona);
    }

    @Override
    public void deletePersona(Persona persona) {
        this.entityManager.remove(this.entityManager.merge(persona));
    }
}
