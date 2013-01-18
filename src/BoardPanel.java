import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

/**
 * 「○×ゲーム」のパネル
 * 
 * 
 * @author sueno
 * 
 */
public class BoardPanel extends Panel implements ActionListener {
    
    private static String cpuclass = "CpuPlayer";
    
    private JButton btn[][]; // 3x3 marubatu board
    private String you;
    private CPUReflector cpup1;
    private CPUReflector cpup2;
    
    private Panel boardP;
    
    private Panel topP;
    private JLabel firstL;
    private JLabel secondL;
    private Choice firstC;
    private Choice secondC;
    private JButton startB;
    
    private Panel infoP;
    private JLabel turnL;
    private JButton resetB;
    private JButton nextB;
    
    private Judge judge;
    
    private int[][] ban = new int[3][3]; // marubatsu board
    private int player = 1; // player
    
    private boolean done;
    
    /**
     * constructor
     * 
     * @param argjudge
     */
    public BoardPanel() {
        
        judge = new Judge();
        you = new String("You");
        
        btn = new JButton[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                btn[i][j] = new JButton(" ");
                btn[i][j].addActionListener(this);
                btn[i][j].setPreferredSize(new Dimension(80, 80));
            }
        }
        turnL = new JLabel("start");
        resetB = new JButton("Reset");
        resetB.addActionListener(this);
        
        firstL = new JLabel("First Player :");
        secondL = new JLabel("Second Player :");
        firstC = new Choice();
        firstC.add(you);
        secondC = new Choice();
        secondC.add(you);
        
        System.err.println("ろーど");
        ArrayList list = CPUReflector.CPUClassLoader(cpuclass);
        for (int i = 0; i < list.size(); ++i) {
            firstC.add((String) list.get(i));
            secondC.add((String) list.get(i));
        }
        
        startB = new JButton("start");
        startB.addActionListener(this);
        nextB = new JButton("next");
        nextB.addActionListener(this);
        
        boardP = new Panel();
        boardP.setLayout(new GridLayout(3, 3));
        for (int i = 0; i < 3; i++) {
            boardP.add(btn[i][0]);
            boardP.add(btn[i][1]);
            boardP.add(btn[i][2]);
        }
        
        topP = new Panel();
        topP.setLayout(new GridLayout(4, 2));
        topP.add(new Label("Marubatsu Game"));
        topP.add(new Label(""));
        topP.add(firstL);
        topP.add(firstC);
        topP.add(secondL);
        topP.add(secondC);
        topP.add(new Label());
        topP.add(startB);
        
        infoP = new Panel(new BorderLayout());
        infoP.add(turnL, BorderLayout.NORTH);
        infoP.add(resetB, BorderLayout.WEST);
        infoP.add(nextB, BorderLayout.EAST);
        
        this.setSize(350, 420);
        this.setLayout(new BorderLayout());
        this.add(topP, BorderLayout.NORTH);
        this.add(boardP, BorderLayout.CENTER);
        this.add(infoP, BorderLayout.SOUTH);
        
        firstC.setEnabled(true);
        secondC.setEnabled(true);
        startB.setEnabled(true);
        nextB.setEnabled(false);
        resetB.setEnabled(false);
        
        done = true;
    }
    
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == startB) {
            startGame();
        }
        
        if (firstC.getSelectedItem().equals(you) || secondC.getSelectedItem().equals(you)) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (e.getSource() == btn[i][j]) {
                        yourMove(i, j);
                    }
                }
            }
        }
        
        if (e.getSource() == resetB) {
            resetGame();
        }
        
        if (e.getSource() == nextB) {
            cpuMove();
        }
        
    }
    
    /**
     * ゲーム開始処理
     */
    private void startGame() {
        firstC.setEnabled(false);
        secondC.setEnabled(false);
        startB.setEnabled(false);
        resetB.setEnabled(true);
        if (!firstC.getSelectedItem().equals(you) && !secondC.getSelectedItem().equals(you)) {
            nextB.setEnabled(true);
        }
        if (!firstC.getSelectedItem().equals(you)) {
            try {
                cpup1 = null;
                cpup1 = new CPUReflector(firstC.getSelectedItem(), true, cpuclass);
            } catch (Exception ex) {
                ex.printStackTrace();
                StringWriter sw = null;
                PrintWriter pw = null;
                sw = new StringWriter();
                pw = new PrintWriter(sw);
                ex.printStackTrace(pw);
                resetGame();
                javax.swing.JOptionPane.showMessageDialog(null, sw.toString());
            }
        }
        if (!secondC.getSelectedItem().equals(you)) {
            try {
                cpup2 = null;
                cpup2 = new CPUReflector(secondC.getSelectedItem(), false, cpuclass);
            } catch (Exception ex) {
                ex.printStackTrace();
                StringWriter sw = null;
                PrintWriter pw = null;
                sw = new StringWriter();
                pw = new PrintWriter(sw);
                ex.printStackTrace(pw);
                resetGame();
                javax.swing.JOptionPane.showMessageDialog(null, sw.toString());
            }
        }
        done = false;
        cpuMove();
    }
    
    /**
     * 次の手を取得(CPU)
     * 
     * @param row 
     * @param col
     */
    private void yourMove(int row, int col) {
        try {
            if(!done) {
                if (judge.validate(ban, row, col, player)) {
                    judge(row,col);
                    cpuMove();
                } else {
                    turnL.setText("Error. Try again");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /**
	 * 
	 */
	private void cpuMove() {
	    try {
	        if(!done) {
	            if (player == 1 && !firstC.getSelectedItem().equals(you)) {
	                int[] hoge = cpup1.nextMove(ban);
	                judge(hoge[0], hoge[1]);
	            } else if (player == -1 && !secondC.getSelectedItem().equals(you)) {
	                int[] hoge = cpup2.nextMove(ban);
	                judge(hoge[0], hoge[1]);
	            }
	        }
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	}

	/**
     * 
     */    
    private void judge(int row, int col) {
        if(player==1)
            turnL.setText("Player1: " + row + "," + col);
        else 
            turnL.setText("Player2: " + row + "," + col);
        if (judge.validate(ban, row, col, player)) {
            ban[row][col] = player;
            player *= -1;
            showJudge();
        } else {
            if(player==1)
                turnL.setText("Player1 failed:" + row + "," + col);
            else 
                turnL.setText("Player2 failed:" + row + "," + col);
        }
    }
        
    private boolean showJudge() {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (ban[i][j] == 1) {
                    btn[i][j].setText("O");
                } else if (ban[i][j] == -1) {
                    btn[i][j].setText("X");
                } else {
                    btn[i][j].setText(" ");
                }
            }
        }
        if (judge.judge(ban) == 1) {
            turnL.setText("Victory : O");
            done = true;
            return true;
        } else if (judge.judge(ban) == 2) {
            turnL.setText("Victory : X");
            done = true;
            return true;
        } else if (judge.judge(ban) == 3) {
            turnL.setText("Draw");
            done = true;
            return true;
        } else {
            return false;
        }
    }

	/**
	 * ゲームのリセット
	 */
	private void resetGame() {
	    ban = judge.clearBoard(ban);
	    turnL.setText("start");
	    player = 1;
	    
	    for (int i = 0; i < 3; ++i) {
	        for (int j = 0; j < 3; ++j) {
	            btn[i][j].setText(" ");
	        }
	    }
	    
	    firstC.setEnabled(true);
	    secondC.setEnabled(true);
	    startB.setEnabled(true);
	    nextB.setEnabled(false);
	    resetB.setEnabled(false);
	}
}
