package br.com.fiap.modelo;

/** Classe com as transações que podem ser efetuadas
 * @author Jhonatan Colina- 
 * {@link #ABERTURA_CONTA}
 * {@link #MODIFICACAO_CONTA}
 * {@link #TRANSFERENCIA}
 * {@link #EMPRESTIMO}
 * {@link #ADICIONANDO_DEPENDENTE}
 * {@link #SAQUE}
 * {@link #TAXA_SAQUE}
 * {@link #TAXA_EXTRATO}
 * {@link #TAXA_EMPRESTIMO}
 * {@link #TAXA_EMPRESTIMO_DEVOLUCAO}
 * {@link #DEPOSITO}
 * @version 1.0
 */
public enum TipoTransacao {

	ABERTURA_CONTA("Abertura da conta"),
	MODIFICACAO_CONTA("Modificação da conta"),
	MODIFICACAO_CONTADEPENDENTE("Modificação do Dependente"),
	TRANSFERENCIA("Transferencia na conta"),
	EMPRESTIMO("Empréstimo"),
	ADICIONANDO_DEPENDENTE("Adicionando Dependentes"),
	SAQUE("Saque"),
	TAXA_SAQUE("Taxa saque"),
	TAXA_EXTRATO("Taxa extrato"),
	TAXA_EMPRESTIMO("Taxa empréstimo"),
	TAXA_EMPRESTIMO_DEVOLUCAO("Devolução taxa de empréstimo"),
	DEPOSITO("Depósito em conta");
	
	/**
	 * Descricao do tipo da transação
	 */
	private String descricao;

	/**Construtor da Classe
	 * 
	 * @param descricao
	 */
	TipoTransacao(String descricao) {
		this.descricao = descricao;		
	}
	
	public String descricao() {
		return descricao;
	}
}
