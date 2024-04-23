package excepciones;

/**
 * @author Federico,Gaston,Tobias <br>
 *         Clase que representa la excepcion que se lanza cuando un abonado
 *         buscado no esta dentro del sistema
 *
 */
public class AbonadoException extends Exception {

	/**
	 * Contructor de la excepcion, directamente al construirse da el mensaje "El
	 * abonado no fue encontrado en el sistema"
	 */
	public AbonadoException() {
		super("El abonado no fue encontrado en el sistema");
	}
}
