package logica.clases;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Usuario {
    @Id
    private String correo;
    private String nombre;
    private String password;

    public Usuario() {
        super();
    }

    public Usuario(String nombre, String correo, String password) {
        super();
        this.nombre = nombre;
        this.correo = correo;
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getPassword(){
        return password;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setPassword(String password){
        this.password = password;
    }

}
