package battleship.controller;

import battleship.model.ShipBoard;
import battleship.model.ShipItem;
import battleship.model.ShipType;

public class MyController extends AbstractController {

	public MyController(ShipBoard solutionBoard, ShipBoard initialBoard) {
		super(solutionBoard, initialBoard);
	}

	@Override
	public int[] getShipCount() {
		int[] ans = new int[ShipType.values().length];
		
		for(int i = 0; i < this.getSize(); i++) {
			this.countShipsInColumn(i, ans);
			this.countShipsAndSubmarinesInRow(i, ans);
		}
		
		return ans;
	}

	private void countShipsInColumn(int col, int[] shipCount) {
		int row = 0, shipLength = 0;
		while (row < getSize()) {
			ShipItem item = getSolutionCellItem(row, col);
			if (item == ShipItem.UP) {
				int startPos = row;
				row++;
				while (row < getSize() && getSolutionCellItem(row, col) != ShipItem.DOWN) {
					row++;
				}
				shipLength = row - startPos + 1;
			} else {
				row++;
			}

			if (shipLength >= 2) {
				ShipType type = ShipType.of(shipLength);
				shipCount[type.ordinal()] += 1;
				shipLength = 0;
			}
		}
	}

	private void countShipsAndSubmarinesInRow(int row, int[] shipCount) {
		int col = 0, shipLength = 0;
		while (col < this.getSize()) {
			ShipItem item = this.getSolutionCellItem(row, col);
			if (item == ShipItem.LEFT) {
				int startPos = col;
				col++;
				while (col < getSize() && this.getSolutionCellItem(row, col) != ShipItem.RIGHT) {
					col++;
				}
				shipLength = col - startPos + 1;
			} else {
				col++;
			}

			if (shipLength >= 2) {
				ShipType type = ShipType.of(shipLength);
				shipCount[type.ordinal()] += 1;
				shipLength = 0;
			}
		}
		
		for(int i = 0; i < getSize(); i++) {
			ShipItem item = this.getSolutionCellItem(row, i);
			
			if(item == ShipItem.SINGLE) {
				shipCount[ShipType.of(1).ordinal()]++;
			}
		}
	}

	@Override
	public int verify() {
		ShipBoard currentBoard = this.getPlayerBoard();
		ShipBoard solutionBoard = this.getSolutionBoard();
		int empty = 0, wrong = 0, size = currentBoard.getSize();
		
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				ShipItem current = currentBoard.getCell(i, j);
				ShipItem solution = solutionBoard.getCell(i, j);
				
				if(current == ShipItem.EMPTY) {
					empty++;
				}else if(current != solution) {
					wrong++;
				}
			}
		}
		
		if(empty == 0) {
			this.setGameOver(true);
		}
		
		return wrong;
	}
}
