package org.bardales.jpa.datos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.bardales.jpa.domain.Usuario;

import java.util.List;

public class UsuarioDAOImpl implements UsuarioDAO {

    private EntityManager entityManager;

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Usuario> findAllUsuarios() {
        return this.entityManager.createNamedQuery("Usuario.findAll", Usuario.class).getResultList();
    }

    @Override
    public Usuario findUsuarioById(Integer idUsuario) {
        return this.entityManager.find(Usuario.class, idUsuario);
    }

    @Override
    public void insertUsuario(Usuario usuario) {
        this.entityManager.persist(usuario);
    }

    @Override
    public void updateUsuario(Usuario usuario) {
        this.entityManager.merge(usuario);
    }

    @Override
    public void deleteUsuario(Usuario usuario) {
        this.entityManager.remove(this.entityManager.merge(usuario));
    }
}
