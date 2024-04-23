package modelo;

/**
 * @author Federico,Gaston,Tobias <br>
 *         Clase extendida de Contratacion, representa una de las dos
 *         contrataciones posibles que se pueden realizar
 */
public class Internet100 extends Contratacion {

	/**
	 * Constructor de la clase que le enviara los dos parametros a su clase Padre
	 * 
	 * @param identificacion : parametro de tipo entero, sera la identifiaccion de
	 *                       la contratacion
	 * @param domicilio      : parametro de tipo Domicilio, sera el domicilio al
	 *                       cual pertenezca la contratacion
	 */
	public Internet100(int identificacion, Domicilio domicilio) {
		super(identificacion, domicilio);
		this.precioBase = 850;
	}

	@Override
	public String toString() {
		return "\t\tInternet100: " + super.toString();
	}

}
