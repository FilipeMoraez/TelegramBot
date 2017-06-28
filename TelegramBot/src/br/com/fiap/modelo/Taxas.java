package br.com.fiap.modelo;

/** Classe com as taxas que podem ser usadas
 * @author Jhonatan Colina
 * {@link #SAQUE}
 * {@link #EXTRATO}
 * {@link #EMPRESTIMO}
 * @version 1.0
 */
public enum Taxas {

	SAQUE(new Double("-2.50")),
	EXTRATO(new Double("-1.00")), 
	EMPRESTIMO(new Double("-15.00"));
	
	/**
	 * Valor da taxa
	 */
	private double valor;

	Taxas(double valor) {
		this.valor = valor;		
	}
	
	public double getValor() {
		return valor;
	}
}
