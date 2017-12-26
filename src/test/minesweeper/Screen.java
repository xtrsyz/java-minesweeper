package test.minesweeper;


import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Description
 *
 * @author xtrsyz.org
 *         Date: 2/12/13
 */
public class Screen extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6949702043578003269L;
    private JButton buttons[][];
    private int iboms[][];
    private static int cols = 10, rows = 10, boms = 10, kotak;
    
    
    
    public static void main(String[] args) {
    	 
        //opens the window where the messages will be received and sent
        Screen frame = new Screen(cols,rows,boms);
        kotak = rows * cols - boms;
     
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
 
    }
    public Screen(int rows,int cols, int boms) {

        super("Screen");
        
        JPanel bomFields = new JPanel();
        GridLayout bomGrid =new GridLayout(rows,cols);
        bomFields.setLayout(bomGrid);
        buttons = new JButton[cols][rows];
        iboms = new int[cols][rows];
        
        int i = 0;
		while(i < boms) {
			int x,y;
			x = (int)(Math.random() * cols); 
			y = (int)(Math.random() * rows); 
			if (iboms[x][y] != 9) {
				iboms[x][y] = 9;
				hitungBom(x,y);
				i++;
			}
        }

        
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof JButton) {
                	JButton btn = (JButton) e.getSource();
                	int col = (int) btn.getClientProperty("col");
                	int row = (int) btn.getClientProperty("row");
                	buka(col,row);
                	System.out.println("clicked column " + col
                            + ", row " + row + " = " + iboms[col][row]);
                }
            }
        };

        for (int ic=0;ic<cols;ic++) {
        	for (int ir=0;ir<rows;ir++) {
        		buttons[ic][ir] = new JButton(" ");
        		buttons[ic][ir].addActionListener(listener);
        		buttons[ic][ir].putClientProperty("col", ic);
        		buttons[ic][ir].putClientProperty("row", ir);
        		buttons[ic][ir].putClientProperty("val", iboms[ic][ir]);
        		bomFields.add(buttons[ic][ir]);
        	}
        }
        

        getContentPane().add(bomFields);


    }

    private void hitungBom(int col, int row){
    	int c1 = col-1;
    	int r1 = row-1;
    	int c2 = col+1;
    	int r2 = row+1;
    	
    	for (int ic=c1;ic<=c2;ic++) {
    		for (int ir=r1;ir<=r2;ir++) {
    			try {
    				if (iboms[ic][ir] != 9) {
        				iboms[ic][ir]++;
        			}
    			} catch (Exception e) {
    				
    			}
    			
    		}
    	}
    }
    
    private void buka(int col, int row) {
    	if (buttons[col][row].getText() == " ") {
    		buttons[col][row].setBackground(Color.white);
			if (iboms[col][row] == 9) {
				buttons[col][row].setText("X");
				buttons[col][row].setBackground(Color.red);
				kalah();
			} else if (iboms[col][row] == 0) {
				buttons[col][row].setText("");
			} else {
				buttons[col][row].setText(Integer.toString(iboms[col][row]));
			}
			if (iboms[col][row] == 0) bukaKeliling(col,row);
			kotak--;
			if (kotak == 0) menang();
    	}
    }
    
    private void bukaKeliling(int col, int row) {
    	int c1 = col-1;
    	int r1 = row-1;
    	int c2 = col+1;
    	int r2 = row+1;
    	
    	for (int ic=c1;ic<=c2;ic++) {
    		for (int ir=r1;ir<=r2;ir++) {
    			try {
    				if (buttons[ic][ir].getText() == " ") {
        				buka(ic,ir);
    				}    				
    			} catch (Exception e) {
    				
    			}
    			
    		}
    	}
    }
    
    private void kalah() {
    	for (int ic=0;ic<cols;ic++) {
        	for (int ir=0;ir<rows;ir++) {
        		if (buttons[ic][ir].getText() == " " && iboms[ic][ir] == 9) {
        			buttons[ic][ir].setText("X");
    				buttons[ic][ir].setBackground(Color.yellow);
        		}
        	}
        }
    }
    
    private void menang() {
    	for (int ic=0;ic<cols;ic++) {
        	for (int ir=0;ir<rows;ir++) {
        		if (buttons[ic][ir].getText() == " " && iboms[ic][ir] == 9) {
        			buttons[ic][ir].setText("X");
    				buttons[ic][ir].setBackground(Color.green);
        		}
        	}
        }
    }
    
}
