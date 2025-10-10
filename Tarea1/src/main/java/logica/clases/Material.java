package logica.clases;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idMaterial;
    private Date fechaRegistro;

    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prestamo> prestamos = new ArrayList<Prestamo>();

    public Material() {
        super();
    }

    public Material(Date fechaRegistro) {
        super();
        this.fechaRegistro = fechaRegistro;
    }

    public int getIdMaterial() {
        return idMaterial;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public void setPrestamos(List<Prestamo> prestamos) {
        this.prestamos = prestamos;
    }

    public List<Prestamo> getPrestamos() {
        return prestamos;
    }
}
