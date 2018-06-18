package fatec;

import java.util.ArrayList;
import java.util.List;


import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;
import com.pengrad.telegrambot.model.Update;

import fatec.Binomial;

public class ModelDAO {

	private static ModelDAO modelDAO;
	static ObjectContainer bancoProblemas;

	public static ModelDAO getInstance() {
		if (modelDAO == null) {
			modelDAO = new ModelDAO();
		}
		return modelDAO;
	}

	public static ObjectContainer connect() {
		if (bancoProblemas == null){
			//try{
			//	bancoProblemas = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "bd\\bancoProblemas.db4o");
			//}catch(Exception e){
				bancoProblemas = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "bd/bancoProblemas.db4o");
			//}
		}	
		return bancoProblemas;
	}

	public static boolean addHistoric(Binomial binomial) {
		bancoProblemas = connect();
		bancoProblemas.store(binomial);
		bancoProblemas.commit();
		return true;
	}

	public static List<Binomial> getBinomial(Update update) {
		long chatId = update.message().chat().id();
		bancoProblemas = connect();
		Query query = bancoProblemas.query();
		query.constrain(Binomial.class);
		ObjectSet<Binomial> allHistoric = query.execute();
		List<Binomial> userHistoric = new ArrayList<>();
		for (Binomial historic : allHistoric) {
			if (historic.getChatId() == chatId) {
				userHistoric.add(historic);
			}
		}
		if (userHistoric.isEmpty()) {
			userHistoric = null;
		}
		return userHistoric;
	}
}
