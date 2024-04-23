package estados;

import java.io.Serializable;

import javax.swing.JOptionPane;

import excepciones.DomicilioEIdentificacionException;
import modelo.Contratacion;
import modelo.Domicilio;
import modelo.IAbonado;

public abstract class State implements Cloneable,Serializable,IState{

	IAbonado abonado;
	
	public State(IAbonado abonado)
	{
		this.abonado=abonado;
	}
	
	public void realizarContratacion(Contratacion contratacion,Domicilio domicilio, int id) throws DomicilioEIdentificacionException
	{
		if (abonado.revisarContrataciones(domicilio, id))
		{
			abonado.setContrataciones(contratacion);
			abonado.cambiarEstado("ConContratacion");
		}
	}
	
	public void eliminarContratacion(Contratacion contratacion) {
		if (contratacion!=null)
		{	
			Domicilio domicilio=contratacion.getDomicilio();
			int i = 0;
			while ((i < abonado.getContrataciones().size()) && !(domicilio.equals(abonado.getContrataciones().get(i).getDomicilio())))
				i++;
			if ((i < abonado.getContrataciones().size()) && (domicilio.equals(abonado.getContrataciones().get(i).getDomicilio()))) {
				this.abonado.getContrataciones().remove(i);
			}
			if (abonado.getContrataciones().size()==0)
				abonado.cambiarEstado("SinContratacion");
		}
		else
		{
			JOptionPane.showMessageDialog(null,"Debe seleccionar una contratacion para eliminar");
		}
	}
	
	public void pagarFactura()
	{
		if (abonado.getFacturas().size()>0)
			abonado.getFacturas().remove(0);
		else
			JOptionPane.showMessageDialog(null,"Abonado no tiene facturas que pagar");
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
