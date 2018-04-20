package fatec;

import java.util.LinkedList;
import java.util.List;

import com.pengrad.telegrambot.model.Update;

public class Model implements Subject {

	private List<Observer> observers = new LinkedList<Observer>();

	private static Model uniqueInstance;

	private Model() {
	}

	public static Model getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new Model();
		}
		return uniqueInstance;
	}

	public void registerObserver(Observer observer) {
		observers.add(observer);
	}

	public void notifyObservers(long chatId, String str) {
		for (Observer observer : observers) {
			observer.update(chatId, str);
		}
	}

	public static double somabin(Binomial cn, int ka) {
		if (ka < 0) {
			return 0;
		} else {
			double soma = cn.getBinomial(ka);
			int kol = ka - 1;
			if (kol-- >= 0)
				System.out.printf("+ ");
			return soma + somabin(cn, --ka);
		}
	}

	public void serchBin(String[] str, Update update, int op) throws Exception {
		double p = 0;
		String BINOMIAL = null;
		Binomial cn = new Binomial(Integer.parseInt(str[0]), Integer.parseInt(str[1]), str[2], str[3]);
		int x = Integer.parseInt(str[1]);
		if (op == 1) {
			p = cn.getBinomial(x);
			BINOMIAL = ("P(X = "+str[1]+") = " + p + " ou " + p * 100 + "%");
		} else if (op == 2) {
			p = somabin(cn, x);
			BINOMIAL = ("P(X <= "+str[1]+") = " + p + " ou " + p * 100 + "%");
		} else if (op == 3) {
			p = 1 - somabin(cn, x);
			BINOMIAL = ("P(X >= "+str[1]+") = " + p + " ou " + p * 100 + "%");
		}

		if (BINOMIAL != null) {
			this.notifyObservers(update.message().chat().id(), BINOMIAL);
		} else {
			this.notifyObservers(update.message().chat().id(), "OPS!!! Deu algum erro, acredita?");
		}

	}

	public void searchPoisson() {
		// metodo para 2 bimestre
	}

}