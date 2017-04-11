import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

class game_board extends JLayeredPane {
		private static final long serialVersionUID = 1L;
		private JPanel gbp = new JPanel();
		
		private CollumnButton collumnbtns[] = new CollumnButton[7];
		private Integer[][] currentBoard = new Integer[7][6];
		private game_base gba = new classic_game();
		private game_control gc;

		public game_board() {
			setLayout(new BorderLayout());
			setSize(742, 606);
			setBounds(26, 100, 742, 606);
			drawGameBoard();
		}
		
		private void drawGameBoard(){
			gbp.setLayout(new GridLayout(1,7));
			gbp.setSize(700, 600);
			for(int i=0; i<7; i++){
				collumnbtns[i] = new CollumnButton(i);
				gbp.add(collumnbtns[i],new Integer(1));
			}
			gbp.setBounds(0, 0, 700, 600);
			add(gbp);
		}
		
		public void bindGameControl(game_control gamecontrol) {
			gc = gamecontrol;
		}	
		
		public void bindGameBase(game_base gba) {
			this.gba = gba;
			clearBoard();
		}

		private void clearBoard() {
			for (CollumnButton c : collumnbtns){
				c.clearCollumn();
			}
		}

		public Integer[][] gameBoardToArray() {
			int i = 0;
			for (CollumnButton c : collumnbtns) {
				int j = 5;
				for (piece p : c.colpieces) {
					currentBoard[i][j] = p.getKey();
					j--;
				}
				i++;
			}
			return currentBoard;
		}
		
		public void addPiece(piece p) {
			if (p == null || p.getRow() == -1)
				return;
			collumnbtns[p.getCol()].addPiece(p);
		}
		
		public void gameWin(){
			for (CollumnButton c : collumnbtns) {
				c.changeBorderColorBasedOnPlayer();
			}
		}
		
		private class CollumnButton extends JButton implements ActionListener {
			private static final long serialVersionUID = 1L;
	    	private JPanel bgrid = new JPanel();
	    	private piece colpieces[] = new piece[6];
	    	int index;
	    	
	        public CollumnButton(int index) {
	        	this.index = index;
	        	setBorderColor(Color.BLACK);
	        	bgrid.setLayout(new GridLayout(6,1));
	        	for(int i=0; i<6; i++){
	    			colpieces[i] = gba.generateNewPiece();
	    			colpieces[i].setPreferredSize(new Dimension(100,100));
	    			bgrid.add(colpieces[i], BorderLayout.CENTER);
	    		}
	    		add(bgrid);
	    		addActionListener(this);
	    		addMouseListener(new java.awt.event.MouseAdapter() {
	    		    public void mouseEntered(java.awt.event.MouseEvent evt) {
	    		    	if (!gc.gameWon) {
	    		    		changeBorderColorBasedOnPlayer();
	    		    		highlightAvailableMove();
	    		    	}
	    		    }

	    		    public void mouseExited(java.awt.event.MouseEvent evt) {
	    		    	if (!gc.gameWon)
	    		    	setBorderColor(Color.BLACK);
	    		    	unhighlightAvailableMove();
	    		    }
	    		});
	        }
	        
	        public void changeBorderColorBasedOnPlayer(){
		    	if (gc.isP1)
		    		setBorderColor(Color.BLUE);
		    	else
		    		setBorderColor(Color.RED);	        	
	        }
	        
	        public void clearCollumn() {
	        	setBorderColor(Color.BLACK);
				for (piece p : colpieces){
					p.updatePiece(gba.generateNewPiece());
				}
			}

			public void addPiece(piece p) {
				if (gc.isP1)
					p.changeIcon(1);
				else
					p.changeIcon(2);

	        	colpieces[p.getRow()].updatePiece(p);
			}

			public void setBorderColor(Color color) {
	        	this.setBorder(BorderFactory.createLineBorder(color, 3));
	        }
	        
			public void actionPerformed(ActionEvent e) {
				gc.moveMade(index);
				if (!gc.gameWon) {
					changeBorderColorBasedOnPlayer();
					highlightAvailableMove();
				}
			}
			
			private void highlightAvailableMove() {
				if (getEmptyRow() != -1) {
					if (gc.isP1)
						colpieces[getEmptyRow()].setFadedIcon(1);
					else
						colpieces[getEmptyRow()].setFadedIcon(2);
				}
					
			}
			
			private void unhighlightAvailableMove() {
				if (getEmptyRow() != -1)
					colpieces[getEmptyRow()].setNormalIcon();;
			}
			
			private int getEmptyRow() {
				for (int r = 5; r >= 0; r--){
					if (colpieces[r].getKey() == 0)
						return r;
				}
				return -1;
			}
	    }
	}
	