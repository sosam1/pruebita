package logica.clases;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "articuloEspecial")
@PrimaryKeyJoinColumn(name = "idMaterial")
public class ArticuloEspecial extends Material {
    private String descripcion;
    private float peso;
    private float dimFisica;

    public ArticuloEspecial() {
        super();
    }

    public ArticuloEspecial(Date fechaRegistro, String descripcion, float peso, float dimFisica) {
        super(fechaRegistro);
        this.descripcion = descripcion;
        this.peso = peso;
        this.dimFisica = dimFisica;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public float getPeso() {
        return peso;
    }

    public float getDimFisica() {
        return dimFisica;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public void setDimFisica(float dimFisica) {
        this.dimFisica = dimFisica;
    }

}
