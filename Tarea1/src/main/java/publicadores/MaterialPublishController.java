package publicadores;

import java.util.Date;
import java.util.List;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.ParameterStyle;
import jakarta.jws.soap.SOAPBinding.Style;
import jakarta.xml.ws.Endpoint;

import interfaces.Fabrica;
import interfaces.IMaterialController;
import datatypes.DtMaterial;

@WebService
@SOAPBinding(style = Style.RPC, parameterStyle = ParameterStyle.WRAPPED)
public class MaterialPublishController {
    private final Fabrica fabrica;
    private final IMaterialController materialController;
    private Endpoint endpoint;

    public MaterialPublishController() {
        fabrica = Fabrica.getInstancia();
        materialController = fabrica.getIControladorMaterial();
    }

    @WebMethod(exclude = true)
    public void publicar() {
        endpoint = Endpoint.publish("http://localhost:8080/materiales", this);
        System.out.println("http://localhost:8080/materiales");
    }

    @WebMethod(exclude = true)
    public Endpoint getEndpoint() {
        return endpoint;
    }

    @WebMethod
    public void agregarMaterial(DtMaterial material) {
        try {
            materialController.agregarMaterial(material);
        } catch (Exception e) {
            // Manejo de excepciones
        }
    }

    @WebMethod
    public DtMaterial[] obtenerMateriales() {
        List<DtMaterial> materiales = materialController.obtenerMateriales();
        return materiales.toArray(new DtMaterial[0]);
    }

    @WebMethod
    public DtMaterial[] obtenerMaterialesPorRango(Date fechaInicio, Date fechaFin) {
        List<DtMaterial> materiales = materialController.obtenerMaterialesPorRango(fechaInicio, fechaFin);
        return materiales.toArray(new DtMaterial[0]);
    }
}
