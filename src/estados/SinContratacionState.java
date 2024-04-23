package estados;

import java.awt.Toolkit;

import javax.swing.JOptionPane;

import excepciones.ContratacionException;
import excepciones.DomicilioEIdentificacionException;
import excepciones.ServicioInexistenteException;
import modelo.Contratacion;
import modelo.Domicilio;
import modelo.Factura;
import modelo.IAbonado;

public class SinContratacionState extends State{

	public SinContratacionState(IAbonado abonado) {
		super(abonado);
	}

	public void pagarFactura()
	{
		Toolkit.getDefaultToolkit().beep();
		JOptionPane.showMessageDialog(null,"No se puede pagar factura, estado SinContratacion");
	}
	
	@Override
	public void eliminarContratacion(Contratacion contratacion) {
		Toolkit.getDefaultToolkit().beep();
		JOptionPane.showMessageDialog(null,"No se puede eliminar contratacion, estado SinContratacion");
	}

}
