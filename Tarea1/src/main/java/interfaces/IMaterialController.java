package interfaces;

import java.util.Date;
import java.util.List;
import logica.clases.Material;

import excepciones.MaterialRepetidoException;
import datatypes.*;

public interface IMaterialController {
    void agregarMaterial(DtMaterial material) throws MaterialRepetidoException;

    void eliminarMaterial(Material material);

    List<DtMaterial> obtenerMateriales();

    Material buscarMaterialPorId(int id);

    List<DtMaterial> obtenerMaterialesPorRango(Date fechaInicio, Date fechaFin);
}
