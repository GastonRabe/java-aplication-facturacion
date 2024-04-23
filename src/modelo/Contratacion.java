package modelo;

import java.io.Serializable;

/**
 * @author Federico,Gaston,Tobias <br>
 *         Clase abstracta que representara alguno de los tipos de contratacion
 *         que operara el sistema
 */
public abstract class Contratacion implements Cloneable,Serializable {

	int identificacion;
	double precioBase;
	int celular = 0;
	int telefono = 0;
	int tvCable = 0;
	Domicilio domicilio;

	/**
	 * Constructor de la clase, con dos parametros
	 * 
	 * @param identificacion : parametro de tipo entero, sera la identifiaccion de
	 *                       la contratacion
	 * @param domicilio      : parametro de tipo Domicilio, sera el domicilio al
	 *                       cual pertenezca la contratacion
	 */
	public Contratacion(int identidicacion, Domicilio domicilio) {
		this.identificacion = identidicacion;
		this.domicilio = domicilio;
	}

	/**
	 * Metodo que agrega una cantidad pasada por parametro de celulares
	 * 
	 * @param cantidad : parametro de tipo entero que representa la cantidad de
	 *                 celulares que se desean añadir a una contratacion<br>
	 *                 <b>Pre:</b> El parametro cantidad debe ser un numero mayor o
	 *                 igual a 0<br>
	 *                 <b>Post:</b> Si las precondiciones son cumplidas se agregara
	 *                 dicha cantidad de celulares a una contratacion especifica
	 */
	public void agregarCelular(int cantidad) {
		celular += cantidad;
	}

	/**
	 * Metodo que agrega una cantidad pasada por parametro de telefonos
	 * 
	 * @param cantidad : parametro de tipo entero que representa la cantidad de
	 *                 telefonos que se desean añadir a una contratacion<br>
	 *                 <b>Pre:</b> El parametro cantidad debe ser un numero mayor o
	 *                 igual a 0 <br>
	 *                 <b>Post:</b> Si las precondiciones son cumplidas se agregara
	 *                 dicha cantidad de telefonos a una contratacion especifica
	 */
	public void agregarTelefono(int cantidad) {
		telefono += cantidad;
	}

	/**
	 * Metodo que agrega una cantidad pasada por parametro de TvCables
	 * 
	 * @param cantidad : parametro de tipo entero que representa la cantidad de
	 *                 TvCables que se desean añadir a una contratacion<br>
	 *                 <b>Pre:</b> El parametro cantidad debe ser un numero mayor o
	 *                 igual a 0<br>
	 *                 <b>Post:</b> Si las precondiciones son cumplidas se agregara
	 *                 dicha cantidad de TvCable a una contratacion especifica
	 */
	public void agregarTvCable(int cantidad) {
		tvCable += cantidad;
	}

	/**
	 * Metodo que calcula el costo de una contratacion
	 * 
	 * @return el retorno sera el costo total de la contratacion
	 */
	public double calcularCosto() {
		double costo = precioBase;
		costo += celular * 300 + telefono * 200 + tvCable * 250;
		return costo;
	}

	/**
	 * Getter del parametro identificacion
	 * 
	 * @return retorna el valor del parametro
	 */
	public int getIdentificacion() {
		return identificacion;
	}

	/**
	 * Metodo devuelve el domicilio al que pertenece una contratacion
	 * 
	 * @return retorna un objeto de tipo Domicilio
	 */
	public Domicilio getDomicilio() {
		return domicilio;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Contratacion clonado = (Contratacion) super.clone();
		if (this.domicilio != null)
			clonado.domicilio = (Domicilio) this.domicilio.clone();
		return clonado;
	}

	@Override
	public String toString() {
		return "ident.=" + identificacion + ", domicilio=" + domicilio + ", precioBase=" + precioBase + ", celulares="
				+ celular + ", telefonos=" + telefono + ", tvCables=" + tvCable + "]\n";
	}

}
