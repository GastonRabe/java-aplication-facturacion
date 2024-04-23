package estados;

import excepciones.DomicilioEIdentificacionException;
import modelo.Contratacion;
import modelo.Domicilio;

public interface IState {

	public void realizarContratacion(Contratacion contratacion,Domicilio domicilio, int id) throws DomicilioEIdentificacionException;
	
	public void eliminarContratacion(Contratacion contratacion);
	
	public void pagarFactura();
}
