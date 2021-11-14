package org.bardales.jpa.test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import lombok.extern.log4j.Log4j2;
import org.bardales.jpa.domain.Persona;
import org.bardales.jpa.domain.Usuario;
import org.bardales.jpa.exception.JPAException;
import org.bardales.jpa.servicio.PersonaService;
import org.bardales.jpa.servicio.PersonaServiceImpl;
import org.bardales.jpa.servicio.UsuarioService;
import org.bardales.jpa.servicio.UsuarioServiceImpl;

import javax.swing.*;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Log4j2
public class Program {

    private EntityManagerFactory entityManagerFactory;

    public Program() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("PersonaPU");
    }

    public static void main(String... args) {
        Program p = new Program();
        PersonaService personaService = new PersonaServiceImpl();
        UsuarioService usuarioService = new UsuarioServiceImpl();

        EntityManager entityManagerPersona = null;
        EntityManager entityManagerUsuario = null;
        EntityTransaction tx = null;
        try {
            entityManagerPersona = p.entityManagerFactory.createEntityManager();
            entityManagerUsuario = p.entityManagerFactory.createEntityManager();
            personaService.setEntityManager(entityManagerPersona);
            usuarioService.setEntityManager(entityManagerUsuario);

            //buscar persona con id 97
            Persona personaBuscado = personaService.encontrarPersonaPorId(97);
            LOG.info("personaBuscado : {}", personaBuscado);

            //registrar usuario
            Usuario usuario = Usuario.of("mateo123", "12345678-a");
            usuario.setPersona(personaBuscado);
            usuarioService.registrarUsuario(usuario);
            LOG.info("usuario creado : {}", usuario);

            //listar todas los usuarios
            List<Usuario> usuarioList = usuarioService.listarUsuarios();
            usuarioList.forEach(u -> LOG.info("usuario : {}", u));

            //eliminar persona
            personaService.eliminarPersona(personaBuscado);

            //listar personas
            List<Persona> personaList = personaService.listarPersonas();
            personaList.forEach(per -> LOG.info("persona : {}", per));

            //listar todas los usuarios
            usuarioList = usuarioService.listarUsuarios();
            usuarioList.forEach(u -> LOG.info("usuario : {}", u));

        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace(System.out);
            if (Objects.nonNull(tx)) tx.rollback();
        } finally {
            if (Objects.nonNull(entityManagerPersona)) entityManagerPersona.close();
            if (Objects.nonNull(entityManagerUsuario)) entityManagerUsuario.close();
        }

    }

}
