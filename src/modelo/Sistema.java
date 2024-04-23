package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import excepciones.AbonadoException;
import excepciones.DomicilioEIdentificacionException;
import util.Util;
import vista.VentanaAfip;

/**
 * @author Federico,Gaston,Tobias <br>
 *         Clase principal, representa el sistema de servicios que contiene
 *         todas las instrucciones, para realizar cualquier tipo de operacion
 *         debera trabajrse con esta clase y esta se encargara de realizar too
 *         tipo de tareas y de la comunicacion
 */
public class Sistema implements Serializable{

	private static Sistema instance;
	private EPT ept= new EPT();
	private ArrayList<IAbonado> abonados = new ArrayList<IAbonado>();
	private boolean recursoCompartido=false;

	private Sistema() {

	}

	/**
	 * Constructor de la clase, se utiliza patron Singleton porque solo puede haber
	 * una instancia del sistema
	 * 
	 * @return
	 */
	public static Sistema getInstance() {
		if (instance == null)
		{
			instance=new Sistema();
		}
		return instance;
	}
	
	/**
	 * Metodo que crea a un abonado, y luego directamente lo añade al sistema junto
	 * a su factura que sera actualizada cada vez que el abonado realice un
	 * movimiento
	 * 
	 * @param nombre : parametro de tipo String que sera l nombre del abonado
	 * @param dni    : parametro de tipo String que sera el dni del abonado
	 * @param tipo   : parametro de tipo String que sera el tipo de persona del
	 *               abonado
	 * @param pago   : parametro de tipo String que sera la forma de pago del
	 *               abonado<br>
	 *               <b>Pre:</b>El tipo de persona debe ser o Fisica o Juridica
	 *               unicamente. En cuanto al pago debe ser: Trajeta, Cheque o
	 *               efectivo<br>
	 *               <b>Post:</b>El resultadp sera la insercion del abonado al
	 *               sistema y la creacion de su factura
	 */
	public void agregarAbonado(String nombre, String dni, String tipo, String pago)
	{
		
		IAbonado abonado = AbonadoFactory.factory(nombre, dni, tipo, pago);
		comenzarAAgregarAbonado();
		Util.espera(6000);//simula tiempo de ingresar abonado
		this.abonados.add(abonado);
		terminaDeIngresarseAbonado();
	}
	
	public synchronized void comenzarAAgregarAbonado()
	{
		JOptionPane.showMessageDialog(null,"Dando de alta abonado, agregando al sistema (esto puede tardar unos segundos)");
		recursoCompartido=true;
		notifyAll();
	}
	
	public synchronized void terminaDeIngresarseAbonado()
	{
		this.recursoCompartido=false;
		notifyAll();
	}
	
	public void pagarFactura(IAbonado abonado)
	{
		abonado.pagarFactura();
	}
	
	public void incrementarMes()
	{
		ept.incrementarMes();
	}

	public void setRecursoCompartido(boolean recursoCompartido) {
		this.recursoCompartido = recursoCompartido;
	}

	public EPT getEpt() {
		return ept;
	}

	public void setEpt(EPT ept) {
		this.ept = ept;
	}

	/**
	 * Metodo que se encargara de agregar celulares a una contratacion
	 * 
	 * @param nombre    : parametro de tipo String que representara el nombre del
	 *                  abonado que desea añadir celulares
	 * @param domicilio : parametro de tipo String que representara el domicilio al
	 *                  cual esta ligada una contratacion y se le quiere agregar
	 *                  celulares
	 * @param cant      : cantidad de celulares a agregar <br>
	 *                  Este metodo en caso de producirse algun error capta las
	 *                  excepciones y muestra el mensaje por pantalla
	 */
	public void agregaCelular(Contratacion contratacion, int cantidad) {
		contratacion.agregarCelular(cantidad);
	}

	/**
	 * Metodo que se encargara de listar todas las facturas del sistema
	 */
	public void listarTodasFacturasDelSistema() {
		Iterator<IAbonado> it = this.getAbonados().iterator();
		while (it.hasNext()) {
			System.out.println(it.next().toString());
		}
	}

	/**
	 * Metodo que elimina una contratacion
	 * 
	 * @param nombre    : parametro de tipo String que representa el abonado al cual
	 *                  se le desea eliminar una contratacion
	 * @param direccion : parametro de tipo String que representa el domicilio del
	 *                  abonado al cual se le desea desvincular la contratacion <br>
	 *                  Si el nombre o el domicilio del abonado no es correcto se
	 *                  notificara
	 */
	public void eliminarContratacion(IAbonado abonado, Contratacion contratacion) {
			abonado.eliminarContratacion(contratacion);
	}

	public synchronized void esperaParaComenzarVisitaAfip()
	{
		while (recursoCompartido)
		{
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		recursoCompartido=true;
		notifyAll();
	}
	
	public ArrayList<Factura> visitaAfip(JButton boton)
	{
		ArrayList<Factura> respuesta= new ArrayList<Factura>();
		if (recursoCompartido)
			JOptionPane.showMessageDialog(null,"AFIP hara su visita al terminar de ingresar abonado en curso");
		esperaParaComenzarVisitaAfip();
		VentanaAfip afip=new VentanaAfip(boton);
		Iterator<IAbonado> it= abonados.iterator();
		while (it.hasNext())
		{
			Iterator<Factura> it2= it.next().getFacturas().iterator();
			while (it2.hasNext())
			{
				Factura aux=null;
				try {
					aux= (Factura) it2.next().clone();
					respuesta.add(aux);
					afip.getArea().append(aux.toString());
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
			}
		}
		if (respuesta.size()==0)
			afip.getArea().append("NO HAY FACTURAS EN EL SISTEMA");
		Util.esperaFija(12000);
		afip.setVisible(false);
		terminaVisitaAfip();
		return respuesta;
	}
	
	public synchronized void terminaVisitaAfip()
	{
		recursoCompartido=false;
		notifyAll();
	}
	
	/**
	 * Metodo que muestra la factura de un abonado especifico
	 * 
	 * @param nombre : parametro de tipo String que sera el nombre del abonado del
	 *               cual se desea mostrar su factura <br>
	 *               Si el nombre del abonado no esta en el sistema se mostrara el
	 *               error
	 */
	public void mostrarFacturaDe(String nombre) {
		IAbonado factura;
		try {
			factura = this.buscaAbonado(nombre);
			System.out.println(factura);
		} catch (AbonadoException e) {
			System.out.println(e.getMessage() + ", no se puede mostrar una factura inexistente");
		}
	}

	/**
	 * Metodo que se encargara de agregar telefonos a una contratacion
	 * 
	 * @param nombre    : parametro de tipo String que representara el nombre del
	 *                  abonado que desea añadir telefonos
	 * @param domicilio : parametro de tipo String que representara el domicilio al
	 *                  cual esta ligada una contratacion y se le quiere agregar
	 *                  telefonos
	 * @param cant      : cantidad de telefonos a agregar <br>
	 *                  Este metodo en caso de producirse algun error capta las
	 *                  excepciones y muestra el mensaje por pantalla
	 */
	public void agregaTelefono(Contratacion contratacion,int cantidad) { // DOCUMENTAR
		contratacion.agregarTelefono(cantidad);
	}

	/**
	 * Metodo que se encargara de agregar TvCable a una contratacion
	 * 
	 * @param nombre    : parametro de tipo String que representara el nombre del
	 *                  abonado que desea añadir TvCable
	 * @param domicilio : parametro de tipo String que representara el domicilio al
	 *                  cual esta ligada una contratacion y se le quiere agregar
	 *                  TvCable
	 * @param cant      : cantidad de TvCable a agregar <br>
	 *                  Este metodo en caso de producirse algun error capta las
	 *                  excepciones y muestra el mensaje por pantalla
	 */
	public void agregaTvCable(Contratacion contratacion, int cantidad) { // DOCUMENTAR
		contratacion.agregarTvCable(cantidad);
	}

	public void eliminarAbonado(IAbonado abonado)
	{
		if (abonados.size()>0)
			abonados.remove(abonado);
		else
			JOptionPane.showMessageDialog(null,"No hay abonados en el sistema para eliminar");
	}
	
	public ArrayList<IAbonado> getAbonados() {
		return abonados;
	}

	/**
	 * Metodo que agrega un domicilio a un abonado
	 * 
	 * @param nombre    : parametro de tipo String que representa el nombre del
	 *                  abonado
	 * @param direccion : parametro de tipo String que representa la direccion del
	 *                  domicilio a crear <br>
	 *                  Si no se encuentra un abonado en el sistema con el nombre
	 *                  pasado por parametro se notificara
	 */
	public void agregaDomicilio(IAbonado abonado, String direccion) { // DOCUMENTAR
		Domicilio domicilio = new Domicilio(direccion);
			abonado.setDomicilios(domicilio);
	}

	/**
	 * Metodo que se encarga de realizar la contratacion de abonado
	 * 
	 * @param nombre         : parametro de tipo String que representa el nombre del
	 *                       abonado
	 * @param direccion      : parametro de tipo String que representa el domicilio
	 *                       al cual pertenecera la contratacion
	 * @param tipo           : parametro de tipo String que representara el tipo de
	 *                       contratacion a realizar por el abonado
	 * @param identificacion : parametro de tipo entero que representara el numero
	 *                       de identificaion de la contratacion a realizar <br>
	 *                       Si se produce algun error en el proceso el metodo
	 *                       atrapara las exepciones y notificara el error
	 */
	public void realizarContratacion(IAbonado abonado,Domicilio domicilio, int id, Contratacion contratacion) {
		try {
			abonado.realizarContratacion(contratacion,domicilio,id);
		} catch (DomicilioEIdentificacionException e) {
			if (e.isElProblemaDomicilio())
				JOptionPane.showMessageDialog(null,"El domicilio requerido ya tiene contratacion, no se puede agregar otra a este");
			else
				JOptionPane.showMessageDialog(null,"Ya hay una contratacion con el numero de identificacion ingresado");
		}
	}

	/**
	 * Metodo que clonara a una factura especifica
	 * 
	 * @param nombre : parametro de tipo String que representa el nombre del abonado
	 *               al cual se le desea duplicar su factura
	 * @return el resultado sera una duplicacion de la factura del abonado que se
	 *         desee <br>
	 *         En caso de no poder ser clonada, se notificara por pantalla
	 */
	public IAbonado clonarFactura(String nombre) {
		IAbonado clonada = null, aux = null;
		try {
			aux = buscaAbonado(nombre);
			clonada = (IAbonado) aux.clone();
		} catch (AbonadoException e) {
			System.out.println(e.getMessage() + " para clonar su factura");
		} catch (CloneNotSupportedException e) {
			System.out.println("La factura de " + nombre + " no puede ser clonada\n");
		}
		return clonada;
	}

	public void setInstance(Sistema instance)
	{
		this.instance=instance;
	}

	/**
	 * Metodo que busca a un abonado en el sistema por su nombre
	 * 
	 * @param nombre : parametro de tipo String que representa el nombre del abonado
	 *               a buscar
	 * @return el retorno es el abonado con dicho nombre
	 * @throws AbonadoException : se lanza en el caso de que el sistema no tenga un
	 *                          abonado con el nombre pasado por parametro
	 */
	private IAbonado buscaAbonado(String nombre) throws AbonadoException {
		IAbonado aux = null;
		int i = 0;
		while ((i < abonados.size()) && !(nombre.equals(abonados.get(i).getNombre())))
			i++;
		if ((i < abonados.size()) && (nombre.equals(abonados.get(i).getNombre())))
			aux = abonados.get(i);
		else
			throw new AbonadoException();
		return aux;
	}

}