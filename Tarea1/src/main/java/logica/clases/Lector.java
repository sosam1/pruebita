package logica.clases;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import javax.persistence.*;
import datatypes.EstadosU;
import datatypes.Zonas;

@Entity
@DiscriminatorValue("Lector")
@PrimaryKeyJoinColumn(name = "correo")
public class Lector extends Usuario {
    private Date fechaIngreso;
    @Enumerated(EnumType.STRING)
    @Column(name = "estadoUsuario")
    private EstadosU estadoUsuario;
    @Enumerated(EnumType.STRING)
    private Zonas zona;
    private String direccion;
    @OneToMany(mappedBy = "lector", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prestamo> prestamos = new ArrayList<Prestamo>();

    public Lector() {
        super();
    }

    public Lector(String nombre, String correo, String password, Date fechaIngreso, EstadosU estadoUsuario, Zonas zona,
            String direccion) {
        super(nombre, correo, password);
        this.fechaIngreso = fechaIngreso;
        this.estadoUsuario = estadoUsuario;
        this.zona = zona;
        this.direccion = direccion;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public EstadosU getEstadoUsuario() {
        return estadoUsuario;
    }

    public Zonas getZona() {
        return zona;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public void setEstadoUsuario(EstadosU estadoUsuario) {
        this.estadoUsuario = estadoUsuario;
    }

    public void setZona(Zonas zona) {
        this.zona = zona;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setPrestamos(List<Prestamo> prestamos) {
        this.prestamos = prestamos;
    }

    public List<Prestamo> getPrestamos() {
        return prestamos;
    }
}
