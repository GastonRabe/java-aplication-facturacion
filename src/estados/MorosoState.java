package estados;

import java.awt.Toolkit;

import javax.swing.JOptionPane;

import excepciones.ContratacionException;
import excepciones.DomicilioEIdentificacionException;
import excepciones.ServicioInexistenteException;
import modelo.Contratacion;
import modelo.Domicilio;
import modelo.IAbonado;

public class MorosoState extends State {


	public MorosoState(IAbonado abonado) {
		super(abonado);
		JOptionPane.showMessageDialog(null,"Abonado "+abonado.getNombre()+" en estado Moroso durante el mes en curso por falta de pago, recargo del 30% en su proxima factura a pagar");
		double costo=abonado.getFacturas().get(0).getCosto();
		double costoModificado=abonado.getFacturas().get(0).getCostoConModificacion();
		abonado.getFacturas().get(0).setCosto(costo*1.3);
		abonado.getFacturas().get(0).setCostoConModificacion(costoModificado*1.3);
	}

	@Override
	public void realizarContratacion(Contratacion contratacion, Domicilio domicilio, int id)
	{
		Toolkit.getDefaultToolkit().beep();
		JOptionPane.showMessageDialog(null,"No se puede realizar contratacion, estado moroso hasta no deber dos facturas consecutivas");
		
	}

	@Override
	public void eliminarContratacion(Contratacion contratacion) {
		Toolkit.getDefaultToolkit().beep();
		JOptionPane.showMessageDialog(null,"No se puede eliminar contratacion, estado moroso");
	}

}
