package logica.controladores;

import interfaces.IPrestamoController;

import java.util.ArrayList;
import java.util.List;
import logica.clases.*;
import java.util.Date;

import datatypes.*;
import excepciones.PrestamoRepetidoException;
import logica.manejadores.PrestamoHandler;
import logica.manejadores.MaterialHandler;
import logica.manejadores.UsuarioHandler;

public class PrestamoController implements IPrestamoController {

    private List<Prestamo> prestamos;

    public PrestamoController() {
        super();
        this.prestamos = new ArrayList<>();
    }

    @Override
    public void agregarPrestamo(Date fechaSoli, Date fechaDev, EstadosP estadoP, String correoLector,
            String correoBiblio, int idMaterial) throws PrestamoRepetidoException {
        PrestamoHandler pH = PrestamoHandler.getInstancia();

        MaterialHandler mH = MaterialHandler.getInstancia();
        Material m = mH.buscarMaterialPorId(idMaterial);

        UsuarioHandler uH = UsuarioHandler.getInstancia();
        Lector uLector = (Lector) uH.buscarUsuarioPorCorreo(correoLector);
        // Convertir de usario a lector

        UsuarioHandler uH2 = UsuarioHandler.getInstancia();
        Bibliotecario uBibliotecario = (Bibliotecario) uH2.buscarUsuarioPorCorreo(correoBiblio);

        if (existePrestamoActivo(idMaterial)) {
            throw new PrestamoRepetidoException(
                    "Ya existe un prestamo activo para este material");
        } else { // Si el prestamo no existe
            Prestamo nuevoPrestamo = new Prestamo(
                    fechaSoli,
                    estadoP,
                    fechaDev,
                    uLector,
                    uBibliotecario,
                    m);
            m.getPrestamos().add(nuevoPrestamo);
            uLector.getPrestamos().add(nuevoPrestamo);
            uBibliotecario.getPrestamos().add(nuevoPrestamo);
            pH.agregarPrestamoH(nuevoPrestamo);
        }
    }

    @Override
    public void eliminarPrestamo(Prestamo prestamo) {
        prestamos.remove(prestamo);
    }

    @Override
    public List<DtPrestamo> obtenerDtPrestamos() {
        List<Prestamo> listaPrestamos = PrestamoHandler.getInstancia().obtenerPrestamos();
        List<DtPrestamo> dtPrestamos = new ArrayList<>();
        for (Prestamo prestamo : listaPrestamos) {
            DtPrestamo dtPrestamo = new DtPrestamo(
                    prestamo.getIdPrestamo(),
                    prestamo.getFechaSoli(),
                    prestamo.getEstadoPres(),
                    prestamo.getFechaDev(),
                    prestamo.getLector().getCorreo(),
                    prestamo.getBibliotecario().getCorreo(),
                    prestamo.getMaterial().getIdMaterial());
            dtPrestamos.add(dtPrestamo);
        }
        return dtPrestamos;
    }

    @Override
    public Prestamo buscarPrestamoPorId(int id) {
        for (Prestamo prestamo : prestamos) {
            if (prestamo.getIdPrestamo() == id) {
                return prestamo;
            }
        }
        return null;
    }

    @Override
    public void cambiarEstadoPrestamo(DtPrestamo Prestamo, EstadosP nuevoEstado) {
        PrestamoHandler pH = PrestamoHandler.getInstancia();
        Prestamo prestamo = pH.buscarPrestamoPorId(Prestamo.getIdPrestamo());
        if (prestamo != null) {
            prestamo.setEstadoPres(nuevoEstado);
            actualizarPrestamo(prestamo);
        }
    }

    @Override
    public void cambiarMaterialPrestamo(DtPrestamo Prestamo, int nuevoMaterialID) {
        PrestamoHandler pH = PrestamoHandler.getInstancia();
        Prestamo prestamo = pH.buscarPrestamoPorId(Prestamo.getIdPrestamo());
        MaterialHandler mH = MaterialHandler.getInstancia();
        Material nuevoMaterial = mH.buscarMaterialPorId(nuevoMaterialID);
        if (nuevoMaterial != null) {
            if (prestamo != null) {
                prestamo.setMaterial(nuevoMaterial);
                actualizarPrestamo(prestamo);
            }
        }
    }

    @Override
    public void cambiarCorreoLectorPrestamo(DtPrestamo Prestamo, String nuevoCorreo) {
        PrestamoHandler pH = PrestamoHandler.getInstancia();
        Prestamo prestamo = pH.buscarPrestamoPorId(Prestamo.getIdPrestamo());
        if (prestamo != null) {
            Lector nuevoLector = (Lector) UsuarioHandler.getInstancia().buscarUsuarioPorCorreo(nuevoCorreo);
            if (nuevoLector != null) {
                prestamo.setLector(nuevoLector);
                actualizarPrestamo(prestamo);
            }
        }
    }

    @Override
    public void cambiarCorreoBibliotecarioPrestamo(DtPrestamo Prestamo, String nuevoCorreo) {
        PrestamoHandler pH = PrestamoHandler.getInstancia();
        Prestamo prestamo = pH.buscarPrestamoPorId(Prestamo.getIdPrestamo());
        if (prestamo != null) {
            Bibliotecario nuevoBiblio = (Bibliotecario) UsuarioHandler.getInstancia()
                    .buscarUsuarioPorCorreo(nuevoCorreo);
            if (nuevoBiblio != null) {
                prestamo.setBibliotecario(nuevoBiblio);
                actualizarPrestamo(prestamo);
            }
        }
    }

    @Override
    public void cambiarFechaSolicitudPrestamo(DtPrestamo Prestamo, Date nuevaFecha) {
        PrestamoHandler pH = PrestamoHandler.getInstancia();
        Prestamo prestamo = pH.buscarPrestamoPorId(Prestamo.getIdPrestamo());
        if (prestamo != null) {
            prestamo.setFechaSoli(nuevaFecha);
            actualizarPrestamo(prestamo);
        }
    }

    @Override
    public void cambiarFechaDevolucionPrestamo(DtPrestamo Prestamo, Date nuevaFecha) {
        PrestamoHandler pH = PrestamoHandler.getInstancia();
        Prestamo prestamo = pH.buscarPrestamoPorId(Prestamo.getIdPrestamo());
        if (prestamo != null) {
            prestamo.setFechaDev(nuevaFecha);
            actualizarPrestamo(prestamo);
        }
    }

    @Override
    public void actualizarPrestamo(Prestamo prestamo) {
        PrestamoHandler pH = PrestamoHandler.getInstancia();
        pH.actualizarPrestamoH(prestamo);
    }

    @Override
    public boolean existePrestamoActivo(int idMaterial) {
        PrestamoHandler pH = PrestamoHandler.getInstancia();
        List<Prestamo> prestamos = pH.obtenerPrestamos();
        for (Prestamo p : prestamos) {
            if (p.getMaterial().getIdMaterial() == idMaterial
                    && (p.getEstadoPres() == EstadosP.PENDIENTE || p.getEstadoPres() == EstadosP.EN_CURSO)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<DtPrestamo> obtenerDtPrestamoBibliotecario(int idEmp) {
        List<Prestamo> listaPrestamosBiblio = PrestamoHandler.getInstancia().obtenerPrestamosPorBibliotecario(idEmp);
        List<DtPrestamo> dtPrestamosBiblio = new ArrayList<>();

        for (Prestamo p : listaPrestamosBiblio) {
            DtPrestamo dtPrestamo = new DtPrestamo(
                    p.getIdPrestamo(),
                    p.getFechaSoli(),
                    p.getEstadoPres(),
                    p.getFechaDev(),
                    p.getLector().getCorreo(),
                    p.getBibliotecario().getCorreo(),
                    p.getMaterial().getIdMaterial());
            dtPrestamosBiblio.add(dtPrestamo);
        }
        return dtPrestamosBiblio;
    }

    @Override
    public List<DtPrestamo> obtenerDtPrestamosPorZona(Zonas zona) {
        List<Prestamo> listaPrestamosZona = PrestamoHandler.getInstancia().obtenerPrestamosPorZona(zona);
        List<DtPrestamo> dtPrestamosZona = new ArrayList<>();

        for (Prestamo p : listaPrestamosZona) {
            DtPrestamo dtPrestamo = new DtPrestamo(
                    p.getIdPrestamo(),
                    p.getFechaSoli(),
                    p.getEstadoPres(),
                    p.getFechaDev(),
                    p.getLector().getCorreo(),
                    p.getBibliotecario().getCorreo(),
                    p.getMaterial().getIdMaterial());
            dtPrestamosZona.add(dtPrestamo);
        }
        return dtPrestamosZona;
    }

    @Override
    public List<DtPrestamo> obtenerDtPrestamosPendientes() {
        List<Prestamo> listaPrestamos = PrestamoHandler.getInstancia().obtenerPrestamosPendientes();
        List<DtPrestamo> dtPrestamos = new ArrayList<>();

        for (Prestamo p : listaPrestamos) {
            DtPrestamo dtPrestamo = new DtPrestamo(
                    p.getIdPrestamo(),
                    p.getFechaSoli(),
                    p.getEstadoPres(),
                    p.getFechaDev(),
                    p.getLector().getCorreo(),
                    p.getBibliotecario().getCorreo(),
                    p.getMaterial().getIdMaterial());
            dtPrestamos.add(dtPrestamo);
        }
        return dtPrestamos;
    }

    @Override
    public List<DtPrestamo> obtenerPrestamosActivosLector(String correoLector) {
        PrestamoHandler pH = PrestamoHandler.getInstancia();
        List<Prestamo> listaPrestamos = pH.obtenerPrestamosActivosLectorH(correoLector);
        List<DtPrestamo> dtPrestamos = new ArrayList<>();
        for (Prestamo p : listaPrestamos) {
            DtPrestamo dtPrestamo = new DtPrestamo(
                    p.getIdPrestamo(),
                    p.getFechaSoli(),
                    p.getEstadoPres(),
                    p.getFechaDev(),
                    p.getLector().getCorreo(),
                    p.getBibliotecario().getCorreo(),
                    p.getMaterial().getIdMaterial());
            dtPrestamos.add(dtPrestamo);
        }
        return dtPrestamos;
    }
}
