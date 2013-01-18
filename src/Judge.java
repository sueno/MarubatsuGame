/**
 * 
 * ���Ԥ�Ƚ��䡤����ߤ��֤��줿�����֤��줿��꤬��������Ƚ��
 * 
 * @author sueno
 *
 */
public class Judge {
    
    
    public Judge () {
    }
    
    /**
     * ���μ�Ρ����뤤�ϡߤ��֤�����꤬�����ʰ��֤��ɤ�����Ƚ�ꤹ��᥽�å�
     * 
     * @param ban ���ߤ��פξ���
     * @param row ���μ�ι԰���
     * @param col ���μ�������
     * @param value ���μ�μ���ʡ����ߤ���
     * 
     * @return	true ���������֤��֤��줿��
     * 			false �����ʰ��֤��֤��줿��
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
     * ����ߤ����������֤��֤���Ƥ�����ˡ����Ԥ�Ƚ�ꤹ��᥽�å�
     * 
     * @param ban ���ߤ��פξ���
     * 
     * @return	0 ���Ԥ��ޤ���ޤäƤ��ʤ��Ȥ�
     * 			1 ���������ä��Ȥ�
     * 			2 ���ߤ����ä��Ȥ�
     * 			3 ����ʬ���ΤȤ�
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
     * �פΥ��ꥢ
     * 
     * @param ban ���ߤ��פξ���
     * 
     * @return ���ꥢ���줿�פξ���
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
