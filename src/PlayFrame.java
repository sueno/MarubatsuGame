import java.awt.*;
import java.applet.Applet;

import javax.swing.JFrame;

/**
 * �֡��ߥ�����ץᥤ�󥯥饹
 * 
 * CPU���饹���ɲä������魯�뤳�Ȥ�����ޤ�(CPU���饹��API����)
 * jar�ե�����̾�ȥѥå�����̾���碌�����Υѥå�������CpuPlayer���饹���ɲä��ޤ���
 * jar�ե�����򥯥饹�ѥ�����֤����Ȥǡ���ưŪ��CpuPlayer���饹����ɤ��ޤ���
 * 
 * �ƥ����ѡ������κ�������CPU����ɲġ�����
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
