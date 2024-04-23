package modelo;

import java.util.ArrayList;
import java.util.Iterator;

import estados.State;
import excepciones.ContratacionException;
import excepciones.DomicilioEIdentificacionException;
import excepciones.ServicioInexistenteException;

public abstract class DecoratorPago implements IAbonado{

	protected IAbonado encapsulado;

	public DecoratorPago(IAbonado encapsulado) {
		super();
		this.encapsulado = encapsulado;
	}

	@Override
	public String getNombre() {
		return encapsulado.getNombre();
	}

	@Override
	public String getDni() {
		return encapsulado.getDni();
	}

	@Override
	public void setDomicilios(Domicilio domicilio) {
		encapsulado.setDomicilios(domicilio);
	}

	public void setPago(String pago)
	{
		encapsulado.setPago(pago);
	}
	
	@Override
	public ArrayList<Contratacion> getContrataciones() {
		return encapsulado.getContrataciones();
	}

	@Override
	public void setContrataciones(Contratacion contratacion) {
		encapsulado.setContrataciones(contratacion);
	}

	@Override
	public void borroDomicilio(Domicilio domicilio) {
		encapsulado.borroDomicilio(domicilio);
	}

	public ArrayList<Domicilio> getDomicilios()
	{
		return encapsulado.getDomicilios();
	}
	
	@Override
	public boolean revisarContrataciones(Domicilio domicilio, int identificacion)
			throws DomicilioEIdentificacionException {
		return encapsulado.revisarContrataciones(domicilio, identificacion);
	}

	@Override
	public void realizarContratacion(Contratacion contratacion,Domicilio domicilio, int id) throws DomicilioEIdentificacionException{
		encapsulado.realizarContratacion(contratacion,domicilio,id);
		
	}

	@Override
	public abstract double realizarDescuentoOIncremento(double monto);

	public double calcularTotal() {
		return encapsulado.calcularTotal();
	}


	@Override
	public void eliminarContratacion(Contratacion contratacion) {
		encapsulado.eliminarContratacion(contratacion);
	}

	public ArrayList<Factura> getFacturas() {
		return this.encapsulado.getFacturas();
	}

	public void agregarFactura(Factura factura) {
		this.encapsulado.agregarFactura(factura);;
	}
	
	public void pagarFactura()
	{
		encapsulado.pagarFactura();
	}
	
	public void cambiarEstado(String estado)
	{
		encapsulado.cambiarEstado(estado);
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		DecoratorPago clon=(DecoratorPago) super.clone();
		if (this.encapsulado!=null)
			clon.encapsulado=(Abonado) this.encapsulado.clone();
		return clon;
	}

	@Override
	public String toString() {
		return encapsulado.toString();
	}
	
	
}
