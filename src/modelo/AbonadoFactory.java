package modelo;

/**
 * @author Federico,Gaston,Tobias <br>
 *         Clase que sirve para crear un abonado dependiendo de sus
 *         caracteristicas
 */
public class AbonadoFactory {

	/**
	 * Metodo que devuelve al abonado ya reado, dependiendo de las caracteristicas
	 * que se le pasen por parametro
	 * 
	 * @param nombre : parametro de tipo String que representara el nombre del
	 *               abonado
	 * @param dni    : parametro de tipo String que representara el DNI del abonado
	 * @param tipo   : parametro de tipo String que representara el tipo de abonado:
	 *               Persona Fisica o Juridica
	 * @param pago   : parametro de tipo String que representara el tipo de pago del
	 *               abonado: Efectivo, Cheque o Tarjeta
	 * @return el valor de retorno es el abonado creado para ingresar al sistema y
	 *         comenzar a operar <br>
	 *         <b>Pre:</b> El parametro tipo debe ser unicamente uno de los
	 *         siguientes: Fisica/Juridica, y por otro lado, el parametro pago debe
	 *         ser unicamente uno de los siguientes: Efectivo, Cheque o Tarjeta. En
	 *         caso de incumplir alguna elresultado sera un abonado nulo<br>
	 *         <b>Post:</b> De cumplir con las precondiciones, el resultado del
	 *         metodo sera la devolcuion de un abonado con las caracteristicas dadas
	 */
	public static IAbonado factory(String nombre, String dni, String tipo, String pago) {
		IAbonado respuesta = null;
		IAbonado encapsulado=null;
		
		if (tipo == "Fisica")
			encapsulado = new PersonaFisica(nombre, dni);
		else if (tipo == "Juridica")
			encapsulado = new PersonaJuridica(nombre, dni);

		if (encapsulado!=null)
		{
			if (pago == "Efectivo")
			{
				encapsulado.setPago("Efectivo");
				respuesta= new DecoratorPagoEfectivo(encapsulado);
			}
			else if (pago == "Tarjeta")
			{
				encapsulado.setPago("Tarjeta");
				respuesta= new DecoratorPagoTarjeta(encapsulado);
			}
			else if (pago == "Cheque")
			{
				encapsulado.setPago("Cheque");
				respuesta= new DecoratorPagoCheque(encapsulado);
			}
		}
		return respuesta;
	}

}
