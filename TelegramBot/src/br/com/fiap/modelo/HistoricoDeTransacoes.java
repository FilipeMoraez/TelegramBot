package br.com.fiap.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**Classe que armazena uma lista com o historico de transações da conta
 * 
 * @author Jhonatan Colina
 * @see Conta
 * @version 1.0
 */
public class HistoricoDeTransacoes implements Serializable
{

	private static final long serialVersionUID = -8431879377848991033L;
	/**
	 * Lista de transacoes da conta
	 */
	private List<Transacao> transacoes = new ArrayList<>();
	
	public HistoricoDeTransacoes adicionar(TipoTransacao tipo, double valor, double saldo) {
		transacoes.add(new Transacao(LocalDateTime.now(), tipo, valor, saldo));
		return this;
	} 
	
	public List<Transacao> getTransacoes() {
		return Collections.unmodifiableList(transacoes);
	}

}
