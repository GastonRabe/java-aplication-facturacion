package modelo;

import java.util.ArrayList;

import javax.swing.JFrame;

import util.Util;
import vista.IVista;
import vista.VentanaSistema;

public class AFIP extends Thread{

	private Sistema sistema;
	private ArrayList<Factura> facturas;
	private IVista ventana=null;
	
	public AFIP(IVista ventana) {
		this.sistema=Sistema.getInstance();
		this.ventana=ventana;
	}

	@Override
	public void run() {
		while (ventana.isEnabled())
		{
			Util.esperaFija(30000);
			facturas=sistema.visitaAfip(ventana.getBotonIncorporar());
		}
		
	}
	
}
