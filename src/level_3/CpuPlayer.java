package level_3;

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
		++turn;
		int player = play;

		if (board[1][1] == 0) {
			return hogeInsert(board, 1, 1, play);
		}

		do {

			if ((board[0][0] + board[1][1] + board[2][2]) == (player * 2)) {
				if (board[0][0] == 0)
					return hogeInsert(board, 0, 0, play);
				if (board[1][1] == 0)
					return hogeInsert(board, 1, 1, play);
				if (board[2][2] == 0)
					return hogeInsert(board, 2, 2, play);
			}
			if ((board[0][2] + board[1][1] + board[2][0]) == (player * 2)) {
				if (board[0][2] == 0)
					return hogeInsert(board, 0, 2, play);
				if (board[1][1] == 0)
					return hogeInsert(board, 1, 1, play);
				if (board[2][0] == 0)
					return hogeInsert(board, 2, 0, play);
			}

			for (int i = 0; i < 3; ++i) {
				if ((board[i][0] + board[i][1] + board[i][2]) == (player * 2)) {
					for (int j = 0; j < 3; ++j) {
						if (board[i][j] == 0)
							return hogeInsert(board, i, j, play);
					}
				}
				if ((board[0][i] + board[1][i] + board[2][i]) == (player * 2)) {
					for (int j = 0; j < 3; ++j) {
						if (board[j][i] == 0)
							return hogeInsert(board, j, i, play);
					}
				}
			}

			if (player == play) {
				if (player == 1) {
					player = -1;
				} else {
					player = 1;
				}
			} else {
				player = 0;
			}

		} while (player != 0);

		player = play;

		if (turn == 1) {
			if (player == -1)
				return hogeInsert(board, 0, 0, player);
		}
		if (turn == 2) {
			if (player == 1) {
				if (board[0][0] == -1)
					return hogeInsert(board, 0, 2, player);
				if (board[0][2] == -1)
					return hogeInsert(board, 2, 2, player);
				if (board[2][0] == -1)
					return hogeInsert(board, 0, 0, player);
				if (board[2][2] == -1)
					return hogeInsert(board, 0, 2, player);
				if (board[0][1] == -1)
					return hogeInsert(board, 2, 2, player);
				if (board[1][0] == -1)
					return hogeInsert(board, 2, 2, player);
				if (board[1][2] == -1)
					return hogeInsert(board, 0, 0, player);
				if (board[2][1] == -1)
					return hogeInsert(board, 0, 0, player);
			}
		}

		if (board[0][0] == 0)
			return hogeInsert(board, 0, 0, player);
		if (board[0][2] == 0)
			return hogeInsert(board, 0, 2, player);
		if (board[2][0] == 0)
			return hogeInsert(board, 2, 0, player);
		if (board[2][2] == 0)
			return hogeInsert(board, 2, 2, player);
		if (board[0][1] == 0)
			return hogeInsert(board, 0, 1, player);
		if (board[1][0] == 0)
			return hogeInsert(board, 1, 0, player);
		if (board[1][2] == 0)
			return hogeInsert(board, 1, 2, player);
		if (board[2][1] == 0)
			return hogeInsert(board, 2, 1, player);

		return new int[]{1,1};

	}

	private int[] hogeInsert(int[][] board, int row, int col, int player) {
//		board[row][col] = player;
		int[] hoge = {row,col};
		System.out.println("turn : " + turn + "  put : " + row + "," + col);
		return hoge;
	}

	public void clear(int[][] board) {
		for (int i=0;i<3;++i) {
			for (int j=0;j<3;++j) {
				if (board[i][j] != 0)
					return;
			}
		}
		turn = 0;
	}

}
