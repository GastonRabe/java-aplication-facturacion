package excepciones;

public class ContratacionException extends Exception{

	public ContratacionException() {
		super("El domicilio no posee contratacion para eliminar");
	}
}
