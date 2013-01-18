import java.awt.*;
import java.applet.Applet;

import javax.swing.JFrame;

/**
 * 「○×ゲーム」メインクラス
 * 
 * CPUクラスを追加して対戦することが出来ます(CPUクラスのAPI参照)
 * jarファイル名とパッケージ名を合わせ，そのパッケージにCpuPlayerクラスを追加します．
 * jarファイルをクラスパス上に置くことで，自動的にCpuPlayerクラスをロードします．
 * 
 * テスト用，学生の作成したCPUをロード可．．．
 * 
 * 
 */
public class PlayFrame extends JFrame{
	
	BoardPanel playP = new BoardPanel();
	
	public void init() {
		this.add(playP);
		this.setSize(350,420);
	}
	
	public static void main (String[] args){
		PlayFrame playF = new PlayFrame();
		playF.init();
		playF.setVisible(true);
	}
	
}
