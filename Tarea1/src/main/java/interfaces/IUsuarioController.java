package interfaces;

import java.util.List;
import logica.clases.Usuario;
import excepciones.UsuarioRepetidoException;
import datatypes.*;

public interface IUsuarioController {
    void agregarUsuario(DtUsuario usuario) throws UsuarioRepetidoException;

    void eliminarUsuario(Usuario usuario);

    List<DtUsuario> obtenerUsuarios();

    Usuario buscarUsuarioPorCorreo(String correo);

    void cambiarEstadoLector(DtLector dtlector, EstadosU nuevoEstado);

    void cambiarZonaLector(DtLector dtlector, Zonas nuevaZona);

    void actualizarUsuario(Usuario usuario);

    boolean validarLogin(String correo, String password);
}