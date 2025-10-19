package battleship.controller;

import java.util.StringJoiner;

import battleship.model.ShipBoard;
import battleship.model.ShipItem;
import battleship.model.ShipType;

public abstract class AbstractController implements Controller {

	private final ShipBoard solutionBoard;
	private final ShipBoard playerBoard;

	private boolean gameOver = false;

	public AbstractController(ShipBoard solutionBoard, ShipBoard initialBoard) {
		if (solutionBoard.getSize() != initialBoard.getSize()) {
			throw new IllegalArgumentException("Initial and solution boards must be of the same size, instead of "
					+ initialBoard.getSize() + " vs. " + solutionBoard.getSize());
		}
		this.solutionBoard = solutionBoard;
		this.playerBoard = initialBoard;
	}

	@Override
	public String getShipList() {
		StringJoiner joiner = new StringJoiner(System.lineSeparator());
		ShipType[] types = ShipType.values();

		for (ShipType shipType : types) {
			int count = getShipCount()[shipType.ordinal()];
			String type = shipType.toString();
			joiner.add(count + type);
		}

		return joiner.toString();
	}

	@Override
	public ShipItem getCurrentCellItem(int row, int col) {
		return this.getPlayerBoard().getCell(row, col);
	}

	@Override
	public void setCurrentCellItem(int row, int col, ShipItem value) {
		this.getPlayerBoard().setCell(row, col, value);
	}

	@Override
	public ShipItem getSolutionCellItem(int row, int col) {
		return this.getSolutionBoard().getCell(row, col);
	}

	@Override
	public int getSize() {
		return this.getPlayerBoard().getSize();
	}

	@Override
	public int[] getCountingRow() {
		return this.getSolutionBoard().getCountingRow();
	}

	@Override
	public int[] getCountingColumn() {
		return this.getSolutionBoard().getCountingCol();
	}


	protected ShipBoard getSolutionBoard() {
		return this.solutionBoard;
	}

	protected ShipBoard getPlayerBoard() {
		return this.playerBoard;
	}

	@Override
	public boolean isGameOver() {
		return this.gameOver;
	}

	protected void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
}
