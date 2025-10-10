package persistencia;

import javax.persistence.EntityManager;

public class TestConexion {
    public static void main(String[] args) {
        try {
            EntityManager em = Conexion.getInstancia().getEntityManager();
            em.getTransaction().begin();
            System.out.println("¡Conexión exitosa a la base de datos con JPA/Hibernate!");
            em.getTransaction().commit();
            Conexion.getInstancia().close();
        } catch (Exception e) {
            System.out.println("Error al conectar con la base de datos:");
            e.printStackTrace();
        }
    }
}