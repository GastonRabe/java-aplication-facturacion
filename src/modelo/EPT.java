package modelo;

import java.io.Serializable;
import java.util.Observable;

public class EPT extends Observable implements Serializable{

	private int mes=1;
	
	public EPT() {
		super();
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public void incrementarMes()
	{
		this.setChanged();
	    this.notifyObservers();
		if (this.mes==12)
			this.mes=1;
		else
			this.mes+=1;
	}
	
}
