package interfaces;

import java.util.Date;
import java.util.List;
import datatypes.*;
import logica.clases.Prestamo;
import excepciones.PrestamoRepetidoException;

public interface IPrestamoController {
    void agregarPrestamo(Date fechaSoli, Date fechaDev, EstadosP estadoP, String correoLector, String correoBiblio,
            int idMaterial) throws PrestamoRepetidoException;

    void eliminarPrestamo(Prestamo prestamo);

    List<DtPrestamo> obtenerDtPrestamos();

    Prestamo buscarPrestamoPorId(int id);

    void cambiarEstadoPrestamo(DtPrestamo Prestamo, EstadosP nuevoEstado);

    void cambiarMaterialPrestamo(DtPrestamo Prestamo, int nuevoMaterialID);

    void cambiarCorreoLectorPrestamo(DtPrestamo Prestamo, String nuevoCorreo);

    void cambiarCorreoBibliotecarioPrestamo(DtPrestamo Prestamo, String nuevoCorreo);

    void cambiarFechaSolicitudPrestamo(DtPrestamo Prestamo, Date nuevaFecha);

    void cambiarFechaDevolucionPrestamo(DtPrestamo Prestamo, Date nuevaFecha);

    void actualizarPrestamo(Prestamo prestamo);

    boolean existePrestamoActivo(int Material);

    List<DtPrestamo> obtenerDtPrestamoBibliotecario(int idEmp);

    List<DtPrestamo> obtenerDtPrestamosPorZona(Zonas zona);

    List<DtPrestamo> obtenerDtPrestamosPendientes();

    List<DtPrestamo> obtenerPrestamosActivosLector(String correoLector);
}
