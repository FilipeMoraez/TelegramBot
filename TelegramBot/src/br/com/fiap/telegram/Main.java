package br.com.fiap.telegram;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;

import br.com.fiap.acoes.Acoes;
import br.com.fiap.acoes.Telegram;
import br.com.fiap.modelo.Conta;

/**
*
* @author Jhonatan Colina
* @see 	 	Telegram
* @see 		Acoes
* @version 1.0
*/
public class Main 
{
	/**
	 * Lista com as contas criadas
	 */
	public static List<Conta> contas = new ArrayList<>();
	/**
	 * Lista com os bloqueios de entrada de mensagem (usado quando o chat aguarda parametro de comando)
	 */
	public static Collection<Long> bloqueioEntrada = new ArrayList<>();
	
	public static void main(String[] args) 
	{
		/**
		 * Instancia da classe Telegram com a chave de uso
		 */
		Telegram tel = new Telegram("314103874:AAHMpW2eOad4j4Y8RzpJyjLwZhO1FSQZ664");
		
		tel.setFila(0);
		while (true)
		{
			//obtem as mensagens pendentes
			tel.setUpdatesResponse(tel.getBot().execute(new GetUpdates().limit(100).offset(tel.getFila())));
			//fila de mensagens
			List<Update> atualizacoes = tel.getUpdatesResponse().updates();
			//leitura de cada mensagem
			for (Update atualizacao : atualizacoes) 
			{
				try
				{			
					// se nao existir bloqueio de entrada de novas mensagens
					if(!bloqueioEntrada.stream().anyMatch(u -> u.equals(atualizacao.message().chat().id())))
					{
						//atualização da fila
						tel.setFila(atualizacao.updateId()+1);
						System.out.println("Mensagem de entrada de "+atualizacao.message().from().firstName()+" "+atualizacao.message().from().lastName()+": "+atualizacao.message().text());
						// cria objeto de mensagem
						Acoes msg = new Acoes(atualizacao.message().chat().id(),
								                  atualizacao.message().text(),
								                  atualizacao.message().from().firstName()+" "+atualizacao.message().from().lastName(),tel);
						// Inicia Thread com a classe Acoes
						new Thread(msg).start();
					}
				}
				catch (NullPointerException e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}
}