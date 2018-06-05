package fatec;

import com.pengrad.telegrambot.model.Update;

public class ControllerHistoric implements Controller {

	private Model model;
	private View view;

	public ControllerHistoric(Model model, View view) {
		this.model = model;
		this.view = view;
	}
	@Override
	public void search(Update update) throws Exception {
		view.sendTypingMessage(update);
		model.getBinomial(update);

	}
	@Override
	public void search(String[] listM, Update update, int op) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
