package sollers;

public class Test {
	
	static int[] A = {1, 6, 2, 3};
	
	public Test()	{


	}

	public static void main(String[] args) {
		
		System.out.println(A[1]);
		System.out.println("1 - "+ countPips(A, 6));
		System.out.println("max - "+ maxP(A));
		System.out.println("min - "+ minP(A));
	
		int n1 =  countPips(A,1);
		int n2 =  countPips(A,2);
		int n3 =  countPips(A,3);
		int n4 =  countPips(A,4);
		int n5 =  countPips(A,5);
		int n6 =  countPips(A,6);
		int p16 =n1+n6;
		int p25 =n2+n5;
		int p23 =n3+n4;
		
		int k16 = min(n1,n6)*2+(A.length-n1-n6)*1;
		int k25 = min(n2,n5)*2+(A.length-n2-n5)*1;
		int k34 = min(n3,n4)*2+(A.length-n3-n4)*1;
		int[] B = {k16, k25, k34};
		System.out.println(minP(B));
	}

	public static int countPips(int[] A, int p) {
        int n=0;
        for(int i =0; i<=A.length-1; i++)   {
            if(A[i]==p) {
                n++;
            }
        }
        return n;
    }
    public static int maxP(int[] A)  {
        int max = A[0];
        for(int i=0; i<=A.length-2; i++)    {
            if(A[i]<A[i+1]) max=A[i+1];
        }
        return max;
    }
    public static int min(int a, int b)	{
    	if (a<=b) return a;
    	else return b;
    }
    public static int minP(int[] A)  {
        int min = A[0];
        for(int i=0; i<=A.length-2; i++)    {
            if(A[i]>A[i+1]) min=A[i+1];
        }
        return min;
    }
    
    public int solution(int[] A) {
        return 3;
    }
}
