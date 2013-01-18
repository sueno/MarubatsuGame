package level_1;

public class CpuPlayer {

	private int turn = 0;
	private int play;
	
	public CpuPlayer(boolean initialMove) {
		if(initialMove) {
			play = 1;
		} else {
			play = -1;
		}
	}

	public int[] nextMove(int[][] board) {

		clear(board);
		int player = play;

		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				if (board[i][j] == 0) {
					return hogeInsert(board, i, j, play);
				}
			}
		}

		return new int[] { -1, -1 };

	}

	private int[] hogeInsert(int[][] board, int row, int col, int player) {
		int[] hoge = { row, col };
		System.out.println("turn : " + turn + "  put : " + row + "," + col);
		return hoge;
	}

	public void clear(int[][] board) {
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				if (board[i][j] != 0)
					return;
			}
		}
		turn = 0;
	}

}
