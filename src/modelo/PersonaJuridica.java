package modelo;

import estados.ConContratacionState;

/**
 * @author Federico,Gaston,Tobias <br>
 *         Clase que representa un tipo de abonado
 */
public class PersonaJuridica extends Abonado {

	/**
	 * Constructor de la clase con dos parametros
	 * 
	 * @param nombre : parametro de tipo String que representa el nombre del abonado
	 * @param dni    : parametro de String entero que representa el dni del abonado
	 */
	public PersonaJuridica(String nombre, String dni) {
		super(nombre, dni);
		this.estado=new ConContratacionState(this);
	}

	@Override
	public void cambiarEstado(String estado) {
		//PERSONA JURIDICA NUNCA CAMBIA DE ESTADO
		
	}
	
	/**
	 * Metodo que realiza el descuento o incremento del monto a pagar del abonado,
	 * dependiendo su tipo de pago <b>Pre:</b>El monto que se pasa como parametro
	 * debe ser un numero mayor o igual a 0 <b>Post:</b>En cas de cumplir con las
	 * precondiciones el resultado sera el total del monto a pagar on el descuento o
	 * incremento realizado
	 */
	@Override
	public double realizarDescuentoOIncremento(double monto) {
		double total = 0;
		if (pago.equals("Cheque"))
			total = monto * 1.15;
		else if (pago.equals("Efectivo"))
			total = monto * 0.9;
		else if (pago.equals("Tarjeta"))
			total = monto * 1.2;
		return total;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	@Override
	public String toString() {
		return "Juridica: " + super.toString();
	}

}
