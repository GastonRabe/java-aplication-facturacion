package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;

import javax.swing.JOptionPane;

import modelo.AFIP;
import modelo.Contratacion;
import modelo.Domicilio;
import modelo.Factura;
import modelo.IAbonado;
import modelo.Internet100;
import modelo.Internet500;
import modelo.Sistema;
import observadores.ActualizadorDeDatos;
import observadores.GestorFacturacion;
import persistencia.IPersistencia;
import persistencia.PersistenciaBIN;
import vista.IVista;
import vista.VentanaSistema;

public class Controlador implements ActionListener {

	private IVista vista;
	private IPersistencia<Serializable> persistencia=new PersistenciaBIN();
	private Sistema sistema;
	
	public Controlador()
	{
		GestorFacturacion gestor= new GestorFacturacion();
		ActualizadorDeDatos actualizador= new ActualizadorDeDatos();
		
		/*sistema=Sistema.getInstance();
		this.vista=new VentanaSistema(this);
		refrescaListas();
		AFIP afip=new AFIP(vista);
		afip.start();*/
		
		try {
			persistencia.abrirInPut("sistema.bin");
			sistema=(Sistema) persistencia.leer();
			persistencia.cerrarInPut();
			sistema.setInstance(sistema);
			this.vista=new VentanaSistema(this);
			refrescaListas();
			AFIP afip=new AFIP(vista);
			afip.start();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,"Error al abrir datos del sistema en archivo");
		} catch (ClassNotFoundException e) {
			
		}
		gestor.agregarObservable(sistema.getEpt());
		actualizador.agregarObservable(sistema.getEpt());
	}
	
	public void refrescarListaAbonado()
	{
		vista.getListModelAbonado().clear();
		Iterator<IAbonado> it = sistema.getAbonados().iterator();
		while (it.hasNext())
		{	
			vista.getListModelAbonado().addElement(it.next());
		}
	}

	public void refrescarListaDomicilio()
	{
		vista.getListModelDomicilio().clear();
		Iterator<Domicilio> it = vista.getAbonado().getDomicilios().iterator();
		while (it.hasNext())
		{	
			vista.getListModelDomicilio().addElement(it.next());
		}
	}
	
	public void refrescarListaContratacion()
	{
		this.vista.getListModelContratacion().clear();
		Iterator<Contratacion> it = vista.getAbonado().getContrataciones().iterator();
		while (it.hasNext())
		{	
			vista.getListModelContratacion().addElement(it.next());
		}
	}
	
	public void refrescarListaFactura()
	{
		vista.getListModelFactura().clear();
		Iterator<Factura> it = vista.getAbonado().getFacturas().iterator();
		while (it.hasNext())
		{	
			vista.getListModelFactura().addElement(it.next());
		}
	}
	
	public void refrescaListas() {
		refrescarListaAbonado();
		if (vista.getAbonado()!=null)
		{	
			refrescarListaDomicilio();
			refrescarListaContratacion();
			refrescarListaFactura();
		}
		vista.repaint();
		vista.getTextAreaMes().setText(sistema.getEpt().getMes()+"");
		try {
			persistencia.abrirOutPut("sistema.bin");
			persistencia.escribir(sistema);
			persistencia.cerrarOutPut();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,"Error al guardar los datos del sistema en archivo");
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if (vista.getAbonado()!=null && sistema.getAbonados()!=null)
		{
			if (e.getActionCommand().equals("Pagar"))
				sistema.pagarFactura(vista.getAbonado());
			else if (e.getActionCommand().equals("Agregar Domicilio"))
			{
				String direccion=vista.getTextFieldDireccion().getText();
				if (sistema.getAbonados().size()>0)
					sistema.agregaDomicilio(vista.getAbonado(), direccion);
				else
					JOptionPane.showMessageDialog(null,"No hay abonados en el sistema para agregarle un domicilio");
			}
			else if (e.getActionCommand().equals("Desvincular"))
				sistema.eliminarAbonado(vista.getAbonado());
			else if (e.getActionCommand().equals("Añadir"))
				agregarServicioAContratacion();
			else if (e.getActionCommand().equals("Eliminar"))
			{
				Contratacion contratacion=vista.getListaContratacion().getSelectedValue();
				sistema.eliminarContratacion(vista.getAbonado(),contratacion);
			}
			else if (e.getActionCommand().equals("Agregar"))
				realizarContratacion();
		}
		if (e.getActionCommand().equals("Avanzar Mes"))
			sistema.incrementarMes();
		else
			if (e.getActionCommand().equals("Incorporar"))
				incoorporarAbonado();
			else if (vista.getAbonado()==null)
				JOptionPane.showMessageDialog(null,"Debe seleccionar un abonado de la lista para realizar operacion");
		refrescaListas();
	}
	
	public void realizarContratacion()
	{
		Domicilio domicilio=vista.getListaDomicilio().getSelectedValue();
		if (domicilio!=null)
		{
			Contratacion contratacion=null;
			int ident= Integer.parseInt(vista.getTextFieldNumeroIdentificacion().getText());
			if (vista.getRadioButtonInternet100().isSelected())
				contratacion= new Internet100(ident, domicilio);
			else
				if (vista.getRadioButtonInternet500().isSelected())
					contratacion=new Internet500(ident, domicilio);
			sistema.realizarContratacion(vista.getAbonado(),domicilio,ident,contratacion);
		}
		else
			JOptionPane.showMessageDialog(null,"Debe seleccionar un domicilio para añadirle una contratacion");
	}
	
	public void agregarServicioAContratacion()
	{
		Contratacion contratacion=vista.getListaContratacion().getSelectedValue();
		String cant=null;
		if (contratacion!=null)
		{
			cant=vista.getTextFieldCantidadServicios().getText();
			if (!cant.equals(""))
			{	
				int cantidad=Integer.parseInt(cant);
				if (cantidad>0)
				{
					if (vista.getRadioButtonCelular().isSelected())
						sistema.agregaCelular(contratacion, cantidad);
					else
						if (vista.getRadioButtonTvCable().isSelected())
							sistema.agregaTvCable(contratacion, cantidad);
						else
							if (vista.getRadioButtonTelefono().isSelected())
								sistema.agregaTelefono(contratacion, cantidad);	
							else
								JOptionPane.showMessageDialog(null,"Debe seleccionar un servicio (Celular, Telefono o TvCable)");
				}
				else
					JOptionPane.showMessageDialog(null,"La cantidad debe ser mayor a 0 obligatoriamente");
			}
			else
				JOptionPane.showMessageDialog(null,"Debe ingresar cantidad de servicios a añadir");
		}
		else
			JOptionPane.showMessageDialog(null,"Debe seleccionar una contratacion para añadirle un servicio");
	}
	
	public void incoorporarAbonado()
	{
		String nombre=vista.getTextFieldNombre().getText();
		String dni=vista.getTextFieldDNI().getText();
		String tipo=null;
		String pago=null;
		if (vista.getRadioButtonFisica().isSelected())
			tipo="Fisica";
		else
			if (vista.getRadioButtonJuridica().isSelected())
				tipo="Juridica";
		if (vista.getRadioButtonTarjeta().isSelected())
			pago="Tarjeta";
		else if (vista.getRadioButtonEfectivo().isSelected())
			pago="Efectivo";
		else
			if (vista.getRadioButtonCheque().isSelected())
				pago="Cheque";
		if (!nombre.equals("") && !dni.equals("") && tipo!=null && pago!=null)
			sistema.agregarAbonado(nombre, dni, tipo, pago);
		else
			JOptionPane.showMessageDialog(null,"Debe completar los datos para incoorporar un aboando al sistema");
	}
}
