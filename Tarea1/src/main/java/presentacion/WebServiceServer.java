package presentacion;

import publicadores.*;

/**
 * Clase para ejecutar solo los servicios web SOAP sin la interfaz gráfica
 */
public class WebServiceServer {
    
    public static void main(String[] args) {
        System.out.println("=== Iniciando Servicios Web SOAP ===");
        
        try {
            // Publicar servicio de Materiales
            MaterialPublishController mpc = new MaterialPublishController();
            mpc.publicar();
            System.out.println("✅ Servicio de Materiales publicado en: http://localhost:8080/materiales");

            // Publicar servicio de Préstamos
            PrestamoPublishController ppc = new PrestamoPublishController();
            ppc.publicar();
            System.out.println("✅ Servicio de Préstamos publicado en: http://localhost:8080/prestamos");

            // Publicar servicio de Usuarios
            UsuarioPublishController upc = new UsuarioPublishController();
            upc.publicar();
            System.out.println("✅ Servicio de Usuarios publicado en: http://localhost:8080/usuarios");

            System.out.println("\n🎉 Todos los servicios web están ejecutándose!");
            System.out.println("📋 Servicios disponibles:");
            System.out.println("   - Materiales: http://localhost:8080/materiales?wsdl");
            System.out.println("   - Usuarios: http://localhost:8080/usuarios?wsdl");
            System.out.println("   - Préstamos: http://localhost:8080/prestamos?wsdl");
            System.out.println("\n⏹️  Presiona Ctrl+C para detener los servicios");

            // Mantener el programa ejecutándose
            Thread.currentThread().join();
            
        } catch (Exception e) {
            System.err.println("❌ Error al publicar servicios web: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
