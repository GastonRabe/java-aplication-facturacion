package estados;

import excepciones.ContratacionException;
import excepciones.DomicilioEIdentificacionException;
import excepciones.ServicioInexistenteException;
import modelo.Contratacion;
import modelo.Domicilio;
import modelo.IAbonado;
import modelo.Internet100;
import modelo.Internet500;

public class ConContratacionState extends State {

	public ConContratacionState(IAbonado abonado) {
		super(abonado);
	}

}
