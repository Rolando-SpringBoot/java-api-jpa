package org.bardales.jpa.datos;

import jakarta.persistence.EntityManager;
import org.bardales.jpa.domain.Usuario;

import java.util.List;

public interface UsuarioDAO {

    void setEntityManager(EntityManager entityManager);

    List<Usuario> findAllUsuarios();

    Usuario findUsuarioById(Integer idUsaurio);

    void insertUsuario(Usuario usuario);

    void updateUsuario(Usuario usuario);

    void deleteUsuario(Usuario usuario);

}
