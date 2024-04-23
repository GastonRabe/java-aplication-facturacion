package modelo;

public class DecoratorPagoCheque extends DecoratorPago{

	public DecoratorPagoCheque(IAbonado encapsulado) {
		super(encapsulado);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double realizarDescuentoOIncremento(double monto) {
		return encapsulado.realizarDescuentoOIncremento(monto);
	}

}
