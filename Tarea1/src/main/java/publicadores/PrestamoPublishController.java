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
import interfaces.IPrestamoController;
import datatypes.DtPrestamo;
import datatypes.EstadosP;
import datatypes.Zonas;

@WebService
@SOAPBinding(style = Style.RPC, parameterStyle = ParameterStyle.WRAPPED)
public class PrestamoPublishController {
    private final Fabrica fabrica;
    private final IPrestamoController prestamoController;
    private Endpoint endpoint;

    public PrestamoPublishController() {
        fabrica = Fabrica.getInstancia();
        prestamoController = fabrica.getIControladorPrestamo();
    }

    @WebMethod(exclude = true)
    public void publicar() {
        endpoint = Endpoint.publish("http://localhost:8080/prestamos", this);
        System.out.println("http://localhost:8080/prestamos");
    }

    @WebMethod(exclude = true)
    public Endpoint getEndpoint() {
        return endpoint;
    }

    @WebMethod
    public void agregarPrestamo(Date fechaSoli, Date fechaDev, EstadosP estadoP,
            String correoLector, String correoBiblio, int idMaterial) {
        try {
            prestamoController.agregarPrestamo(fechaSoli, fechaDev, estadoP, correoLector, correoBiblio, idMaterial);
        } catch (Exception e) {
            // Manejo de excepciones
        }
    }

    @WebMethod
    public DtPrestamo[] obtenerPrestamos() {
        List<DtPrestamo> prestamos = prestamoController.obtenerDtPrestamos();
        return prestamos.toArray(new DtPrestamo[0]);
    }

    @WebMethod
    public DtPrestamo[] obtenerPrestamosPendientes() {
        List<DtPrestamo> prestamos = prestamoController.obtenerDtPrestamosPendientes();
        return prestamos.toArray(new DtPrestamo[0]);
    }

    @WebMethod
    public DtPrestamo[] obtenerPrestamosPorZona(Zonas zona) {
        List<DtPrestamo> prestamos = prestamoController.obtenerDtPrestamosPorZona(zona);
        return prestamos.toArray(new DtPrestamo[0]);
    }

    @WebMethod
    public DtPrestamo[] obtenerPrestamosPorBibliotecario(int idEmp) {
        List<DtPrestamo> prestamos = prestamoController.obtenerDtPrestamoBibliotecario(idEmp);
        return prestamos.toArray(new DtPrestamo[0]);
    }

    @WebMethod
    public DtPrestamo[] obtenerPrestamosActivosLector(String correoLector) {
        List<DtPrestamo> prestamos = prestamoController.obtenerPrestamosActivosLector(correoLector);
        return prestamos.toArray(new DtPrestamo[0]);
    }
}
