package org.bardales.jpa.datos;

import jakarta.persistence.EntityManager;
import org.bardales.jpa.domain.Persona;

import java.util.List;

public interface PersonaDAO {

    void setEntityManager(EntityManager entityManager);

    List<Persona> findAllPersonas();

    Persona findPersonaById(Integer idPersona);

    Persona findPersonaByEmail(String email);

    void insertPersona(Persona persona);

    void updatePersona(Persona persona);

    void deletePersona(Persona persona);

}