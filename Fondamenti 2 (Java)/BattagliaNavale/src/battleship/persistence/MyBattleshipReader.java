package battleship.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;

import battleship.model.ShipBoard;

public class MyBattleshipReader implements BattleshipReader {
	private ShipBoard solutionBoard;
	private ShipBoard playerBoard;

	public MyBattleshipReader() {
		this.solutionBoard = null;
		this.playerBoard = null;
	}

	@Override
	public ShipBoard getSolutionBoard(Reader reader) throws BadFileFormatException, IOException {
		if (this.solutionBoard == null) {
			this.solutionBoard = readBoard(reader, "<>^vxo~");
		}
		return this.solutionBoard;
	}

	@Override
	public ShipBoard getPlayerBoard(Reader reader) throws BadFileFormatException, IOException {
		if (this.playerBoard == null) {
			this.playerBoard = readBoard(reader, "<>^vxo#");
		}
		return this.playerBoard;
	}

	protected ShipBoard readBoard(Reader reader, String admissibleChars) throws BadFileFormatException, IOException {
		BufferedReader bufReader = new BufferedReader(reader);
		ShipBoard board = new ShipBoard();
		String line;
		int row = 0;
		while ((line = bufReader.readLine()) != null) {
			String[] items = line.split("\\s+");
			if (items.length != board.getSize()) {
				throw new BadFileFormatException("Wrong number of items in line: " + line);
			}
			// validate line
			String values[] = Arrays.stream(items).map(String::trim).toArray(String[]::new);
			for (String s : values) {
				for (char ch : s.toCharArray()) {
					if (admissibleChars.indexOf(ch) < 0) {
						throw new BadFileFormatException("Invalid value: " + ch);
					}
				}
			}
			// check number of lines read so far
			if (row < board.getSize()) {
				board.setCellRow(row, line);
			} else {
				throw new BadFileFormatException("Extra line in file");
			}
			row++;
		}
		if (row != board.getSize()) {
			throw new BadFileFormatException("Not enough lines");
		}
		return board;
	}

}
