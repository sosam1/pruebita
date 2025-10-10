package com.biblioteca.client;

import java.net.URL;
import java.util.List;
import java.util.ArrayList;

import jakarta.xml.ws.Service;
import jakarta.xml.ws.WebServiceException;

import com.biblioteca.datatypes.DtUsuario;
import com.biblioteca.datatypes.DtLector;
import com.biblioteca.datatypes.DtBibliotecario;
import com.biblioteca.publicadores.UsuarioPublishController;

/**
 * Cliente para consumir el servicio web de Usuarios
 */
public class UsuarioServiceClient {
    
    private static final String USUARIO_SERVICE_URL = "http://localhost:8080/usuarios?wsdl";
    private UsuarioPublishController usuarioService;
    
    public UsuarioServiceClient() {
        try {
            URL url = new URL(USUARIO_SERVICE_URL);
            Service service = Service.create(url, 
                new javax.xml.namespace.QName("http://publicadores/", "UsuarioPublishControllerService"));
            usuarioService = service.getPort(UsuarioPublishController.class);
            System.out.println("✅ Conectado al servicio de usuarios");
        } catch (Exception e) {
            System.err.println("⚠️ No se pudo conectar con el servicio de usuarios, usando datos de prueba: " + e.getMessage());
            usuarioService = null; // Usar datos mock
        }
    }
    
    /**
     * Agrega un nuevo usuario
     */
    public void agregarUsuario(DtUsuario usuario) {
        if (usuarioService != null) {
            try {
                usuarioService.agregarUsuario(usuario);
                System.out.println("✅ Usuario agregado al backend: " + usuario);
            } catch (WebServiceException e) {
                System.err.println("Error al agregar usuario: " + e.getMessage());
                throw new RuntimeException("Error al agregar usuario", e);
            }
        } else {
            System.out.println("✅ Usuario agregado (modo prueba): " + usuario);
        }
    }
    
    /**
     * Obtiene todos los usuarios
     */
    public List<DtUsuario> obtenerUsuarios() {
        if (usuarioService != null) {
            try {
                DtUsuario[] usuarios = usuarioService.obtenerUsuarios();
                List<DtUsuario> lista = new ArrayList<>();
                for (DtUsuario usuario : usuarios) {
                    lista.add(usuario);
                }
                return lista;
            } catch (WebServiceException e) {
                System.err.println("Error al obtener usuarios: " + e.getMessage());
                throw new RuntimeException("Error al obtener usuarios", e);
            }
        } else {
            // Datos de prueba
            List<DtUsuario> usuarios = new ArrayList<>();
            usuarios.add(new DtUsuario("Juan Pérez", "juan@test.com", "password123"));
            usuarios.add(new DtUsuario("María García", "maria@test.com", "password456"));
            usuarios.add(new DtUsuario("Carlos López", "carlos@test.com", "password789"));
            System.out.println("📋 Retornando usuarios de prueba: " + usuarios.size() + " elementos");
            return usuarios;
        }
    }
    
    /**
     * Verifica si el servicio está disponible
     */
    public boolean isServiceAvailable() {
        try {
            usuarioService.obtenerUsuarios();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

