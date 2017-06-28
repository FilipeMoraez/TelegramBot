package br.com.fiap.acoes;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendAudio;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;

/**
*
* @author Jhonatan Colina
* @version 1.0
*/
public class Telegram 
{
	/**
	 * Criacao do objeto bot com as informacoes de acesso à API
	 */
	private TelegramBot bot;
	/**
	 * Objeto responsavel por receber as mensagens
	 */
	private GetUpdatesResponse updatesResponse;
	/**
	 * Objeto responsavel por gerenciar o envio de respostas
	 */
	private SendResponse sendResponse;
	/**
	 * Objeto responsavel por gerenciar o envio de acoes do chat
	 */
	private BaseResponse baseResponse;
	/**
	 * Fila de espera de mensagens do bot
	 */
	private int fila;
	
	public int getFila()
	{
		return fila;
	}

	public void setFila(int fila)
	{
		this.fila = fila;
	}

	/**
	 * Construtor da Classe
	 * @param key Chave de acesso a API do Telegram
	 */
	public Telegram(String key) 
	{
		super();
		this.bot = TelegramBotAdapter.build(key);
	}
	
	public TelegramBot getBot() {
		return bot;
	}
	
	public void setBot(String key) {
		this.bot = TelegramBotAdapter.build(key);
	}
	
	public GetUpdatesResponse getUpdatesResponse() {
		return updatesResponse;
	}
	
	public void setUpdatesResponse(GetUpdatesResponse updatesResponse) {
		this.updatesResponse = updatesResponse;
	}
	
	public SendResponse getSendResponse() {
		return sendResponse;
	}
	
	public void setSendResponse(SendResponse sendResponse) {
		this.sendResponse = sendResponse;
	}
	
	public BaseResponse getBaseResponse() {
		return baseResponse;
	}
	
	public void setBaseResponse(BaseResponse baseResponse) {
		this.baseResponse = baseResponse;
	}
	
	
}
