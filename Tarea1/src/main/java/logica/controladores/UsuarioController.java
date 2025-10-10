package logica.controladores;

import interfaces.IUsuarioController;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import logica.clases.Usuario;
import logica.clases.Bibliotecario;
import logica.clases.Lector;
import logica.manejadores.UsuarioHandler;
import excepciones.UsuarioRepetidoException;
import datatypes.*;
import persistencia.*;

public class UsuarioController implements IUsuarioController {

    private List<Usuario> usuarios;

    public UsuarioController() {
        super();
        this.usuarios = new ArrayList<>();
    }

    @Override
    public void agregarUsuario(DtUsuario usuario) throws UsuarioRepetidoException {
        UsuarioHandler uh = UsuarioHandler.getInstancia();
        Usuario nuevoUsuario = uh.buscarUsuarioPorCorreo(usuario.getCorreo());
        if (nuevoUsuario != null)
            throw new UsuarioRepetidoException(
                    "El usuario con correo " + usuario.getCorreo() + " ya existe en el sistema");
        if (usuario instanceof DtLector) {
            nuevoUsuario = new Lector(usuario.getNombre(), usuario.getCorreo(), usuario.getPassword(), 
                    ((DtLector) usuario).getFechaIngreso(), ((DtLector) usuario).getEstadoUsuario(),
                    ((DtLector) usuario).getZona(), ((DtLector) usuario).getDireccion());
        } else if (usuario instanceof DtBibliotecario) {
            nuevoUsuario = new Bibliotecario(usuario.getNombre(), usuario.getCorreo(), usuario.getPassword());
        }
        uh.agregarUsuarioH(nuevoUsuario);
    }

    @Override
    public void eliminarUsuario(Usuario usuario) {
        usuarios.remove(usuario);
    }

    @Override
    public List<DtUsuario> obtenerUsuarios() {
        List<Usuario> listaUsuarios = UsuarioHandler.getInstancia().obtenerTodosLosUsuarios();
        List<DtUsuario> dtUsuarios = new ArrayList<>();
        for (Usuario usuario : listaUsuarios) {
            if (usuario instanceof Lector) {
                Lector lector = (Lector) usuario;
                DtLector dtLector = new DtLector(lector.getNombre(), lector.getCorreo(), lector.getPassword(), lector.getFechaIngreso(),
                        lector.getEstadoUsuario(), lector.getZona(), lector.getDireccion());
                dtUsuarios.add(dtLector);
            } else if (usuario instanceof Bibliotecario) {
                Bibliotecario bibliotecario = (Bibliotecario) usuario;
                DtBibliotecario dtBibliotecario = new DtBibliotecario(bibliotecario.getNombre(),
                        bibliotecario.getCorreo(), bibliotecario.getPassword(), bibliotecario.getIdEmp());
                dtUsuarios.add(dtBibliotecario);
            }
        }
        return dtUsuarios;
    }

    @Override
    public Usuario buscarUsuarioPorCorreo(String correo) {
        UsuarioHandler uh = UsuarioHandler.getInstancia();
        return uh.buscarUsuarioPorCorreo(correo);
    }

    @Override
    public void cambiarEstadoLector(DtLector dtLector, EstadosU nuevoEstado) {
        UsuarioHandler uh = UsuarioHandler.getInstancia();
        Usuario existe = uh.buscarUsuarioPorCorreo(dtLector.getCorreo());
        if (existe != null && existe instanceof Lector) {
            Lector lector = (Lector) existe;
            lector.setEstadoUsuario(nuevoEstado);
            actualizarUsuario(lector);
        } else {
            System.out.println("El usuario no existe o no es un lector.");
        }
    }

    @Override
    public void cambiarZonaLector(DtLector dtlector, Zonas nuevaZona) {
        UsuarioHandler uh = UsuarioHandler.getInstancia();
        Usuario existe = uh.buscarUsuarioPorCorreo(dtlector.getCorreo());
        if (existe != null && existe instanceof Lector) {
            Lector lector = (Lector) existe;
            lector.setZona(nuevaZona);
            actualizarUsuario(lector);
        } else {
            System.out.println("El usuario no existe o no es un lector.");
        }
    }

    @Override
    public void actualizarUsuario(Usuario usuario) {
        UsuarioHandler uh = UsuarioHandler.getInstancia();
        uh.actualizarUsuario(usuario);
    }

    @Override
    public boolean validarLogin(String correo, String password) {
        UsuarioHandler uh = UsuarioHandler.getInstancia();
        return uh.validLogin(correo, password);
    }

}
