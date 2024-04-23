package modelo;

import java.util.ArrayList;

import estados.State;
import estados.ConContratacionState;
import estados.MorosoState;
import estados.SinContratacionState;


/**
 * @author Federico,Gaston,Tobias <br>
 *         Clase que representa un tipo de abonado
 */
public class PersonaFisica extends Abonado {
	
	/**
	 * Constructor de la clase con dos parametros
	 * 
	 * @param nombre : parametro de tipo String que representa el nombre del abonado
	 * @param dni    : parametro de String entero que representa el dni del abonado
	 */
	public PersonaFisica(String nombre, String dni) {
		super(nombre, dni);
		estado= new SinContratacionState(this);
	}

	public void cambiarEstado(String estado)
	{
		if (estado.equals("Moroso"))
			this.estado=new MorosoState(this);
		else
			if (estado.equals("SinContratacion"))
				this.estado=new SinContratacionState(this);
			else
				if (estado.equals("ConContratacion"))
					this.estado=new ConContratacionState(this);
	}
	
	/**
	 * Metodo que realiza el descuento o incremento del monto a pagar del abonado,
	 * dependiendo su tipo de pago <br>
	 * <b>Pre:</b>El monto que se pasa como parametro debe ser un numero mayor o
	 * igual a 0 <br>
	 * <b>Post:</b>En cas de cumplir con las precondiciones el resultado sera el
	 * total del monto a pagar on el descuento o incremento realizado
	 */
	@Override
	public double realizarDescuentoOIncremento(double monto) {
		double total = 0;
		if (pago.equals("Cheque"))
			total = monto * 1.1;
		else if (pago.equals("Efectivo"))
			total = monto * 0.8;
		else if (pago.equals("Tarjeta"))
			total = monto;
		return total;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		PersonaFisica clonado = (PersonaFisica) super.clone();
		if (this.domicilios != null) {
			clonado.domicilios = (ArrayList<Domicilio>) this.domicilios.clone();
			int j = this.domicilios.size();
			for (int i = 0; i < j; i++) {
				clonado.domicilios.set(i, (Domicilio) this.domicilios.get(i).clone());
			}
		}
		if (this.contrataciones != null) {
			clonado.contrataciones = (ArrayList<Contratacion>) this.contrataciones.clone();
			int j = this.contrataciones.size();
			for (int i = 0; i < j; i++) {
				clonado.contrataciones.set(i, (Contratacion) this.contrataciones.get(i).clone());
			}
		}
		if (this.facturas != null) {
			clonado.facturas = (ArrayList<Factura>) this.facturas.clone();
			int j = this.facturas.size();
			for (int i = 0; i < j; i++) {
				clonado.facturas.set(i, (Factura) this.facturas.get(i).clone());
			}
		}
		if (this.estado!=null)
			clonado.estado=(State) this.estado.clone();
		return clonado;
	}

	@Override
	public String toString() {
		return "Fisica: " + super.toString();
	}

}
