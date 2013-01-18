/**
 * 
 * 勝敗の判定や，○や×が置かれた時の置かれた場所が正当性の判定
 * 
 * @author sueno
 *
 */
public class Judge {
    
    
    public Judge () {
    }
    
    /**
     * 次の手の○あるいは×が置かれる場所が不正な位置かどうかを判定するメソッド
     * 
     * @param ban 現在の盤の状況
     * @param row 次の手の行位置
     * @param col 次の手の列位置
     * @param value 次の手の種類（○か×か）
     * 
     * @return	true 正しい位置に置かれた時
     * 			false 不正な位置に置かれた時
     */
    public boolean validate(int[][] ban, int row, int col,int value) {
	if (row<0||3<=row) {
	    return false;
	}
	if (col<0||3<=col) {
	    return false;
	}
	if (ban[row][col] == 0) {
	    return true;
	    //ban[x][y] = z;
	} else {
	    return false;
	}/***/
    }
    
    /**
     * ○や×が正しい位置に置かれている時に，勝敗を判定するメソッド
     * 
     * @param ban 現在の盤の状況
     * 
     * @return	0 勝敗がまだ決まっていないとき
     * 			1 先手○が勝ったとき
     * 			2 後手×が勝ったとき
     * 			3 引き分けのとき
     */
    public int judge (int[][] ban) {
	for (int i=0;i<3;i++) {
	    if (ban[i][0]==ban[i][1] && ban[i][0]==ban[i][2] && ban[i][0]!=0) {
		if (ban[i][0]<0) return ban[i][0]+3;
		return ban[i][0];
	    }
	}
	for (int i=0;i<3;i++) {
	    if (ban[0][i]==ban[1][i] && ban[0][i]==ban[2][i] && ban[0][i]!=0) {
		if (ban[0][i]<0) return ban[0][i]+3;
		return ban[0][i];
	    }
	}
	if (ban[0][0]==ban[1][1] && ban[0][0]==ban[2][2] && ban[0][0]!=0) {
	    if (ban[0][0]<0) return ban[0][0]+3;
	    return ban[0][0];
	}
	if (ban[0][2]==ban[1][1] && ban[0][2]==ban[2][0] && ban[0][2]!=0) {
	    if (ban[0][2]<0) return ban[0][2]+3;
	    return ban[0][2];
	}/***/
	
	for (int i=0;i<3;++i) {
	    for (int j=0;j<3;++j) {
		if (ban[i][j] == 0) return 0;
	    }
	}
	
	return 3;
    }
    
    /**
     * 盤のクリア
     * 
     * @param ban 現在の盤の状況
     * 
     * @return クリアされた盤の状況
     */
    public int[][] clearBoard (int[][] ban) {
	
	for (int i=0;i<3;++i) {
	    for (int j=0;j<3;++j) {
		ban[i][j] = 0;
	    }
	}
	return ban;
    }
    
}
