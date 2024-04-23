package modelo;

public interface IFactura {

	public String getAbonado();

	public double getCosto();

	public void setCosto(double costo);

	public double getCostoConModificacion();

	public void setCostoConModificacion(double costoConModificacion);

	public void setAbonado(String abonado);
	
	public Object clone() throws CloneNotSupportedException;

	public String toString();
}
