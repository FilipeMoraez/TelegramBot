package br.com.fiap.acoes;

/** <h2>- Recebe mensagens do chat - </h2>
 * Classe utilizada pelo Main indiretamente pela Classe Acoes onde se e armazenada as mensagens dos usuarios
 * @author Jhonatan Colina
 * @see Acoes
 * @version 1.0
 */
public class Mensagens
{
	/**
	 * @param mensagem Mensagem recebida do usuario pelo chat
	 */
	protected String mensagem;
	/**
	 * ID do chat
	 */
	protected long id;
	/**
	 * Nome Completo do usuario cadastrado no Telegram
	 */
	protected String nomeUsuario;

	/**
	 * Construtor da Classe
	 * @param id ID do chat
	 * @param mensagem Mensagem recebida
	 * @param nomeUsuario Nome completo do usuario
	 */
	public Mensagens(long id,String mensagem, String nomeUsuario)
	{
		super();
		this.mensagem = mensagem;
		this.id = id;
		this.nomeUsuario = nomeUsuario;
	}
	
	public String getNomeUsuario()
	{
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario)
	{
		this.nomeUsuario = nomeUsuario;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getMensagem()
	{
		return mensagem;
	}

	public void setMensagem(String mensagem)
	{
		this.mensagem = mensagem;
	}
}
