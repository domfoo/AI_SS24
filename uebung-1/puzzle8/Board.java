package puzzle8;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.LinkedList;

/**
 * Klasse Board für 8-Puzzle-Problem
 * @author domfoo
 */
public class Board {

	/**
	 * Problemgröße
	 */
	public static final int N = 8;

	/**
	 * Board als Feld. 
	 * Gefüllt mit einer Permutation von 0,1,2, ..., 8.
	 * 0 bedeutet leeres Feld.
	 */
	protected int[] board = new int[N+1];

	/**
	 * Generiert ein zufälliges Board.
	 */
	public Board() {
		this.board = new int[]{0,1,2,3,4,5,6,7,8};
		Random random = new Random();

		do {
			for (int i = 0; i < this.board.length; i++) {
				int randomIdx = random.nextInt(this.board.length);
				int temp = this.board[randomIdx];
				this.board[randomIdx] = this.board[i];
				this.board[i] = temp;
			}
		} while (!parity());
	}
	
	/**
	 * Generiert ein Board und initialisiert es mit board.
	 * @param board Feld gefüllt mit einer Permutation von 0,1,2, ..., 8.
	 */
	public Board(int[] board) {
		this.board = board;
	}

	@Override
	public String toString() {
		return "Puzzle{" + "board=" + Arrays.toString(board) + '}';
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Board other = (Board) obj;
		return Arrays.equals(this.board, other.board);
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 67 * hash + Arrays.hashCode(this.board);
		return hash;
	}
	
	/**
	 * Paritätsprüfung.
	 * @return Parität.
	 */
	public boolean parity() {
		int false_count = 0;

		for (int x = 1; x < this.board.length; x++) {
			for (int y : this.board) {
				if (y == 0) continue;
				if (y > x) false_count++;
				if (y == x) break;
			}
		}

		return false_count % 2 == 0;
	}

	/**
	 * Heurstik h1. (Anzahl der falsch platzierten Steine)
	 * @return Heuristikwert.
	 */
	public int h1() {
		int result = 0;
		for (int i = 0; i < this.board.length; i++) {
			if (this.board[i] == 0) continue;
			if (this.board[i] != i) result++;
		}
		return result;
	}
	
	/**
	 * Heurstik h2. (Manhattan-Distanz zum Ziel)
	 * @return Heuristikwert.
	 */
	public int h2() {
		int result = 0;
		for (int i = 0; i < this.board.length; i++) {
			if (this.board[i] == 0) continue;

			int row = i % 3;
			int col = i / 3;
			int goal_row = this.board[i] % 3;
			int goal_col = this.board[i] / 3;

			result += Math.abs(row - goal_row) + Math.abs(col - goal_col);
		}
		return result;
	}
	
	/**
	 * Liefert eine Liste der möglichen Aktion als Liste von Folge-Boards zurück.
	 * @return Folge-Boards.
	 */
	public List<Board> possibleActions() {
		List<Board> boardList = new LinkedList<>();

		// find position of empty tile
		int empty = -1;
		for (int i = 0; i < this.board.length; i++) {
			if (this.board[i] == 0) {
				empty = i;
				break;
			}
		}

		int emptyRow = empty % 3;
		int emptyCol = empty / 3;

		if (emptyCol != 0) boardList.add(step(empty, empty - 3));
		if (emptyCol != 2) boardList.add(step(empty, empty + 3));

		if (emptyRow != 0) boardList.add(step(empty, empty - 1));
		if (emptyRow != 2) boardList.add(step(empty, empty + 1));

		return boardList;
	}

	private Board step(int oldPos, int newPos) {
		int[] temp = this.board.clone();
		temp[oldPos] = temp[newPos];
		temp[newPos] = 0;
		return new Board(temp);
	}
	
	
	/**
	 * Prüft, ob das Board ein Zielzustand ist.
	 * @return true, falls Board Ziestzustand (d.h. 0,1,2,3,4,5,6,7,8)
	 */
	public boolean isSolved() {
		return Arrays.equals(this.board, new int[]{0,1,2,3,4,5,6,7,8});
	}
	
	
	public static void main(String[] args) {
		Board b = new Board(new int[]{7,2,4,5,0,6,8,3,1});		// abc aus Aufgabenblatt
		Board goal = new Board(new int[]{0,1,2,3,4,5,6,7,8});
				
		System.out.println(b);
		System.out.println(b.parity());
		System.out.println(b.h1());
		System.out.println(b.h2());
		
		for (Board child : b.possibleActions())
			System.out.println(child);
		
		System.out.println(goal.isSolved());
	}
}
	
