package modelo;

import java.util.ArrayList;
import java.util.Iterator;

import estados.ConContratacionState;
import estados.SinContratacionState;
import estados.State;
import excepciones.ContratacionException;
import excepciones.DomicilioEIdentificacionException;
import excepciones.ServicioInexistenteException;

/**
 * @author Federico,Gaston,Tobias <br>
 *         Clase abstracta que representa un abonado del sistema
 */
public abstract class Abonado implements IAbonado,IColeccionFacturas {

	String nombre;
	String dni;
	ArrayList<Domicilio> domicilios = new ArrayList<Domicilio>();
	ArrayList<Contratacion> contrataciones = new ArrayList<Contratacion>();
	ArrayList<Factura> facturas= new ArrayList<Factura>();
	String pago;
	State estado;

	/**
	 * Constructor con dos parametros para setear, el nombre y el dni del abonado
	 * <br>
	 * 
	 * @param nombre : parametro de tipo String que representa el nombre del abonado
	 * @param dni    : parametro de tipo String que representa el dni del abonado
	 */
	public Abonado(String nombre, String dni) {
		super();
		this.nombre = nombre;
		this.dni = dni;
	}

	/**
	 * Getter del nombre de un abonado
	 * 
	 * @return el retorno es el nombre del abonado
	 */
	public String getNombre() {
		return nombre;
	}
	
	public void setPago(String pago) {
		this.pago = pago;
	}

	public State getEstado() {
		return estado;
	}
	
	/**
	 * Getter del DNI de un abonado
	 * 
	 * @return el retorno es el dni del abonado que lo implementa
	 */
	public String getDni() {
		return dni;
	}
	
	public ArrayList<Factura> getFacturas() {
		return facturas;
	}

	public void agregarFactura(Factura factura) {
		this.facturas.add(factura);
	}

	public ArrayList<Domicilio> getDomicilios() {
		return domicilios;
	}

	public Object clone() throws CloneNotSupportedException{
		return super.clone();
	}
	/**
	 * Metodo que añade un domicilio a la lista de domicilios de un abonado <br>
	 * 
	 * @param domicilio : parametro de tipo Domicilio que representa un nuevo
	 *                  domicilio de un abonado
	 */
	public void setDomicilios(Domicilio domicilio) {
		this.domicilios.add(domicilio);
	}

	public ArrayList<Contratacion> getContrataciones() {
		return contrataciones;
	}

	/**
	 * Metodo que añade una contratacion nueva a la lista de contrataciones de un
	 * abonado <br>
	 * 
	 * @param contratacion : parametro de tipo Contratacion que representa una nueva
	 *                     contratacion adquirida por un abonado
	 */
	public void setContrataciones(Contratacion contratacion) {
		this.contrataciones.add(contratacion);
	}

	/**
	 * Metodo que borra un domicilio especifico de la lista de domicilios de un
	 * abonado <br>
	 * 
	 * @param domicilio : parametro de tipo Domicilio que representa el domicilio a
	 *                  eliminar
	 */
	public void borroDomicilio(Domicilio domicilio) {
		this.domicilios.remove(domicilio);
	}

	/**
	 * Este metodo revisa que no haya contrataciones con el mismo numero de
	 * identificacion o con el mismo domicilio en el sistema <br>
	 * 
	 * @param domicilio      : parametro de tipo Domicilio que representa el
	 *                       domicilio que queremos inspeccionar que no tenga
	 *                       ninguna contratacion
	 * @param identificacion : parametro de tipo entero que representa el numero de
	 *                       identificacion de la contratacion que no puede ser
	 *                       repetido
	 * @return EL valor de retorno es verdadero si ya existe una contratacion con el
	 *         domicilio o con la identificaion pasadas por parametro, falso si no
	 *         existe aun <br>
	 *         <b>Pre:</b>El domicilio pasado por parametro debe ser distinto de
	 *         null <b>Post:</b>El resultado sera si ya existe una contratacion con
	 *         ese domicilio o ese numero de identificacion, o si no existe aun
	 * @throws DomicilioEIdentificacionException 
	 */
	public boolean revisarContrataciones(Domicilio domicilio, int identificacion) throws DomicilioEIdentificacionException {
		Iterator<IAbonado> iterador = Sistema.getInstance().getAbonados().iterator();
		while (iterador.hasNext()) {
			Iterator<Contratacion> iterador2 = iterador.next().getContrataciones().iterator();
			while (iterador2.hasNext()) {
				Contratacion aux = iterador2.next();
				if (aux.getDomicilio().equals(domicilio))
					throw new DomicilioEIdentificacionException(true);
				else if (aux.getIdentificacion() == identificacion) {
					throw new DomicilioEIdentificacionException(false);
				}
			}
		}
		return true;
	}

	/**
	 * Metodo que realiza una contratacion de un abonado para ser agregada al
	 * sistema
	 * 
	 * @param domicilio      : parametro de tipo Domicilio al cual le pertenecera la
	 *                       contratacion
	 * @param nombre         : parametro de tipo String que representa el tipo de
	 *                       contratacion que se llevara a cabo
	 * @param identificacion : parametro de tipo entero que sera el numero de
	 *                       identificacion de la futura contratacion
	 * @throws ServicioInexistenteException      : se lanza en caso de no existir el
	 *                                           servicio requerido
	 * @throws DomicilioEIdentificacionException : se lanza si ya existe una
	 *                                           contratacion con el domicilio o el
	 *                                           numero de identificacion pasados
	 *                                           como parametro
	 */
	public void realizarContratacion(Contratacion contratacion,Domicilio domicilio, int id) throws DomicilioEIdentificacionException{
		estado.realizarContratacion(contratacion,domicilio,id);
	}

	/**
	 * Metodo abstracto que se encargara de realizar el descuento o incremento del
	 * costo a pagar de un abonado, dependiendo de su tipo
	 * 
	 * @param monto : parametro de tipo double que representa el monto al que se le
	 *              debe descontar o aumentar el valor
	 * @return el retorno es el total a pagar descontado o aumentado
	 */
	public abstract double realizarDescuentoOIncremento(double monto);

	public double calcularTotal() {
		double total = 0;
		Iterator<Contratacion> it = this.getContrataciones().iterator();
		while (it.hasNext()) {
			total += it.next().calcularCosto();
		}
		return total;
	}

	/**
	 * Metodo que elimina una contratacion especifica, dependiendo del domicilio al
	 * que pertenece
	 * 
	 * @param domicilio : parametro de tipo Domicilio que representa el domicilio al
	 *                  cual se le quiere eliminar la contratacion, en caso de tener
	 *                  una
	 * @throws DomicilioEIdentificacionException : se lanza en caso de que el
	 *                                           domicilio pasado por parametro no
	 *                                           tengfa ninguna contratacion
	 *                                           relacionada con el
	 * @throws ContratacionException 
	 */
	public void eliminarContratacion(Contratacion contratacion) {
		this.estado.eliminarContratacion(contratacion);
	}

	public void pagarFactura()
	{
		this.estado.pagarFactura();
	}
	
	@Override
	public String toString() {
		String aux = nombre + ", DNI=" + dni+", ";
		aux += "Forma de pago: "+pago+"\n";
		return aux;
	}

}
