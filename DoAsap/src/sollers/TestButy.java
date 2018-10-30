package sollers;

public class TestButy {
	
	
	public static void main(String[] args) {
		String S = "LLRLRLRLRLRR";
		// TODO Auto-generated method stub
		char[] A = S.toCharArray();
		int nL=0;
		int nP=0;
		int x = 0;
		for(char el: A)	{
			if(el=='L') nL++;
			else nP++;
			if (nL==nP) x++;
		}
	System.out.println(x);
	}

}
