package excepciones;

public class CorreoInvalidoException extends Exception {
    private static final long serialVersionUID = 1L;

    public CorreoInvalidoException(String mensaje) {
        super(mensaje);
    }
}