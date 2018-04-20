package fatec;
import com.pengrad.telegrambot.model.Update;

public interface Controller {	

	public void search(String[] listM,Update update,int op) throws Exception;

}
