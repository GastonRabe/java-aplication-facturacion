package modelo;

import java.util.ArrayList;

public interface IColeccionFacturas {

	public ArrayList<Factura> getFacturas();

	public void agregarFactura(Factura factura);
	
}
