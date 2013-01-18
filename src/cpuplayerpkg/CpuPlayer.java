/**
 * �������Υ���ԥ塼����ɽ�����饹CpuPlayer���饹
 * 
 * @author sueno
 * 
 */

package cpuplayerpkg;

public class CpuPlayer {

	private int turn = 0;
	private int play;
	
	/**
	 * ���󥹥ȥ饯��
	 * 
	 * @param initialMove	true  CPU�����
	 * 						false CPU�����
	 */
	public CpuPlayer(boolean initialMove) {
		if(initialMove) {
			play = 1;
		} else {
			play = -1;
		}
	}

	/**
	 * ���μ���󼨤���᥽�å�
	 * 
	 * @param board ����ԥ塼�����֤ˤʤä������פξ���
	 * 
	 * @return	[0] ���μ�ΰ��֤򼨤��������٥��ȥ�ι԰���
	 * 			[1] ���μ�ΰ��֤򼨤��������٥��ȥ�������
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
	 * ���̤Υꥻ�å�
	 * 
	 * @param board �פξ���
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
