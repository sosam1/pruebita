package logica.clases;

import java.util.Date;
import datatypes.EstadosP;
import javax.persistence.*;

import logica.clases.Bibliotecario;
import logica.clases.Lector;
import logica.clases.Material;

@Entity
@Table(name = "prestamo")
public class Prestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPrestamo;
    private Date fechaSoli;
    @Enumerated(EnumType.STRING)
    private EstadosP estadoPres;
    private Date fechaDev;
    // private String correoL;
    // private String correoB;
    // private int idMaterial;

    // @Transient
    @ManyToOne
    @JoinColumn(name = "correoL", referencedColumnName = "correo")
    private Lector lector;

    @ManyToOne
    @JoinColumn(name = "idMaterial", referencedColumnName = "idMaterial")
    private Material material;

    @ManyToOne
    @JoinColumn(name = "correoB", referencedColumnName = "correo")
    private Bibliotecario bibliotecario;

    public Prestamo() {
        super();
    }

    public Prestamo(Date fechaSoli, EstadosP estadoPres, Date fechaDev, Lector lector, Bibliotecario bibliotecario,
            Material material) {
        this.fechaSoli = fechaSoli;
        this.estadoPres = estadoPres;
        this.fechaDev = fechaDev;
        this.lector = lector;
        this.bibliotecario = bibliotecario;
        this.material = material;
    }

    public int getIdPrestamo() {
        return idPrestamo;
    }

    public Date getFechaSoli() {
        return fechaSoli;
    }

    public EstadosP getEstadoPres() {
        return estadoPres;
    }

    public Date getFechaDev() {
        return fechaDev;
    }

    public void setFechaSoli(Date fechaSoli) {
        this.fechaSoli = fechaSoli;
    }

    public void setEstadoPres(EstadosP estadoPres) {
        this.estadoPres = estadoPres;
    }

    public void setFechaDev(Date fechaDev) {
        this.fechaDev = fechaDev;
    }

    public Lector getLector() {
        return lector;
    }

    public void setLector(Lector lector) {
        this.lector = lector;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Bibliotecario getBibliotecario() {
        return bibliotecario;
    }

    public void setBibliotecario(Bibliotecario bibliotecario) {
        this.bibliotecario = bibliotecario;
    }
}