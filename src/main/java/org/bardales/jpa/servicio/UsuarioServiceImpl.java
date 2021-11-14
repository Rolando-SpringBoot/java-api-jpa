package org.bardales.jpa.servicio;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.bardales.jpa.datos.UsuarioDAO;
import org.bardales.jpa.datos.UsuarioDAOImpl;
import org.bardales.jpa.domain.Usuario;
import org.bardales.jpa.exception.JPAException;
import org.eclipse.persistence.exceptions.JPQLException;

import java.util.ArrayList;
import java.util.List;

public class UsuarioServiceImpl implements UsuarioService {

    private UsuarioDAO usuarioDAO;
    private EntityManager entityManager;

    public UsuarioServiceImpl() {
        this.usuarioDAO = new UsuarioDAOImpl();
    }

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.usuarioDAO.setEntityManager(entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public List<Usuario> listarUsuarios() throws JPAException {
        List<Usuario> usuarioList;
        try {
            usuarioList = this.usuarioDAO.findAllUsuarios();
        } catch (Exception e) {
            throw new JPAException(e.getMessage());
        }
        return usuarioList;
    }

    @Override
    public Usuario encontrarUsuarioPorId(Integer idUsuario) throws JPAException {
        Usuario usuarioBuscado;
        try {
            usuarioBuscado = this.usuarioDAO.findUsuarioById(idUsuario);
        } catch (Exception e) {
            throw new JPAException(e.getMessage());
        }
        return usuarioBuscado;
    }

    @Override
    public void registrarUsuario(Usuario usuario) throws JPAException {
        EntityTransaction tx = null;
        try {
            tx = this.entityManager.getTransaction();
            tx.begin();
            this.usuarioDAO.insertUsuario(usuario);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw new JPAException(e.getMessage());
        }
    }

    @Override
    public void modificarUsuario(Usuario usuario) throws JPAException {
        EntityTransaction tx = null;
        try {
            tx = entityManager.getTransaction();
            tx.begin();
            this.usuarioDAO.updateUsuario(usuario);
            tx.commit();
            ;
        } catch (JPQLException e) {
            tx.rollback();
            throw new JPAException(e.getMessage());
        }
    }

    @Override
    public void eliminarUsuario(Usuario usuario) throws JPAException {
        EntityTransaction tx = null;
        try {
            tx = this.entityManager.getTransaction();
            tx.begin();
            this.usuarioDAO.deleteUsuario(usuario);
            tx.commit();
        } catch (JPQLException e) {
            tx.rollback();
            throw new JPAException(e.getMessage());
        }
    }
}
