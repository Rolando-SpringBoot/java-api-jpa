package org.bardales.jpa.servicio;

import jakarta.persistence.EntityManager;
import org.bardales.jpa.domain.Usuario;
import org.bardales.jpa.exception.JPAException;

import java.util.List;

public interface UsuarioService {

    void setEntityManager(EntityManager entityManager);

    List<Usuario> listarUsuarios() throws JPAException;

    Usuario encontrarUsuarioPorId(Integer idUsuario) throws JPAException;

    void registrarUsuario(Usuario usuario) throws JPAException;

    void modificarUsuario(Usuario usuario) throws JPAException;

    void eliminarUsuario(Usuario usuario) throws JPAException;

}
