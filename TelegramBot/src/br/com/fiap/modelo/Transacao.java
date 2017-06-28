package br.com.fiap.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
/**Classe para salvar as transações efetivadas durante a utilização da aplicação.
 * 
 * @author Jhonatan Colina
 * @see TipoTransacao
 * @version 1.0
 *
 */
public class Transacao implements Serializable {
	private static final long serialVersionUID = 7419293686881383697L;
	/**
	 * Data da transação efetuada
	 */
	private LocalDateTime dataHora;
	/**
	 * Classe TipoTransacao Enum para salvar nesta classe
	 */
	private TipoTransacao tipo;
	/**
	 * Saldo atual da conta no momento da transação
	 */
	private double saldoConta;
	/**
	 * Valor efetivo da tansação efetuada
	 */
	private double valorTransacao;

	/**Construtor da classe
	 * 
	 * @param dataHora Data da transação efetuada
	 * @param tipo Classe TipoTransacao Enum para salvar nesta classe
	 * @param valorTransacao Valor efetivo da tansação efetuada
	 * @param saldoConta Saldo atual da conta no momento da transação
	 */
	public Transacao(LocalDateTime dataHora, TipoTransacao tipo, double valorTransacao, double saldoConta) {
		this.dataHora = dataHora;
		this.tipo = tipo;
		this.valorTransacao = valorTransacao;
		this.saldoConta = saldoConta;		
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public TipoTransacao getTipo() {
		return tipo;
	}

	public double getSaldo() {
		return saldoConta;
	}

	public double getValor() {
		return valorTransacao;
	}

	@Override
	public String toString() {
		return "Transacao [dataHora=" + dataHora + ", tipo=" + tipo.descricao() + ", saldoConta=" + saldoConta
				+ ", valorTransacao=" + valorTransacao + "]";
	}
}
