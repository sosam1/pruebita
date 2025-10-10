package presentacion;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.ParameterStyle;
import jakarta.jws.soap.SOAPBinding.Style;
import jakarta.xml.ws.Endpoint;

import datatypes.DtMaterial;
import datatypes.DtUsuario;
import datatypes.DtPrestamo;
import datatypes.EstadosP;
import datatypes.Zonas;

/**
 * Servidor de prueba para servicios web SOAP sin dependencias de base de datos
 */
public class TestWebServiceServer {
    
    @WebService
    @SOAPBinding(style = Style.RPC, parameterStyle = ParameterStyle.WRAPPED)
    public static class TestMaterialService {
        
        @WebMethod
        public void agregarMaterial(DtMaterial material) {
            System.out.println("✅ Material agregado: " + material);
        }
        
        @WebMethod
        public DtMaterial[] obtenerMateriales() {
            List<DtMaterial> materiales = new ArrayList<>();
            materiales.add(new DtMaterial(1, new Date()));
            materiales.add(new DtMaterial(2, new Date()));
            return materiales.toArray(new DtMaterial[0]);
        }
        
        @WebMethod
        public DtMaterial[] obtenerMaterialesPorRango(Date fechaInicio, Date fechaFin) {
            List<DtMaterial> materiales = new ArrayList<>();
            materiales.add(new DtMaterial(1, fechaInicio));
            return materiales.toArray(new DtMaterial[0]);
        }
    }
    
    @WebService
    @SOAPBinding(style = Style.RPC, parameterStyle = ParameterStyle.WRAPPED)
    public static class TestUsuarioService {
        
        @WebMethod
        public void agregarUsuario(DtUsuario usuario) {
            System.out.println("✅ Usuario agregado: " + usuario);
        }
        
        @WebMethod
        public DtUsuario[] obtenerUsuarios() {
            List<DtUsuario> usuarios = new ArrayList<>();
            usuarios.add(new DtUsuario("Juan Pérez", "juan@test.com", "password123"));
            usuarios.add(new DtUsuario("María García", "maria@test.com", "password456"));
            return usuarios.toArray(new DtUsuario[0]);
        }
    }
    
    @WebService
    @SOAPBinding(style = Style.RPC, parameterStyle = ParameterStyle.WRAPPED)
    public static class TestPrestamoService {
        
        @WebMethod
        public void agregarPrestamo(Date fechaSoli, Date fechaDev, EstadosP estadoP,
                String correoLector, String correoBiblio, int idMaterial) {
            System.out.println("✅ Préstamo agregado: " + correoLector + " -> " + idMaterial);
        }
        
        @WebMethod
        public DtPrestamo[] obtenerPrestamos() {
            List<DtPrestamo> prestamos = new ArrayList<>();
            prestamos.add(new DtPrestamo(1, new Date(), EstadosP.PENDIENTE, new Date(), 
                "juan@test.com", "biblio@test.com", 1));
            return prestamos.toArray(new DtPrestamo[0]);
        }
        
        @WebMethod
        public DtPrestamo[] obtenerPrestamosPendientes() {
            return obtenerPrestamos();
        }
        
        @WebMethod
        public DtPrestamo[] obtenerPrestamosPorZona(Zonas zona) {
            return obtenerPrestamos();
        }
        
        @WebMethod
        public DtPrestamo[] obtenerPrestamosPorBibliotecario(int idEmp) {
            return obtenerPrestamos();
        }
        
        @WebMethod
        public DtPrestamo[] obtenerPrestamosActivosLector(String correoLector) {
            return obtenerPrestamos();
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Iniciando Servidor de Prueba SOAP ===");
        
        try {
            // Publicar servicios de prueba
            Endpoint.publish("http://localhost:8080/materiales", new TestMaterialService());
            System.out.println("✅ Servicio de Materiales (Prueba) publicado en: http://localhost:8080/materiales");

            Endpoint.publish("http://localhost:8080/usuarios", new TestUsuarioService());
            System.out.println("✅ Servicio de Usuarios (Prueba) publicado en: http://localhost:8080/usuarios");

            Endpoint.publish("http://localhost:8080/prestamos", new TestPrestamoService());
            System.out.println("✅ Servicio de Préstamos (Prueba) publicado en: http://localhost:8080/prestamos");

            System.out.println("\n🎉 Servicios de prueba ejecutándose!");
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
