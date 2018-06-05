package fatec;

public class Molde {
	public int fat(int nro) {
		if (nro <= 1)
			return 1;
		else
			return nro * fat(--nro);
	}
	public double transforma(String str) throws Exception {
	if(isNumber(str)){
		return Double.parseDouble(str);
	}
	else {
		String[] s = str.split("/");
		try{
			return Double.parseDouble(s[0])/Double.parseDouble(s[1]);
		}catch (Exception a){
			throw new Exception("");			
		}
	}
	}
	public boolean isNumber(String s) {
		try{
			Double.parseDouble(s);
			return true;
		}catch(Exception e) {
			return false;
		}
}
}
