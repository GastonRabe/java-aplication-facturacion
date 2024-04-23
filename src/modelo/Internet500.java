package modelo;

/**
 * @author Federico,Gaston,Tobias <br>
 *         Clase extendida de Contratacion, representa una de las dos
 *         contrataciones posibles que se pueden realizar
 */
public class Internet500 extends Contratacion {

	/**
	 * Constructor de la clase que le enviara los dos parametros a su clase Padre
	 * 
	 * @param identificacion : parametro de tipo entero, sera la identifiaccion de
	 *                       la contratacion
	 * @param domicilio      : parametro de tipo Domicilio, sera el domicilio al
	 *                       cual pertenezca la contratacion
	 */
	public Internet500(int identificacion, Domicilio domicilio) {
		super(identificacion, domicilio);
		this.precioBase = 1000;
	}

	/**
	 * Metodo toString de la clase
	 */
	@Override
	public String toString() {
		return "\t\tInternet500: " + super.toString();
	}

}
