package excepciones;

public class MaterialRepetidoException extends Exception {
    private static final long serialVersionUID = 1L;

    public MaterialRepetidoException(String mensaje) {
        super(mensaje);
    }
}
