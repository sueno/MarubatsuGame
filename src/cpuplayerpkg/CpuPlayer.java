/**
 * 対戦相手のコンピュータを表すクラスCpuPlayerクラス
 * 
 * @author sueno
 * 
 */

package cpuplayerpkg;

public class CpuPlayer {

	private int turn = 0;
	private int play;
	
	/**
	 * コンストラクタ
	 * 
	 * @param initialMove	true  CPUが先手
	 * 						false CPUが後手
	 */
	public CpuPlayer(boolean initialMove) {
		if(initialMove) {
			play = 1;
		} else {
			play = -1;
		}
	}

	/**
	 * 次の手を提示するメソッド
	 * 
	 * @param board コンピュータの番になった時の盤の状況
	 * 
	 * @return	[0] 次の手の位置を示す２次元ベクトルの行位置
	 * 			[1] 次の手の位置を示す２次元ベクトルの列位置
	 */
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

	
	/**
	 * 盤面のリセット
	 * 
	 * @param board 盤の状況
	 */
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
