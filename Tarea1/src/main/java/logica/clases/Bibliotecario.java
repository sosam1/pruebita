package logica.clases;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@DiscriminatorValue("Bibliotecario")
@PrimaryKeyJoinColumn(name = "correo")
public class Bibliotecario extends Usuario {
    @Column(name = "idEmp", unique = true, nullable = false)
    private int idEmp;
    @OneToMany(mappedBy = "bibliotecario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prestamo> prestamos = new ArrayList<Prestamo>();

    public Bibliotecario() {
        super();
    }

    public Bibliotecario(String nombre, String correo, String password) {
        super(nombre, correo, password);
    }

    public Bibliotecario(String nombre, String correo, String password, int idEmp) {
        super(nombre, correo, password);
        this.idEmp = idEmp;
    }

    public int getIdEmp() {
        return idEmp;
    }

    public void setIdEmp(int idEmp) {
        this.idEmp = idEmp;
    }

    public void setPrestamos(List<Prestamo> prestamos) {
            this.prestamos = prestamos;
    }
    
    public List<Prestamo> getPrestamos() {
        return prestamos;
    }
}
