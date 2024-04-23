package modelo;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Iterator;

/**
 * @author Federico,Gaston,Tobias <br>
 *         Clase que representa las facturas del sistema
 */
public class Factura implements Cloneable,Serializable,IFactura {

	private String abonado;
	private int mes;
	private double costo;
	private double costoConModificacion;

	/**
	 * Contructor de la clase con un parametro
	 * 
	 * @param abonado2 : parametro de tipo Abonado que representa el abonado al cual
	 *                refiere la factura
	 */
	public Factura(String abonado,double costo, double costoConModificacion,int mes) {
		super();
		this.abonado = abonado;
		this.costo=costo;
		this.costoConModificacion=costoConModificacion;
		this.mes=mes;
	}

	/**
	 * Metodo que calcula el total que tendra que pagar el abonado por todas las
	 * contrataciones realizadas
	 * 
	 * @return el retorno sera la cantidad total a pagar por el mismo
	 */
	
	public String getAbonado() {
		return abonado;
	}

	public double getCosto() {
		return costo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}

	public double getCostoConModificacion() {
		return costoConModificacion;
	}

	public void setCostoConModificacion(double costoConModificacion) {
		this.costoConModificacion = costoConModificacion;
	}

	public void setAbonado(String abonado) {
		this.abonado = abonado;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public String toString() {
		DecimalFormat formato= new DecimalFormat("#.00");
		return "Factura de: "+abonado+" del mes: "+mes+" \nTotal sin Descuento: \t$" + formato.format(costo)
				+ " \nTotal con descuento o incremento (depende de la forma de pago y tipo de abonado): \t$"
				+ formato.format(costoConModificacion) + "\n\n";
	}

}
