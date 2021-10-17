package org.bardales.jpa.servicio;

import jakarta.persistence.EntityManager;
import org.bardales.jpa.domain.Persona;

import java.util.List;

public interface PersonaService {

    void setEntityManager(EntityManager entityManager);

    List<Persona> listarPersonas();

    Persona encontrarPersonaPorId(Persona persona);

    Persona encontrarPersonaPorEmail(Persona persona);

    void registrarPersona(Persona persona);

    void modificarPersona(Persona persona);

    void eliminarPersona(Persona persona);

}
