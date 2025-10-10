package logica.controladores;

import interfaces.IMaterialController;

import java.util.List;

import datatypes.EstadosP;

import java.util.ArrayList;

import logica.clases.Material;
import logica.clases.Prestamo;
import logica.clases.Libro;
import logica.clases.ArticuloEspecial;
import logica.manejadores.MaterialHandler;
import logica.manejadores.PrestamoHandler;

import java.util.Date;

import excepciones.MaterialRepetidoException;
import datatypes.*;

public class MaterialController implements IMaterialController {

    private List<Material> materiales;

    public MaterialController() {
        super();
        this.materiales = new ArrayList<>();
    }

    @Override
    public void agregarMaterial(DtMaterial material) throws MaterialRepetidoException {
        MaterialHandler mh = MaterialHandler.getInstancia();
        Material nuevoMaterial = mh.buscarMaterialPorId(material.getIdMaterial());
        if (nuevoMaterial != null) {
            throw new MaterialRepetidoException(
                    "El material con ID " + material.getIdMaterial() + " ya existe en el sistema");
        }
        if (material instanceof DtLibro) {
            nuevoMaterial = new Libro(
                    /* material.getIdMaterial(), */
                    material.getFechaRegistro(),
                    ((DtLibro) material).getTitulo(),
                    ((DtLibro) material).getCantPag());
        } else if (material instanceof DtArticuloEspecial) {
            nuevoMaterial = new ArticuloEspecial(
                    material.getFechaRegistro(),
                    ((DtArticuloEspecial) material).getDescripcion(),
                    ((DtArticuloEspecial) material).getPeso(),
                    ((DtArticuloEspecial) material).getDimFisica());
        } else {
            System.out.println("Tipo de material no reconocido.");
            return;
        }
        mh.agregarMaterialH(nuevoMaterial);
    }

    @Override
    public void eliminarMaterial(Material material) {
        materiales.remove(material);
    }

    @Override
    public List<DtMaterial> obtenerMateriales() {
        List<Material> materiales = MaterialHandler.getInstancia().obtenerTodosLosMateriales();
        List<DtMaterial> dtMateriales = new ArrayList<>();
        for (Material m : materiales) {
            if (m instanceof Libro) {
                Libro libro = (Libro) m;
                DtLibro dtLibro = new DtLibro(
                        libro.getIdMaterial(),
                        libro.getFechaRegistro(),
                        libro.getTitulo(),
                        libro.getCantPag());
                dtMateriales.add(dtLibro);
            } else if (m instanceof ArticuloEspecial) {
                ArticuloEspecial art = (ArticuloEspecial) m;
                DtArticuloEspecial dtArt = new DtArticuloEspecial(
                        art.getIdMaterial(),
                        art.getFechaRegistro(),
                        art.getDescripcion(),
                        art.getPeso(),
                        art.getDimFisica());
                dtMateriales.add(dtArt);
            }
        }
        return dtMateriales;
    }

    @Override
    public Material buscarMaterialPorId(int id) {
        for (Material material : materiales) {
            if (material.getIdMaterial() == id) {
                return material;
            }
        }
        return null;
    }

    @Override
    public List<DtMaterial> obtenerMaterialesPorRango(Date fechaInicio, Date fechaFin) {
        List<Material> materiales = MaterialHandler.getInstancia().obtenerMaterialesPorRango(fechaInicio, fechaFin);
        List<DtMaterial> dtMateriales = new ArrayList<>();
        for (Material m : materiales) {
            if (m instanceof Libro) {
                Libro libro = (Libro) m;
                DtLibro dtLibro = new DtLibro(
                        libro.getIdMaterial(),
                        libro.getFechaRegistro(),
                        libro.getTitulo(),
                        libro.getCantPag());
                dtMateriales.add(dtLibro);
            } else if (m instanceof ArticuloEspecial) {
                ArticuloEspecial art = (ArticuloEspecial) m;
                DtArticuloEspecial dtArt = new DtArticuloEspecial(
                        art.getIdMaterial(),
                        art.getFechaRegistro(),
                        art.getDescripcion(),
                        art.getPeso(),
                        art.getDimFisica());
                dtMateriales.add(dtArt);
            }
        }
        return dtMateriales;
    }

    // libros
    /*
     * @Override
     * public List<Material> buscarLibros() {
     * List<Material> libros = new ArrayList<>();
     * for (Material material : materiales) {
     * if (material instanceof Libro) {
     * libros.add(material);
     * }
     * }
     * return libros;
     * }
     * 
     * // articulos especiales
     * 
     * @Override
     * public List<Material> buscarArticulosEspeciales() {
     * List<Material> articulosEspeciales = new ArrayList<>();
     * for (Material material : materiales) {
     * if (!(material instanceof Libro)) {
     * articulosEspeciales.add(material);
     * }
     * }
     * return articulosEspeciales;
     * }
     */

    // Eventualmente se van a agregar / eliminar funciones
}
