package logica.manejadores;

import logica.clases.Material;

import persistencia.Conexion;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Date;

public class MaterialHandler {

    private static MaterialHandler instancia = null;

    private MaterialHandler() {
    }

    public static MaterialHandler getInstancia() {
        if (instancia == null)
            instancia = new MaterialHandler();
        return instancia;
    }

    public void agregarMaterialH(Material m) {
        Conexion c = Conexion.getInstancia();
        EntityManager em = c.getEntityManager();
        em.getTransaction().begin();
        em.persist(m);
        em.getTransaction().commit();
    }

    public Material buscarMaterialPorId(int id) {
        Conexion c = Conexion.getInstancia();
        EntityManager em = c.getEntityManager();
        return em.find(Material.class, id);
    }

    public List<Material> obtenerTodosLosMateriales() {
        Conexion c = Conexion.getInstancia();
        EntityManager em = c.getEntityManager();
        return em.createQuery("SELECT m FROM Material m", Material.class).getResultList();
    }

    public List<Material> obtenerMaterialesPorRango(Date fechaInicio, Date fechaFin) {
        Conexion c = Conexion.getInstancia();
        EntityManager em = c.getEntityManager();
        return em
                .createQuery("SELECT m FROM Material m WHERE m.fechaRegistro BETWEEN :fechaInicio AND :fechaFin",
                        Material.class)
                .setParameter("fechaInicio", fechaInicio)
                .setParameter("fechaFin", fechaFin)
                .getResultList();
    }
}
