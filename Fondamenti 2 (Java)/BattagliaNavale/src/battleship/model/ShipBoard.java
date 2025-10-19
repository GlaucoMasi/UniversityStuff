package battleship.model;

public class ShipBoard {
	private ShipItem[][] board;
	private static final int DIM = 8;

	public ShipBoard(String eightLines) {
		board = new ShipItem[DIM][DIM];
		String[] lines = eightLines.split("\\r?\\n");
		for (int i = 0; i < DIM; i++) {
			setCellRow(i, lines[i]);
		}
	}

	public ShipBoard() {
		board = new ShipItem[DIM][DIM];
		for (int i = 0; i < DIM; i++) {
			for(int j = 0; j < DIM; j++) {
				this.setCell(i, j, ShipItem.EMPTY);
			}
		}
	}

	public void setCellRow(int row, String line) {
		String[] items = line.split("\\s");
		if (row < 0 || row >= DIM || items.length != DIM) {
			throw new IllegalArgumentException("Errore nei parametri");
		}
		for (int col = 0; col < DIM; col++) {
			board[row][col] = ShipItem.of(items[col].trim());
		}
	}

	public ShipItem getCell(int row, int col) {
		if (row < 0 || row >= DIM || col < 0 || col >= DIM) {
			throw new IllegalArgumentException("Errore nei parametri");
		}

		return this.board[row][col];
	}

	public int getSize() {
		return this.board.length;
	}

	public void clearCell(int row, int col) {
		this.setCell(row, col, ShipItem.EMPTY);
	}

	public void setCell(int row, int col, ShipItem item) {
		if (row < 0 || row >= DIM || col < 0 || col >= DIM) {
			throw new IllegalArgumentException("Errore nei parametri");
		}
		
		this.board[row][col] = item;
	}

	public int countShipCellsInRow(int row) {
		if (row < 0 || row >= DIM) {
			throw new IllegalArgumentException("Errore nei parametri");
		}
		
		int ans = 0;
		for(int i = 0; i < this.getSize(); i++) {
			ShipItem curr = this.getCell(row, i);
			if(curr != ShipItem.SEA && curr != ShipItem.EMPTY) {
				ans++;
			}
		}
		
		return ans;
	}

	public int countShipCellsInColumn(int column) {
		if (column < 0 || column >= DIM) {
			throw new IllegalArgumentException("Errore nei parametri");
		}
		
		int ans = 0;
		for(int i = 0; i < this.getSize(); i++) {
			ShipItem curr = this.getCell(i, column);
			if(curr != ShipItem.SEA && curr != ShipItem.EMPTY) {
				ans++;
			}
		}
		
		return ans;
	}

	public int[] getCountingRow() {
		int[] ans = new int[this.getSize()];
		
		for(int i = 0; i < this.getSize(); i++) {
			ans[i] = this.countShipCellsInColumn(i);
		}
		
		return ans;
	}

	public int[] getCountingCol() {
		int[] ans = new int[this.getSize()];
		
		for(int i = 0; i < this.getSize(); i++) {
			ans[i] = this.countShipCellsInRow(i);
		}
		
		return ans;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (int k = 0; k < DIM * DIM; k++) {
			int row = k / DIM;
			int col = k % DIM;

			sb.append(board[row][col].getValue());
			sb.append(col == DIM - 1 ? System.lineSeparator() : ' ');
		}

		return sb.toString();
	}

}
