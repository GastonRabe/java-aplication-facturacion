package excepciones;

import modelo.Abonado;
import modelo.IAbonado;

/**
 * @author Federico,Gaston,Tobias <br>
 *         Clase que representa la excepcion cuando una direccion ingresada no
 *         pertenece al abonado al cual se hace referencia
 */
public class DomicilioInexistenteException extends DomicilioEIdentificacionException {

	/**
	 * @param abonado : Parametro de tipo Abonado que sirve para dar detalle del
	 *                abonado el cual no contiene la direccion buscada
	 */
	public DomicilioInexistenteException(IAbonado abonado) {
		super("El domicilio no pertenece al abonado " + abonado.getNombre());
	}

}
