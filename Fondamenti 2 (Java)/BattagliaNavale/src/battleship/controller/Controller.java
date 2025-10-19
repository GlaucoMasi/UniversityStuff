package battleship.controller;

import battleship.model.ShipItem;


public interface Controller {
	int getSize();

	ShipItem getCurrentCellItem(int row, int col);

	void setCurrentCellItem(int row, int col, ShipItem value);

	boolean isGameOver();

	String getShipList();

	int[] getCountingRow();

	int[] getCountingColumn();

	int verify();

	int[] getShipCount();

	ShipItem getSolutionCellItem(int row, int col);
}
