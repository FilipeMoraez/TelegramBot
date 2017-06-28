package br.com.fiap.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author Jhonatan Colina
 * @version 1.0
 */
public class Emprestimo implements Serializable {

	private static final long serialVersionUID = -2373598535575498004L;

	/**
	 * Valor maximo do emprestimo
	 */
	private static final int valorMaximoEmprestimo = 40;
	/**
	 * Valor do juros ao mes
	 */
	private static final double jurosAoMes = 0.05d;
	/**
	 * Prazo escolhido pelo cliente em meses
	 */
	private int prazo;
	/**
	 * Valor do emprestimo escolhido pelo cliente
	 */
	private double valor;
	/**
	 * Valor dos juros a ser pago no mes
	 */
	private double valorJurosAoMes;
	/**
	 * Valor a ser pago no mes
	 */
	private double valorAoMes;
	/**
	 * Valor total pago no mes, sendo a soma do ValorJurosAoMes+valorAoMes
	 */
	private double valorTotalPorMes;
	/**
	 * Valor total do pagamento do emprestimo
	 */
	private double valorTotal;

	/**Construtor da classe
	 * 
	 * @param valor - valor do emprestimo escolhido pelo cliente
	 * @param prazo - Prazo escolhido pelo cliente em meses
	 */
	public Emprestimo(double valor, int prazo) 
	{
		this.valor = valor;
		this.prazo = prazo;		
	}
	
	public int getPrazo()
	{
		return prazo;
	}

	public void setPrazo(int prazo)
	{
		this.prazo = prazo;
	}

	public double getValor()
	{
		return valor;
	}

	public void setValor(double valor)
	{
		this.valor = valor;
	}

	public double getValorJurosAoMes()
	{
		return valorJurosAoMes;
	}

	public void setValorJurosAoMes(double valorJurosAoMes)
	{
		this.valorJurosAoMes = valorJurosAoMes;
	}

	public double getValorAoMes()
	{
		return valorAoMes;
	}

	public void setValorAoMes(double valorAoMes)
	{
		this.valorAoMes = valorAoMes;
	}

	public double getValorTotalPorMes()
	{
		return valorTotalPorMes;
	}

	public void setValorTotalPorMes(double valorTotalPorMes)
	{
		this.valorTotalPorMes = valorTotalPorMes;
	}

	public double getValorTotal()
	{
		return valorTotal;
	}

	public void setValorTotal(double valorTotal)
	{
		this.valorTotal = valorTotal;
	}
	
	/**
	 * 
	 * @param conta - Classe conta passada como parametro
	 * @return true se o valor nao ultrapassar 40x o saldo da conta
	 * 				 realizando todas as contas do emprestimo.
	 */
	public boolean calcularEmprestimo(Conta conta) 
	{
		if(this.valor <= (conta.getSaldoConta()*valorMaximoEmprestimo))
		{
			valorAoMes = valor/prazo; // sem juros
			valorJurosAoMes =  valor*jurosAoMes; // valor ao mes com juros
			valorTotalPorMes = valorAoMes+valorJurosAoMes; // valor total com juros ao mes
			valorTotal = valorTotalPorMes*prazo; // valor total

			return true;
		}
		else
		{
			return false;
		}
	}
	
	@Override
	public String toString()
	{
		return "Prazo pedido para o pagamento: " + prazo + " meses\nValor do emprestimo: R$ " + valor
				+ "\nValor dos juros ao mês: R$ " + valorJurosAoMes + "\nValor a ser pago no mês: R$ " + valorTotalPorMes 
				+ "\nValor total do pagamento: R$ "	+ valorTotal;
	}
	
}
