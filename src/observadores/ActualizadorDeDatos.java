package observadores;

import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import estados.ConContratacionState;
import estados.MorosoState;
import estados.SinContratacionState;
import modelo.EPT;
import modelo.IAbonado;
import modelo.Sistema;

public class ActualizadorDeDatos implements Observer{

	protected EPT observado=null;

	public void agregarObservable(EPT ept)
    {
		ept.addObserver(this);
		this.observado=ept;;
    }

    public void borrarObservable(EPT ept)
    {
    	ept.deleteObserver(this);
    	this.observado=ept;
    }
	
	@Override
	public void update(Observable o, Object arg)
	{
		if (this.observado==o)
		{
			Iterator<IAbonado> it=Sistema.getInstance().getAbonados().iterator();
			while (it.hasNext())
			{
				IAbonado aux= it.next();
				if (aux.getFacturas().size()>=2)
					aux.cambiarEstado("Moroso");
				else if (aux.getFacturas().size()<=1 && aux.getContrataciones().size()>0)
					aux.cambiarEstado("ConContratacion");
			}
			
		} else
		    throw new IllegalArgumentException();
	}

}
