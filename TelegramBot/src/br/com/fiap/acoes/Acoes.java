package br.com.fiap.acoes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendMessage;

import br.com.fiap.modelo.Conta;
import br.com.fiap.modelo.Taxas;
import br.com.fiap.modelo.TipoTransacao;
import br.com.fiap.modelo.Transacao;
import br.com.fiap.modelo.Usuario;
import br.com.fiap.telegram.Main;

/** <h2>- Realiza Acoes do Banco - </h2>
 * Classe Thread para N execuções sem travar a utilização por N usuarios
 * @author Jhonatan Colina
 * @version 1.0
 */
public class Acoes extends Mensagens implements Runnable
{
	/**
	 * Classe telegram passada por parametro
	 */
	Telegram tel;

	/**
	 * Lista com os comandos que podem ser executados
	 */
	private List<String> cmd = Arrays.asList("/start","/ajuda","/criarconta","/exibirdados",
			"/deposito","/saque","/criarcontaconjunta","/extrato",
			"/modificarconta","/lancamentos","/retiradas","/tarifaservicos",
			"/emprestimo","/saldodevedor");
	
	/**
	 * Metodo run verificando se o comando existe, caso não envia a mensagem com o erro mostrado, caso sim executa o comando
	 * {@link #verificaComando(String)}
	 * {@link #enviaMensagem(String)}
	 */
	public void run() 
	{
		if(!verificaComando(super.mensagem))
		{
			enviaMensagem("Opa, parece que você digitou um comando inválido 😕\n"
					+ "Que tal me mandar /ajuda para saber minhas funcionalidades? 😀");
		}
	}
	
	/**
	 * Constutor da Classe
	 * @param id ID da classe Mensagem
	 * @param mensagem Texto da mensagem recebida
	 * @param nomeUsuario Nome completo do usuario
	 * @param tel Classe Telegram
	 * @see Mensagens
	 * @see Telegram
	 */
	public Acoes(long id, String mensagem, String nomeUsuario,Telegram tel)
	{
		super(id, mensagem,nomeUsuario);
		this.tel = tel;
	}
	
	/**
	 * Metodo para enviar ação de texto para o bot
	 * @return SendChatAction
	 */
	public SendChatAction acaoTexto()
	{
		return new SendChatAction(super.id, ChatAction.typing.name());
	}
	
	/**
	 * Metodo que verifica se o comando existe na lista 
	 * {@link #cmd}
	 * @param mensagem - Parametro com a mensagem do usuario
	 * @return true se o comando existe, false caso contrario
	 */
	public boolean verificaComando(String mensagem)
	{
		// se achar o comando
		if(cmd.stream().anyMatch(u -> u.equals(mensagem)))
		{//executa ele
			executaComando(mensagem);
			return true;
		}
		else
		{//retorna falso
			return false;
		}
	}
	
	/**
	 * Metodo executador dos comandos possiveis
	 * @param cmd - Parametro com o comando a ser executado
	 */
	private void executaComando(String cmd)
	{
		switch (cmd)
		{
			case "/start":
				start();
				break;
			case "/ajuda":
				ajuda();
				break;
			case "/criarconta":
				criarConta();
				break;
			case "/modificarconta":
				modificarConta();
				break;
			case "/criarcontaconjunta":
				criarContaConjunta();
				break;
			case "/exibirdados":
				exibirDados();
				break;
			case "/deposito":
				deposita();
				break;
			case "/saque":
				saque();
				break;
			case "/extrato":
				solicitaExtrato();
				break;
			case "/lancamentos":
				lancamentos();
				break;
			case "/retiradas":
				retiradas();
				break;
			case "/tarifaservicos":
				tarifaServico();
				break;
			case "/emprestimo":
				emprestimo();
				break;
			case "/saldodevedor":
				saldoDevedor();
				break;
			default :
				break;
		}
	}
	
	/**
	 * Metodo que envia a mensagem de bem vindo ao usuário do chat
	 */
	private void start()
	{
		enviaMensagem("Ola "+this.nomeUsuario+"!\n"
				+ "Meu nome é Digit e eu sou o assistente pessoal do DigitalBank! 😀 \n"
				+ "Você pode saber todas as minhas funcionalidades apenas me enviando /ajuda "
				+ "Que tal tentar, estou a sua disposição!");
	}
	
	/**
	 * Metodo que envia a mensagem de ajuda ao usuário do chat
	 */
	private void ajuda()
	{
		enviaMensagem("Aqui esta o que eu posso fazer:\n"
				+ "1 - /criarconta - Cria uma nova conta no DigitalBank\n"
				+ "2 - /modificarconta - Modifica a sua conta no DigitalBank\n"
				+ "3 - /criarcontaconjunta - Inclusão de dependentes\n"
				+ "4 - /exibirdados - Mostra os dados cadastrados\n"
				+ "5 - /deposito - Depositar valor em sua conta\n"
				+ "6 - /saque - Saca valor de sua conta (Custo R$2,50)\n"
				+ "7 - /extrato - Solicita um extrato de sua conta (Custo R$ 1,00)\n"
				+ "8 - /emprestimo - Solicitar emprestimo (Custos adicionais,sera mostrado ao solicitar)\n"
				+ "9 - /saldodevedor - Exibição de saldo devedor do emprestimo e prazo de pagamento\n"
				+ "10 - /lancamentos - Exibição detalhada de lançamentos\n"
				+ "11 - /retiradas - Exibição detalhada de retiradas\n"
				+ "12 - /tarifaservicos - Exibição detalhada de tarifas.");
	}
	
	/**
	 * Metodo que cria a conta do usuário
	 */
	private void criarConta()
	{
		bloqueiaEntradaMensagem();
		if(!Main.contas.stream().anyMatch(u -> u.getNumeroConta() == this.id))
		{
			enviaMensagem("Vamos começar o processo de criação da sua nova conta:\n\n");
			enviaMensagem("Qual o seu nome completo: ");
			String nome = aguardaResposta();
			enviaMensagem("Digite seu CPF: ");
			String cpf = aguardaResposta();
			enviaMensagem("Por ultimo, digite seu endereço: ");
			String endereco = aguardaResposta();
			enviaMensagem("Criando conta...");
			Usuario user = new Usuario(nome,cpf,endereco);
			Conta conta = new Conta(this.id, LocalDateTime.now(),LocalDateTime.now(), user, 0.0);
			conta.Transacao(TipoTransacao.ABERTURA_CONTA, 0.0);
			Main.contas.add(conta);
			enviaMensagem("Conta criada com êxito.\nMe mande o comando /ajuda para ver o que eu posso fazer! 😀");
		}
		else
		{
			enviaMensagem("Você ja tem uma conta criada!\n"
					+ "Me escreva o comando /exibirdados para eu te retornar os dados cadastrados");
		}
		liberaEntradaMensagem();
	}
	
	/**
	 * Metodo que modifica a conta do usuario
	 */
	private void modificarConta()
	{
		bloqueiaEntradaMensagem();
		if(Main.contas.stream().anyMatch(u -> u.getNumeroConta() == this.id))
		{
			enviaMensagem(Main
					.contas
					.stream()
					.filter(u -> u.getNumeroConta() == this.id)
					.findFirst().get().toString());

			enviaMensagem("Me mande:\n"
					+ "1. Para mudar o nome do titular;\n"
					+ "2. Para mudar o CPF do titular;\n"
					+ "3. Para mudar o endereço do titular;\n"
					+ "4. Para mudar os dados dos dependentes;\n"
					+ "5. Para voltar;");

			String opcao = aguardaResposta();

			switch (opcao)
			{
				case "1":
					enviaMensagem("Ok, escreva o novo nome do titular");
					String novoNome = aguardaResposta();
					enviaMensagem("Trocando nome...");
					Main.contas.stream().filter(u -> u.getNumeroConta() == this.id).findFirst().get().setNomeTitularConta(novoNome);
					Main.contas.stream().filter(u -> u.getNumeroConta() == this.id).findFirst().get().Transacao(TipoTransacao.MODIFICACAO_CONTA, 0.0);
					enviaMensagem("Nome trocado com êxito! 😀");
					break;
				case "2":
					enviaMensagem("Ok, escreva o novo CPF do titular");
					String novoCpf = aguardaResposta();
					enviaMensagem("Trocando CPF...");
					Main.contas.stream().filter(u -> u.getNumeroConta() == this.id).findFirst().get().setCPFTitularConta(novoCpf);
					Main.contas.stream().filter(u -> u.getNumeroConta() == this.id).findFirst().get().Transacao(TipoTransacao.MODIFICACAO_CONTA, 0.0);
					enviaMensagem("CPF trocado com êxito! 😀");
					break;
				case "3":
					enviaMensagem("Ok, escreva o novo Endereço do titular");
					String novoEndereco = aguardaResposta();
					enviaMensagem("Trocando Endereço...");
					Main.contas.stream().filter(u -> u.getNumeroConta() == this.id).findFirst().get().setEnderecoTitularConta(novoEndereco);
					Main.contas.stream().filter(u -> u.getNumeroConta() == this.id).findFirst().get().Transacao(TipoTransacao.MODIFICACAO_CONTA, 0.0);
					enviaMensagem("Endereço trocado com êxito! 😀");
					break;
				case "4":
					int qtdDependente = Main.contas.stream()
					.filter(u -> u.getNumeroConta() == this.id)
					.findFirst().get().getDepedenteConta().size();
					if(qtdDependente > 0)
					{
						enviaMensagem("Ok, vou listar os dependentes de sua conta...");
						StringBuilder sb = new StringBuilder();	
						Main.contas.stream().filter(u -> u.getNumeroConta() == this.id)
						.findFirst().get().getDepedenteConta()
						.forEach(u -> sb.append("\nnome: "+u.getNome()+"\nCPF: "+u.getCpf()+"\nEndereço: "+u.getEndereco()+"\n"));
						enviaMensagem(sb.toString());
						enviaMensagem("Me mande o nome do dependente"
								+ " que você deseja mudar "
								+ "(exatamente como esta escrito acima).");
						String dependente = aguardaResposta();
						if(Main.contas.stream().filter(u -> u.getNumeroConta() == this.id)
								.findFirst().get().getDepedenteConta().stream().filter(u -> u.getNome().equals(dependente))
								.findFirst().isPresent())
						{
							enviaMensagem("Ok, Me mande:\n"
									+ "1. Para mudar o nome deste dependente;\n"
									+ "2. Para mudar o CPF deste dependente;\n"
									+ "3. Para mudar o endereço deste dependente;\n"
									+ "4. Para voltar;");

							String opcaoDep = aguardaResposta();

							switch (opcaoDep)
							{
								case "1" :
									enviaMensagem("Ok, escreva o novo nome deste dependente");
									String novoNomeDependente = aguardaResposta();
									enviaMensagem("Trocando nome...");
									Main.contas.stream().filter(u -> u.getNumeroConta() == this.id)
									.findFirst().get().getDepedenteConta().stream().filter(u -> u.getNome().equals(dependente))
									.findFirst().get().setNome(novoNomeDependente);
									Main.contas.stream().filter(u -> u.getNumeroConta() == this.id).findFirst().get().Transacao(TipoTransacao.MODIFICACAO_CONTADEPENDENTE, 0.0);
									enviaMensagem("Nome trocado com êxito! 😀");
									break;
								case "2" :
									enviaMensagem("Ok, escreva o novo CPF deste dependente");
									String novoCpfDependente = aguardaResposta();
									enviaMensagem("Trocando CPF...");
									Main.contas.stream().filter(u -> u.getNumeroConta() == this.id)
									.findFirst().get().getDepedenteConta().stream().filter(u -> u.getNome().equals(dependente))
									.findFirst().get().setCpf(novoCpfDependente);
									Main.contas.stream().filter(u -> u.getNumeroConta() == this.id).findFirst().get().Transacao(TipoTransacao.MODIFICACAO_CONTADEPENDENTE, 0.0);
									enviaMensagem("CPF trocado com êxito! 😀");
									break;
								case "3" :
									enviaMensagem("Ok, escreva o novo Endereço deste dependente");
									String novoEnderecoDependente = aguardaResposta();
									enviaMensagem("Trocando Endereço...");
									Main.contas.stream().filter(u -> u.getNumeroConta() == this.id)
									.findFirst().get().getDepedenteConta().stream().filter(u -> u.getNome().equals(dependente))
									.findFirst().get().setEndereco(novoEnderecoDependente);
									Main.contas.stream().filter(u -> u.getNumeroConta() == this.id).findFirst().get().Transacao(TipoTransacao.MODIFICACAO_CONTADEPENDENTE, 0.0);
									enviaMensagem("Endereço trocado com êxito! 😀");
									break;
								case "5":
									break;
								default :
									enviaMensagem("Escolha uma opção valida!\n"
											+ "Voltando ao menu inicial...");
									break;
							}
						}
						else
						{
							enviaMensagem("Dependente não encontrado! 😕\n"
									+ "Você digitou o nome correto?");
						}
					}
					else
					{
						enviaMensagem("Não ha dependentes em sua conta!");
					}
					break;
				case "5":
					enviaMensagem("Voltando ao menu inicial...");
					break;
				default :
					enviaMensagem("Escolha uma opção valida!\n"
							+ "Voltando ao menu inicial...");
					break;
			}
		}
		else
		{
			enviaMensagem("Você ainda nao tem nenhuma conta!\n"
					+ "Que tal criar uma? Me escreva o comando /criarconta para mais informações 😀");
		}
		liberaEntradaMensagem();
	}
	
	/*
	 * Metodo que cria a conta conjunta do usuário
	 */
	private void criarContaConjunta()
	{
		bloqueiaEntradaMensagem();
		if(Main.contas.stream().anyMatch(u -> u.getNumeroConta() == this.id))
		{
			enviaMensagem("Vamos começar o processo de criação de uma conta conjunta a conta "+this.id+":\n\n");
			enviaMensagem("Qual o nome completo do dependente: ");
			String nome = null;
			nome = aguardaResposta();
			enviaMensagem("Digite o cpf do dependente: ");
			String cpf = null;
			cpf = aguardaResposta();
			enviaMensagem("Por ultimo, digite o endereço do dependente: ");
			String endereco = null;
			endereco = aguardaResposta();

			enviaMensagem("Criando conta conjunta...");
			Usuario user = new Usuario(nome,cpf,endereco);
			Main.contas.stream().filter(u -> u.getNumeroConta() == this.id).findFirst().get().adicionaDependente(user);
			Main.contas.stream().filter(u -> u.getNumeroConta() == this.id).findFirst().get().Transacao(TipoTransacao.ADICIONANDO_DEPENDENTE, 0.0);
			Main.contas.stream().filter(u -> u.getNumeroConta() == this.id).findFirst().get().setUltimaModificacao(LocalDateTime.now());
			enviaMensagem("Conta conjunta criada com êxito.\nMe mande o comando /ajuda para ver o que eu posso fazer! 😀");
		}
		else
		{
			enviaMensagem("Você ainda nao tem nenhuma conta!\n"
					+ "Que tal criar uma? Me escreva o comando /criarconta para mais informações 😀");
		}
		liberaEntradaMensagem();
	}
	
	/**
	 * Metodo que exibe os dados da conta do usuario
	 */
	private void exibirDados()
	{
		if(Main.contas.stream().anyMatch(u -> u.getNumeroConta() == this.id))
		{
			//	enviaMensagem()));
			enviaMensagem("Aqui estão seus dados: \n\n"+
					Main.contas.stream().filter(u -> u.getNumeroConta() == this.id).findFirst().get().toString());
		}
		else
		{
			enviaMensagem("Você ainda nao tem nenhuma conta!\n"
					+ "Que tal criar uma? Me escreva o comando /criarconta para mais informações 😀");
		}
	}
	
	/**
	 * Metodo que deposita saldo em conta
	 */
	private void deposita()
	{
		bloqueiaEntradaMensagem();
		if(Main.contas.stream().anyMatch(u -> u.getNumeroConta() == this.id))
		{
			enviaMensagem("Escreva o quanto você deseja depositar: ");
			String valor = null;
			valor = aguardaResposta();
			double valorConvertido;
			try
			{
				valorConvertido = Double.parseDouble(valor);
				enviaMensagem("Depositando...");
				Main.contas.stream().filter(u -> u.getNumeroConta() == this.id).findFirst().get().deposita(valorConvertido);
				Main.contas.stream().filter(u -> u.getNumeroConta() == this.id).findFirst().get().Transacao(TipoTransacao.DEPOSITO, valorConvertido);
				Main.contas.stream().filter(u -> u.getNumeroConta() == this.id).findFirst().get().setUltimaModificacao(LocalDateTime.now());
				enviaMensagem("Valor Depositado com êxito! 😀");
			}
			catch (Exception e) {
				enviaMensagem("Valor informado não compativel com o formato (##.##)\nRefaça o precedimento com cautela...");
			}

		}
		else
		{
			enviaMensagem("Você ainda nao tem nenhuma conta!\n"
					+ "Que tal criar uma? Me escreva o comando /criarconta para mais informações 😀");
		}
		liberaEntradaMensagem();
	}
	
	/**
	 * Metodo que saca do saldo em conta
	 */
	private void saque()
	{
		bloqueiaEntradaMensagem();
		if(Main.contas.stream().anyMatch(u -> u.getNumeroConta() == this.id))
		{
			enviaMensagem("Escreva o quanto você deseja sacar (custo do serviço R$ 2.50): ");
			String valor = null;
			valor = aguardaResposta();
			double valorConvertido;
			try
			{
				valorConvertido = Double.parseDouble(valor);
				enviaMensagem("Sacando R$ "+valor+" (custo do serviço R$ 2.50)...");
				boolean resposta = Main.contas.stream().filter(u -> u.getNumeroConta() == this.id).findFirst().get().saque(valorConvertido);

				if(resposta)
				{
					Main.contas.stream().filter(u -> u.getNumeroConta() == this.id).findFirst().get().Transacao(TipoTransacao.SAQUE, valorConvertido);
					Main.contas.stream().filter(u -> u.getNumeroConta() == this.id).findFirst().get().Transacao(TipoTransacao.TAXA_SAQUE, Taxas.SAQUE.getValor());
					Main.contas.stream().filter(u -> u.getNumeroConta() == this.id).findFirst().get().setUltimaModificacao(LocalDateTime.now());
					enviaMensagem("Saque efetuado com êxito! 😀");
				}
				else
				{
					enviaMensagem("Impossivel sacar mais do que você tem em depósito! 😐");
				}
			}
			catch (Exception e) {
				enviaMensagem("Valor informado não compativel com o formato (##.##)\nRefaça o precedimento com cautela...");
			}

		}
		else
		{
			enviaMensagem("Você ainda nao tem nenhuma conta!\n"
					+ "Que tal criar uma? Me escreva o comando /criarconta para mais informações 😀");
		}
		liberaEntradaMensagem();
	}
	
	/**
	 * Metodo que exibe o extrato da conta
	 */
	private void solicitaExtrato()
	{
		if(Main.contas.stream().anyMatch(u -> u.getNumeroConta() == this.id))
		{
			StringBuilder sb = new StringBuilder();
			enviaMensagem("Criando Extrato... (Custo do serviço R$ 1.00)");
			if(Main.contas.stream().filter(u -> u.getNumeroConta() == this.id).findFirst().get().taxaExtrato())
			{
				Main.contas.stream().filter(u -> u.getNumeroConta() == this.id)
				.findFirst().get().getHistorico().getTransacoes().stream()
				.filter(u -> u.getTipo() != TipoTransacao.TAXA_SAQUE &&
				u.getTipo() != TipoTransacao.TAXA_EXTRATO &&
				u.getTipo() != TipoTransacao.TAXA_EMPRESTIMO && 
				u.getTipo() != TipoTransacao.MODIFICACAO_CONTA && 
				u.getTipo() != TipoTransacao.MODIFICACAO_CONTADEPENDENTE)
				.forEach(u -> sb.append("\nData: " + u.getDataHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) +
						"\nDescrição: " + u.getTipo().descricao() +
						"\nValor: R$ " + u.getValor()+"\n"));
				sb.append("Saldo Atual: R$ "+Main.contas.stream()
				.filter(u -> u.getNumeroConta() == this.id).findFirst().get().getSaldoConta()+"\n");
				Main.contas.stream().filter(u -> u.getNumeroConta() == this.id).findFirst().get().Transacao(TipoTransacao.TAXA_EXTRATO, Taxas.EXTRATO.getValor());
				enviaMensagem(sb.toString());
			}
			else
			{
				enviaMensagem("Você não tem saldo suficiente para realizar esta operação! 😕");
			}
		}
		else
		{
			enviaMensagem("Você ainda nao tem nenhuma conta!\n"
					+ "Que tal criar uma? Me escreva o comando /criarconta para mais informações 😀");
		}
	}
	
	/**
	 * Metodo que exibe todos os lançamentos detalhadamente da conta
	 */
	private void lancamentos()
	{
		if(Main.contas.stream().anyMatch(u -> u.getNumeroConta() == this.id))
		{
			StringBuilder sb = new StringBuilder();
			enviaMensagem("Exibindo lancamentos detalhado...");

			Main.contas.stream().filter(u -> u.getNumeroConta() == this.id)
			.findFirst().get().getHistorico().getTransacoes()
			.forEach(new Consumer<Transacao>()
			{
				double soma;
				@Override
				public void accept(Transacao t)
				{
					sb.append("\nData: " + t.getDataHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) +
							"\nDescrição: " + t.getTipo().descricao() +
							"\nValor: R$ " + t.getValor()+
							"\nSaldo: R$ " + t.getSaldo()+"\n");
					soma += t.getValor();
					sb.append("Soma: R$ "+soma+"\n");
				}
			});

			enviaMensagem(sb.toString());
		}
		else
		{
			enviaMensagem("Você ainda nao tem nenhuma conta!\n"
					+ "Que tal criar uma? Me escreva o comando /criarconta para mais informações 😀");
		}
	}
	
	/**
	 * Metodo que exibe todas as retidadas da conta
	 */
	private void retiradas()
	{
		if(Main.contas.stream().anyMatch(u -> u.getNumeroConta() == this.id))
		{
			StringBuilder sb = new StringBuilder();
			enviaMensagem("Exibindo retiradas...");

			Main.contas.stream().filter(u -> u.getNumeroConta() == this.id)
			.findFirst().get().getHistorico().getTransacoes()
			.stream().filter(u -> u.getTipo() == TipoTransacao.SAQUE)
			.forEach(new Consumer<Transacao>()
			{
				double soma;
				@Override
				public void accept(Transacao t)
				{
					sb.append("\nData: " + t.getDataHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) +
							"\nDescrição: " + t.getTipo().descricao() +
							"\nValor: R$ " + t.getValor()+"\n");
					soma += t.getValor();
					sb.append("Soma: R$ "+soma+"\n");
				}
			});

			enviaMensagem(sb.toString());
		}
		else
		{
			enviaMensagem("Você ainda nao tem nenhuma conta!\n"
					+ "Que tal criar uma? Me escreva o comando /criarconta para mais informações 😀");
		}
	}
	
	/**
	 * Metodo que exibe todas as tarifas de serviços que foram efetivadas na conta
	 */
	private void tarifaServico()
	{
		if(Main.contas.stream().anyMatch(u -> u.getNumeroConta() == this.id))
		{
			StringBuilder sb = new StringBuilder();
			enviaMensagem("Exibindo Tarifas de Serviço...");

			Main.contas.stream().filter(u -> u.getNumeroConta() == this.id)
			.findFirst().get().getHistorico().getTransacoes().stream()
			.filter(u -> u.getTipo() == TipoTransacao.TAXA_SAQUE ||
			u.getTipo() == TipoTransacao.TAXA_EXTRATO ||
			u.getTipo() == TipoTransacao.TAXA_EMPRESTIMO)
			.forEach(new Consumer<Transacao>()
			{
				double soma;
				@Override
				public void accept(Transacao t)
				{
					sb.append("\nData: " + t.getDataHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) +
							"\nDescrição: " + t.getTipo().descricao() +
							"\nValor: R$ " + t.getValor()+"\n");
					soma += t.getValor();
					sb.append("Soma: R$ "+soma+"\n");
				}
			});

			enviaMensagem(sb.toString());
		}
		else
		{
			enviaMensagem("Você ainda nao tem nenhuma conta!\n"
					+ "Que tal criar uma? Me escreva o comando /criarconta para mais informações 😀");
		}
	}
	
	/**
	 * Metodo que realiza emprestimo na conta
	 */
	private void emprestimo()
	{
		bloqueiaEntradaMensagem();	
		if(Main.contas.stream().anyMatch(u -> u.getNumeroConta() == this.id))
		{
			if(Main.contas.stream().filter(u -> u.getNumeroConta() == this.id)
					.findFirst().get().getEmprestimo() == null)
			{
				enviaMensagem("Ok...O custo deste serviço é de R$ 15.00 + 5% a.m\n"
						+ "Em quanto tempo você deseja pagar o empréstimo?\n"
						+ "1. Para um ano;\n"
						+ "2. Para dois anos;\n"
						+ "3. Para três anos;\n\n"
						+ "Lembrando que o valor maximo é de 40 vezes o saldo de sua conta.");
				String opcao = aguardaResposta();
				int prazo = 0;
				switch (opcao)
				{
					case "1":
						prazo = 12;
						break;
					case "2":
						prazo = 24;
						break;
					case "3":
						prazo = 36;
						break;
					default :
						enviaMensagem("Escolha uma opção valida!\n"
								+ "Voltando ao menu inicial...");
						prazo = 0;
						break;
				}

				if(prazo != 0)
				{
					enviaMensagem("Me escreva o valor do seu empréstimo:");
					String valor = aguardaResposta();
					double valorConvertido = 0.0;
					try
					{
						valorConvertido = Double.parseDouble(valor);

						if(Main.contas.stream().filter(u -> u.getNumeroConta() == this.id).findFirst().get().taxaEmprestimo())
						{
							if(Main.contas.stream().filter(u -> u.getNumeroConta() == this.id).findFirst().get().Emprestimo(valorConvertido, prazo))
							{
								Main.contas.stream().filter(u -> u.getNumeroConta() == this.id).findFirst().get().Transacao(TipoTransacao.EMPRESTIMO, valorConvertido);
								Main.contas.stream().filter(u -> u.getNumeroConta() == this.id).findFirst().get().Transacao(TipoTransacao.TAXA_EMPRESTIMO, Taxas.EMPRESTIMO.getValor());
								enviaMensagem("Emprestimo realizado com êxito!\n"
										+ "Pague as parcelas em dia para nao ter problemas com os juros!");
							}
							else
							{
								enviaMensagem("O emprestimo ultrapassa o maximo permitido de 40x o valor do saldo! 😕");
								Main.contas.stream().filter(u -> u.getNumeroConta() == this.id).findFirst().get().deposita((Taxas.EMPRESTIMO.getValor()*-1));
								Main.contas.stream().filter(u -> u.getNumeroConta() == this.id).findFirst().get().setEmprestimo(null);
							}
						}
						else
						{
							enviaMensagem("Você não tem saldo suficiente para realizar esta operação! 😕");
						}
					}
					catch (Exception e) 
					{
						enviaMensagem("Valor informado não compativel com o formato (##.##)\n"
								+ "Refaça o precedimento com cautela...");
					}
				}
			}
			else
			{
				enviaMensagem("Você ja tem um emprestimo em conta!\n"
						+ "Me escreva o comando /saldodevedor para verificar!");
			}
		}
		else
		{
			enviaMensagem("Você ainda nao tem nenhuma conta!\n"
					+ "Que tal criar uma? Me escreva o comando /criarconta para mais informações 😀");
		}
		liberaEntradaMensagem();
	}
	
	/**
	 * Metodo que exibe o saldo devedor a partir do emprestimo em conta
	 */
	private void saldoDevedor()
	{
		if(Main.contas.stream().anyMatch(u -> u.getNumeroConta() == this.id))
		{
			enviaMensagem("Ok... Vou procurar o seu saldo devedor");
			enviaMensagem(Main.contas.stream()
					.filter(u -> u.getNumeroConta() == this.id)
					.findFirst().get().getEmprestimo().toString());
		}
		else
		{
			enviaMensagem("Você ainda nao tem nenhuma conta!\n"
					+ "Que tal criar uma? Me escreva o comando /criarconta para mais informações 😀");
		}
	}
	
	/**
	 * Metodo que requisita uma entrada do usuário, o colocando na lista de bloqueio de novos comandos, 
	 * aguardando algum parametro para as outras funções. Enquanto for nulo, nao sai desse metodo
	 * @return String - parametro do usuario
	 */
	private String aguardaResposta()
	{
		String retorno = null;

		while (retorno == null)
		{
			//obtem as mensagens pendentes
			tel.setUpdatesResponse(tel.getBot().execute(new GetUpdates().limit(100).offset(tel.getFila())));
			//fila de mensagens
			List<Update> atualizacoes = tel.getUpdatesResponse().updates();
			//leitura de cada mensagem
			for (Update atualizacao : atualizacoes) 
			{
				//atualização da fila
				tel.setFila(atualizacao.updateId()+1);
				if(atualizacao.message().chat().id() == this.id)
				{
					System.out.println("Mensagem de resposta de "+this.nomeUsuario+": "+atualizacao.message().text());
					retorno = atualizacao.message().text();
				}
			}
		}
		return retorno;
	}
	
	/**
	 * Metodo que envia uma mensagem de texto ao usuario, 
	 * utilizado para a comunicação entre o bot e o usuario
	 * @param mensagem - mensagem a ser enviada pelo bot
	 */
	public void enviaMensagem(String mensagem)
	{
		tel.setBaseResponse(tel.getBot().execute(acaoTexto()));
		tel.setBaseResponse(tel.getBot().execute(new SendMessage(this.id, mensagem)));
	}
	
	/**
	 * Metodo que coloca o usuario na lista de bloqueio temporario de novos comandos, 
	 * utilizado quando se tem uam comunicação entre o bot e o usuario na entrada de novos parametros.
	 */
	private void bloqueiaEntradaMensagem()
	{
		Main.bloqueioEntrada.add(this.id);
	}
	
	/**
	 * Metodo que retira o usuario na lista de bloqueio temporario de novos comandos, 
	 * utilizado quando a comunicação entre o usuario e o bot é finalizada.
	 */
	private void liberaEntradaMensagem()
	{
		Main.bloqueioEntrada.removeIf(u -> u.equals(this.id));
	}
	
}
