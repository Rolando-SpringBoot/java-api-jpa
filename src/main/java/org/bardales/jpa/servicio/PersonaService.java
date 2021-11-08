package org.bardales.jpa.servicio;

import jakarta.persistence.EntityManager;
import org.bardales.jpa.domain.Persona;
import org.bardales.jpa.exception.JPAException;

import java.util.List;

public interface PersonaService {

    void setEntityManager(EntityManager entityManager);

    List<Persona> listarPersonas() throws JPAException;

    Persona encontrarPersonaPorId(Persona persona) throws JPAException;

    Persona encontrarPersonaPorEmail(Persona persona) throws JPAException;

    void registrarPersona(Persona persona) throws JPAException;

    void modificarPersona(Persona persona) throws JPAException;

    void eliminarPersona(Persona persona) throws JPAException;

}
