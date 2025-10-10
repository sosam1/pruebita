package logica.manejadores;

import logica.clases.Usuario;
import java.util.*;
import persistencia.Conexion;
import javax.persistence.EntityManager;

public class UsuarioHandler {
    private static UsuarioHandler instancia = null;

    private UsuarioHandler() {
    }

    public static UsuarioHandler getInstancia() {
        if (instancia == null)
            instancia = new UsuarioHandler();
        return instancia;
    }

    public void agregarUsuarioH(Usuario u) {
        Conexion c = Conexion.getInstancia();
        EntityManager em = c.getEntityManager();
        em.getTransaction().begin();
        em.persist(u);
        em.flush();
        em.refresh(u);
        em.getTransaction().commit();
    }

    public Usuario buscarUsuarioPorCorreo(String correo) {
        Conexion c = Conexion.getInstancia();
        EntityManager em = c.getEntityManager();
        return em.find(Usuario.class, correo);
    }

    public List<Usuario> obtenerTodosLosUsuarios() {
        Conexion c = Conexion.getInstancia();
        EntityManager em = c.getEntityManager();
        return em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
    }

    public void actualizarUsuario(Usuario u) {
        Conexion c = Conexion.getInstancia();
        EntityManager em = c.getEntityManager();
        em.getTransaction().begin();
        em.merge(u);
        em.getTransaction().commit();
    }

    public boolean validLogin(String correo, String password){
        Conexion c = Conexion.getInstancia();
        EntityManager em = c.getEntityManager();
        return em.createQuery("""
                SELECT u FROM Usuario u
                WHERE u.correo = :email AND
                u.password = :password
                """, Usuario.class)
                .setParameter("email", correo)
                .setParameter("password", password)
                .getResultList()
                .size() > 0;
    }

}
