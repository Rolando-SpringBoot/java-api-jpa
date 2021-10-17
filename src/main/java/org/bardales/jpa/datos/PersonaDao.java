package org.bardales.jpa.datos;

import jakarta.persistence.EntityManager;
import org.bardales.jpa.domain.Persona;

import java.util.List;

public interface PersonaDao {

    void setEntityManager(EntityManager entityManager);

    List<Persona> findAllPersonas();

    Persona findPersonaById(Persona persona);

    Persona findPersonaByEmail(Persona persona);

    void insertPersona(Persona persona);

    void updatePersona(Persona persona);

    void deletePersona(Persona persona);

}