package modelo;

public class DecoratorPagoEfectivo extends DecoratorPago{

	public DecoratorPagoEfectivo(IAbonado encapsulado) {
		super(encapsulado);
	}

	@Override
	public double realizarDescuentoOIncremento(double monto) {
		return encapsulado.realizarDescuentoOIncremento(monto);
	}

}
