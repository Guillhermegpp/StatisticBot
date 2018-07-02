package fatec;

import java.util.List;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ReplyKeyboardRemove;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;

public class View implements Observer {

	TelegramBot bot = TelegramBotAdapter.build("604870961:AAG-o7ialcabIkQd3rM4EJfv3FF6AcybFk0");
	// Object that receives messages
	GetUpdatesResponse updatesResponse;
	// Object that send responses
	SendResponse sendResponse;
	// Object that manage chat actions like "typing action"
	BaseResponse baseResponse;

	int queuesIndex = 0;

	Controller controllerSearch; // Strategy Pattern -- connection View -> Controller

	boolean searchBehaviour = false;
	boolean denovo = false;
	int i = -1;
	int op = 0;
	private Model model;

	public View(Model model) {
		this.model = model;
	}

	public void setControllerSearch(Controller controllerSearch) { // Strategy Pattern
		this.controllerSearch = controllerSearch;
	}

	public void receiveUsersMessages() throws Exception {
		// Lista para as mensagens
		String[] listUp = new String[4];

		// infinity loop
		while (true) {

			// taking the Queue of Messages
			updatesResponse = bot.execute(new GetUpdates().limit(100).offset(queuesIndex));

			// Queue of messages
			List<Update> updates = updatesResponse.updates();
			//updates.clear();
			for (Update update : updates) {

				// updating queue's index
				queuesIndex = update.updateId() + 1;
			}
			
			// taking each message in the Queue
			for (Update update : updates) {

				// updating queue's index
				queuesIndex = update.updateId() + 1;

				if (this.searchBehaviour == true) {
					String resp = update.message().text();
					setControllerSearch(new ControllerBinomial(model, this));
					if (resp.equals("X = x")) {
						// X = x
						op = 1;

					} else if (resp.equals("X <= x")) {
						// X <= x
						op = 2;

					} else if (resp.equals("X >= x")) {
						// X >= x
						op = 3;

					}
					this.callController(listUp, update, op);
					sendResponse = bot.execute(new SendMessage(update.message().chat().id(),
							"Options: ").replyMarkup(new ReplyKeyboardMarkup(new String[] {"Binomial","History"})
									.oneTimeKeyboard(false).resizeKeyboard(true).selective(true)));
				}
				else if (update.message().text().equals("/start")) {
					sendResponse = bot.execute(new SendMessage(update.message().chat().id(),
							"Hello "+update.message().chat().firstName()+
						 ", Here you can make statistical calculations to discover the binomial distribution ")
							.replyMarkup(new ReplyKeyboardMarkup(new String[] { "Binomial", "History"})
									.oneTimeKeyboard(false).resizeKeyboard(true).selective(true)));
					
					denovo = false;
				} 
				else if (update.message().text().equals("Binomial")) {
					sendResponse = bot.execute(new SendMessage(update.message().chat().id(),
							"Total number of events: ").replyMarkup(new ReplyKeyboardRemove()));
					i = 0;
					//denovo = false;
				
				}else if (update.message().text().equals("History")) {
					
					this.callController(update);
					sendResponse = bot.execute(new SendMessage(update.message().chat().id(),
							"Options: ").replyMarkup(new ReplyKeyboardMarkup(new String[] {"Binomial","History"})
									.oneTimeKeyboard(false).resizeKeyboard(true).selective(true)));
					i = -1;
					//denovo = false;
				
				
			
				}else if (i == 0) {
					sendResponse = bot.execute(new SendMessage(update.message().chat().id(),
							"Event Occurrence Number: "));
					listUp[i] = update.message().text();
					i++;
				} else if (i == 1) {
					sendResponse = bot
							.execute(new SendMessage(update.message().chat().id(), "Probability of success: (0 - 1 or you can use fraction)"));
					listUp[i] = update.message().text();
					i++;
				} else if (i == 2) {
					sendResponse = bot
							.execute(new SendMessage(update.message().chat().id(), "Probability of failure: (0 - 1 or you can use fraction)"));
					listUp[i] = update.message().text();
					i++;
				} else if (i == 3) {
					sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Options: ")
							.replyMarkup(new ReplyKeyboardMarkup(new String[] { "X = x", "X <= x", "X >= x" })
									.oneTimeKeyboard(false).resizeKeyboard(true).selective(true)));
					listUp[i] = update.message().text();
					i++;
					this.searchBehaviour = true;

				}

			}

		}

	}

	private void callController(String[] listUp, Update update, int op) throws Exception {
		this.controllerSearch.search(listUp, update, op);

	}
	private void callController(Update update) throws Exception {
		this.controllerSearch.search(update);

	}

	public void update(long chatId, String resposta) {
		sendResponse = bot.execute(new SendMessage(chatId, resposta).replyMarkup(new ReplyKeyboardRemove()));
		this.searchBehaviour = false;
		this.i = 0;
		this.op = 0;
		denovo = true;
	}

	public void sendTypingMessage(Update update) {
		baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));

	}

}
