package br.com.fiap.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Jhonatan Colina
 * @see Usuario
 * @see Emprestimo
 * @version 1.0
 */
public class Conta implements Serializable
{
	private static final long serialVersionUID = 1167002478304976851L;
	
	/**
	 * Numero da conta criada(gerado a partir do ID do chat)
	 */
	private long numeroConta;
	/**
	 * Data/hora da abertura da conta
	 */
	private LocalDateTime aberturaConta;
	/**
	 * Data/hora da ultima modificação da conta
	 */
	private LocalDateTime ultimaModificacao;
	/**
	 * Classe usuario armazenando o nome/cpf/endereco do cliente
	 */
	private Usuario titularConta;
	/**
	 * Lista de dependentes utilizando a classe usuario
	 */
	private List<Usuario> depedenteConta = new ArrayList<>();
	/**
	 * Classe HistoricoDeTransacoes armazenando todas as transações efetivadas
	 */
	private HistoricoDeTransacoes historico = new HistoricoDeTransacoes();
	/**
	 * Classe emprestimo
	 */
	private Emprestimo emprestimo;
	/**
	 * Armazena o saldo em conta
	 */
	private double saldoConta;
	
	/**Contrutor da classe
	 * 
	 * @param numeroConta Numero da conta criada(gerado a partir do ID do chat)
	 * @param aberturaConta Data/hora da abertura da conta
	 * @param ultimaModificacao Data/hora da ultima modificação da conta
	 * @param titularConta Classe usuario armazenando o nome/cpf/endereco do cliente
	 * @param saldoConta Armazena o saldo em conta
	 */
	public Conta(long numeroConta,LocalDateTime aberturaConta, LocalDateTime ultimaModificacao,
			Usuario titularConta,double saldoConta)
	{
		super();
		this.numeroConta = numeroConta;
		this.aberturaConta = aberturaConta;
		this.ultimaModificacao = ultimaModificacao;
		this.titularConta = titularConta;
		this.saldoConta = saldoConta;
	}
	
	public LocalDateTime getUltimaModificacao()
	{
		return ultimaModificacao;
	}

	public void setUltimaModificacao(LocalDateTime ultimaModificacao)
	{
		this.ultimaModificacao = ultimaModificacao;
	}
	
	public Usuario getTitularConta()
	{
		return titularConta;
	}

	public void setTitularConta(Usuario titularConta)
	{
		this.titularConta = titularConta;
	}
	
	public void setNomeTitularConta(String nome)
	{
		this.titularConta.setNome(nome);
	}
	
	public void setCPFTitularConta(String cpf)
	{
		this.titularConta.setCpf(cpf);
	}
	
	public void setEnderecoTitularConta(String endereco)
	{
		this.titularConta.setEndereco(endereco);;
	}
	
	public long getNumeroConta()
	{
		return numeroConta;
	}
	public void setNumeroConta(long numeroConta)
	{
		this.numeroConta = numeroConta;
	}
	public LocalDateTime getAberturaConta()
	{
		return aberturaConta;
	}
	public void setAberturaConta(LocalDateTime aberturaConta)
	{
		this.aberturaConta = aberturaConta;
	}
	public List<Usuario> getDepedenteConta()
	{
		return depedenteConta;
	}
	public void setDepedenteConta(List<Usuario> depedenteConta)
	{
		this.depedenteConta = depedenteConta;
	}
	public HistoricoDeTransacoes getHistorico()
	{
		return historico;
	}
	public void setHistorico(HistoricoDeTransacoes historico)
	{
		this.historico = historico;
	}
	public Emprestimo getEmprestimo()
	{
		return emprestimo;
	}
	public void setEmprestimo(Emprestimo emprestimo)
	{
		this.emprestimo = emprestimo;
	}
	public double getSaldoConta()
	{
		return saldoConta;
	}
	public void setSaldoConta(double saldoConta)
	{
		this.saldoConta = saldoConta;
	}
	
	/** Metodo que deposita o valor em conta
	 * 
	 * @param valor - valor do deposito
	 */
	public void deposita(double valor)
	{
		this.saldoConta += valor;
	}
	
	/** Metodo que saca valor da conta
	 * 
	 * @param valor - valor do saque
	 * @return true se valor do saque for menor que o saldo em conta + taxa do saque
	 */
	public boolean saque(double valor)
	{
		if((saldoConta-valor)+Taxas.SAQUE.getValor() < 0.0)
		{
			return false;
		}
		else
		{
			this.saldoConta -= valor-(Taxas.SAQUE.getValor());
			return true;
		}
	}
	
	/** Metodo verificador se existe saldo para taxa do extrato
	 * 
	 * @return true se o saldo em conta for maior que a taxa do extrato
	 */
	public boolean taxaExtrato()
	{
		if(saldoConta+(Taxas.EXTRATO.getValor()) < 0.0)
		{
			return false;
		}
		else
		{
			this.saldoConta += Taxas.EXTRATO.getValor();
			return true;
		}
	}
	
	/**Metodo para adicionar Dependente a conta
	 * 
	 * @param dependente - Classe usuario com os valores do dependente
	 */
	public void adicionaDependente(Usuario dependente)
	{
		depedenteConta.add(dependente);
	}
	
	/**Metodo que adiciona uma transação a conta
	 * 
	 * @param tipo - Tipo de transação
	 * @param valor - valor da transação
	 */
	public void Transacao(TipoTransacao tipo, double valor) 
	{
		historico.adicionar(tipo, valor, this.saldoConta);
	}
	
	/** Metodo que verifica se o saldo é maior que a taxa do emprestimo
	 * 
	 * @return true se taxa do emprestimo for menor que o saldo em conta
	 */
	public boolean taxaEmprestimo()
	{
		if(saldoConta+(Taxas.EMPRESTIMO.getValor()) < 0.0)
		{
			return false;
		}
		else
		{
			this.saldoConta += Taxas.EMPRESTIMO.getValor();
			return true;
		}
	}
	
	/**Metodo que efetiva o emprestimo
	 * 
	 * @param valor - valor do emprestimo
	 * @param prazo - prazo escolhido pelo cliente
	 * @see Emprestimo
	 * @return true se o emprestimo for efetivado
	 */
	public boolean Emprestimo(double valor, int prazo)
	{
		this.emprestimo = new Emprestimo(valor, prazo);
		if(this.emprestimo.calcularEmprestimo(this) == true)
		{
			this.saldoConta += valor;
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
		String dependentes="Sem dependentes!";
		if(depedenteConta.size() != 0)
		{
			dependentes = depedenteConta.toString();
		}
		
		return "Conta: "+ numeroConta+
				   "\nData da Abertura: "+aberturaConta.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))+
				   "\nUltima Modificação: "+ultimaModificacao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))+
				   "\nTitular da Conta: "+titularConta.getNome()+
				   "\nCPF do Titular: "+titularConta.getCpf()+
				   "\nEndereço do Titular: "+titularConta.getEndereco()+
				   "\n\nDependentes: "+dependentes+
				   "\n\nSaldo: R$ "+saldoConta;
	}

}
