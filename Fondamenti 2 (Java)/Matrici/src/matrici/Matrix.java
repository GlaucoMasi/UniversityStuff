package matrici;

public class Matrix {
	double[][] array;
	
	private Matrix(int rows, int cols) {
		array = new double[rows][cols];
	}
	
	public Matrix(double[][] values) {
		this(values.length, values[0].length);
		for(int i = 0; i < getRows(); i++) for(int j = 0; j < getCols(); j++) array[i][j] = values[i][j];
	}
	
	private void setValue(int row, int col, double value) {
		if(row >= 0 && col >= 0 && row < getRows() && col < getCols()) array[row][col] = value;
	}
	
	public double getValue(int row, int col) {
		if(row < 0 || col < 0 || row >= getRows() || col >= getCols()) return Double.NaN;
		return array[row][col];
	}
	
	public Matrix extractSubMatrix(int startRow, int startCol, int rowCount, int colCount) {
		if(startRow < 0 || startCol < 0 || startRow+rowCount > getRows() || startCol+colCount > getCols()) return null;
		Matrix ans = new Matrix(rowCount, colCount);
		for(int i = 0; i < rowCount; i++) for(int j = 0; j < rowCount; j++) ans.setValue(i, j, this.getValue(startRow+i, startCol+j));
		return ans;
	}
	
	public Matrix extractMinor(int row, int col) {
		if(!this.isSquared()) return null;
		Matrix ans = new Matrix(getRows()-1, getCols()-1);
		for(int i = 0; i < getRows()-1; i++) for(int j = 0; j < getCols()-1; j++) ans.setValue(i, j, this.getValue(i >= row ? i+1 : i, j >= col ? j+1 : j));
		return ans;
	}
	
	private double calcDet() {
		if(getRows() == 1) return getValue(0, 0);
		double ans = 0;
		for(int i = 0; i < getCols(); i++) ans += (i%2 == 1 ? -1 : 1)*getValue(0, i)*extractMinor(0, i).det();
		return ans;
	}
	
	public int getCols() {
		return array[0].length;
	}
	
	public int getRows() {
		return array.length;
	}
	
	public boolean isSquared() {
		return getCols() == getRows();
	}
	
	public double det() {
		return isSquared() ? calcDet() : Double.NaN;
	}
	
	public Matrix sum(Matrix m) {
		if(m.getRows() != this.getRows() || m.getCols() != this.getCols()) return null;
		Matrix ans = new Matrix(getRows(), getCols());
		for(int i = 0; i < getRows(); i++) for(int j = 0; j < getCols(); j++) ans.setValue(i, j, this.getValue(i, j)+m.getValue(i, j));
		return ans;
	}
	
	public Matrix mul(Matrix m) {
		if(m.getRows() != this.getCols()) return null;
		Matrix ans = new Matrix(getRows(), m.getCols());
		for(int i = 0; i < getRows(); i++) for(int j = 0; j < m.getCols(); j++) {
			double temp = 0;
			for(int k = 0; k < getCols(); k++) temp += this.getValue(i, k)*m.getValue(k, j);
			ans.setValue(i, j, temp);
		}
		return ans;		
	}
	
	public String toString() {
		return java.util.Arrays.deepToString(array);
	}
	
}
