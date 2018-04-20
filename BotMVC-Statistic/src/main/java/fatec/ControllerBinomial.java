package fatec;

import com.pengrad.telegrambot.model.Update;

public class ControllerBinomial implements Controller {

	private Model model;
	private View view;

	public ControllerBinomial(Model model, View view) {
		this.model = model; // connection Controller -> Model
		this.view = view; // connection Controller -> View
	}

	@Override
	public void search(String[] listM, Update update, int op) throws Exception {
		view.sendTypingMessage(update);
		model.serchBin(listM, update, op);

	}

}
