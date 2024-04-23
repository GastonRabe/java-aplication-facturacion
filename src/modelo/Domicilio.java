package modelo;

import java.io.Serializable;

/**
 * @author Federico,Gaston,Tobias <br>
 *         Clase que representa un domicilio de un abonado
 */
public class Domicilio implements Cloneable,Serializable {

	private String direccion;

	/**
	 * Constructor con un parametro que es la direccion del domicilio a crear <br>
	 * 
	 * @param direccion
	 */
	public Domicilio(String direccion) {
		super();
		this.direccion = direccion;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Override
	public Object clone() throws CloneNotSupportedException { 
		return super.clone();
	}

	@Override
	public String toString() {
		return direccion;
	}

}
