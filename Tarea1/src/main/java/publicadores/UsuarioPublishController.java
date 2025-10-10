package publicadores;

import java.util.List;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.ParameterStyle;
import jakarta.jws.soap.SOAPBinding.Style;
import jakarta.xml.ws.Endpoint;

import interfaces.Fabrica;
import interfaces.IUsuarioController;
import datatypes.DtUsuario;

@WebService
@SOAPBinding(style = Style.RPC, parameterStyle = ParameterStyle.WRAPPED)
public class UsuarioPublishController {
    private final Fabrica fabrica;
    private final IUsuarioController usuarioController;
    private Endpoint endpoint;

    public UsuarioPublishController() {
        fabrica = Fabrica.getInstancia();
        usuarioController = fabrica.getIControladorUsuario();
    }

    @WebMethod(exclude = true)
    public void publicar() {
        endpoint = Endpoint.publish("http://localhost:8080/usuarios", this);
        System.out.println("http://localhost:8080/usuarios");
    }

    @WebMethod(exclude = true)
    public Endpoint getEndpoint() {
        return endpoint;
    }

    @WebMethod
    public void agregarUsuario(DtUsuario usuario) {
        try {
            usuarioController.agregarUsuario(usuario);
        } catch (Exception e) {
            // Manejo de excepciones
        }
    }

    @WebMethod
    public DtUsuario[] obtenerUsuarios() {
        List<DtUsuario> usuarios = usuarioController.obtenerUsuarios();
        return usuarios.toArray(new DtUsuario[0]);
    }
}
