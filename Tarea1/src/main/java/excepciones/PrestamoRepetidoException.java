package excepciones;

public class PrestamoRepetidoException extends Exception{
    private static final long serialVersionUID = 1L;

    public PrestamoRepetidoException(String mensaje){
        super(mensaje);
    }
}
