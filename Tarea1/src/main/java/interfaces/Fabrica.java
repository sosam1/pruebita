package interfaces;

import logica.controladores.*;

import logica.controladores.UsuarioController;

public class Fabrica {
    private static Fabrica instancia = null;

    private Fabrica() {
    }

    public static Fabrica getInstancia() {
        if (instancia == null) {
            instancia = new Fabrica();
        }
        return instancia;
    }

    public IUsuarioController getIControladorUsuario() {
        return new UsuarioController();
    }

    public IPrestamoController getIControladorPrestamo() {
        return new PrestamoController();
    }

    public IMaterialController getIControladorMaterial() {
        return new MaterialController();
    }
}
