package fatec;

import java.text.DecimalFormat;

public class Binomial {
	public String result;
	int n, k;
	double p, q;
	
	long chatId;

	public void setChatId(long chatId) {
		this.chatId = chatId;
	}

	public Binomial(int a, int b, String c, String d) throws Exception {
		Molde m = new Molde();
		n = a;
		k = b;
		p = m.transforma(c);
		q = m.transforma(d);

	}

	public double getBinomial(int ka) {
		
		Molde m = new Molde();
		double P;
		P = (m.fat(n) / (m.fat(ka) * m.fat(n - ka))) * Math.pow(p, ka) * Math.pow(q, (n - ka));
		System.out.printf("P(X = %d) = %.4f ou %.2f", ka, P, P * 100);
		System.out.println("%");
		
		return P;

	}

	public long getChatId() {
		return chatId;
	}
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
