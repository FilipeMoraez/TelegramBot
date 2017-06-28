package br.com.fiap.modelo;

import java.io.Serializable;

/**Classe para criacao do objeto Usuario, para implementacao da classe Conta
 * @author Jhonatan Colina
 * @see Conta
 * @version 1.0
 */
public class Usuario implements Serializable
{
	private static final long serialVersionUID = 1L;
	/**
	 * Nome completo do Usuario
	 */
	private String nome;
	/**
	 * CPF correspondente ao Usuario
	 */
	private String cpf;
	/**
	 * Endereço do Usuario
	 */
	private String endereco;

	/**Construtor da classe Usuario
	 * 
	 * @param nome Nome completo do Usuario
	 * @param cpf CPF correspondente ao Usuario
	 * @param endereco Endereço do Usuario
	 */
	public Usuario(String nome, String cpf, String endereco) 
	{
		this.nome = nome;
		this.cpf = cpf;
		this.endereco = endereco;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getCpf()
	{
		return cpf;
	}

	public void setCpf(String cpf)
	{
		this.cpf = cpf;
	}

	public String getEndereco()
	{
		return endereco;
	}

	public void setEndereco(String endereco)
	{
		this.endereco = endereco;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "\nnome: "+nome+"\nCPF: "+cpf+"\nEndereço: "+endereco;
	}
	
	
}
