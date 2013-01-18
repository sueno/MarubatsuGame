package level_2;

import java.util.Random;



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
		
		Random random = new Random();

		for (int i=0;i<200;++i) {
			int[] hoge = {random.nextInt(3),random.nextInt(3)};
			if (board[hoge[0]][hoge[1]]==0) {
				return hogeInsert(board,hoge[0],hoge[1],player);
			}
		}
		
		return new int[]{-1,-1};

	}

	private int[] hogeInsert(int[][] board, int row, int col, int player) {
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
