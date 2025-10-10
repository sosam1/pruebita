package logica.clases;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "Libro")
@PrimaryKeyJoinColumn(name = "idMaterial")
public class Libro extends Material {
    private String titulo;
    private int cantPag;

    public Libro() {
        super();
    }

    public Libro(Date fechaRegistro, String titulo, int cantPag) {
        super(fechaRegistro);
        this.titulo = titulo;
        this.cantPag = cantPag;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getCantPag() {
        return cantPag;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setCantPag(int cantPag) {
        this.cantPag = cantPag;
    }

}
