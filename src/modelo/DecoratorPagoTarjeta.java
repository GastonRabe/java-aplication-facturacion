package modelo;

public class DecoratorPagoTarjeta extends DecoratorPago{

	public DecoratorPagoTarjeta(IAbonado encapsulado) {
		super(encapsulado);
	}

	@Override
	public double realizarDescuentoOIncremento(double monto) {
		return encapsulado.realizarDescuentoOIncremento(monto);
	}

}
