package fatec;

public class Binomial {
	public String result;
	int n, k;
	double p, q;
	Molde m = new Molde();

	public Binomial(int a, int b, String c, String d) throws Exception {
		n = a;
		k = b;
		p = m.transforma(c);
		q = m.transforma(d);

	}

	public double getBinomial(int ka) {

		double P;
		P = (m.fat(n) / (m.fat(ka) * m.fat(n - ka))) * Math.pow(p, ka) * Math.pow(q, (n - ka));
		System.out.printf("P(X = %d) = %.4f ou %.2f", ka, P, P * 100);
		System.out.println("%");
		return P;

	}
}
