package libreriastatica;

public class MatriceLib {
	public static double[][] sommaMatrici(double[][] a, double[][] b){
		double[][] ans = new double[a.length][a[0].length];
		for(int i = 0; i < a.length; i++) for(int j = 0; j < a[0].length; j++) ans[i][j] = a[i][j]+b[i][j];
		return ans;
	}
	
	public static double[][] prodottoMatrici(double[][] a, double[][] b){
		double[][] ans = new double[a.length][b[0].length];
		for(int i = 0; i < a.length; i++) for(int j = 0; j < b[0].length; j++) for(int k = 0; k < a[0].length; k++) ans[i][j] += a[i][k]*b[k][j];
		return ans;		
	}
	
	public static void stampaMatrice(double[][] a) {
		System.out.println(java.util.Arrays.deepToString(a));
	}
}
