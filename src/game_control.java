import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class game_control extends JPanel implements ActionListener  {
	private static final long serialVersionUID = 1L;
	
	private JComboBox<game_base> gameTypeCBox = new JComboBox<game_base>();
	private JComboBox<player> player1Box = new JComboBox<player>();
	private JComboBox<player> player2Box = new JComboBox<player>();
	private JButton playBtn = new JButton();
	private JLabel status = new JLabel("Player 1 Turn ");
	private game_board gbo = new game_board();
	private game_base classic = new classic_game();
	private game_base victorious = new victorious_game();
	private game_base tot = new toot_game();
	private game_base gba = classic;
	private game_base gbatmp = gba;
	
	public boolean gameWon = false;
	public boolean isP1 = true;
	boolean P1movemade = false;
	boolean P2movemade = false;
	
	public game_control(){
		initializeControls();
		drawControls();
	}

	private void drawControls() {
		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(new GridBagLayout());
		gbc.insets= new Insets(5,5,5,5);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(gameTypeCBox,gbc);
		add(player1Box,gbc);
		add(player2Box,gbc);
		add(playBtn,gbc);
		add(status,gbc);
	}

	private void initializeControls() {
		gameTypeCBox.addItem(classic);
		gameTypeCBox.addItem(victorious);
		gameTypeCBox.addItem(tot);
		gameTypeCBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				add_opponents();
			}			
		});
		
		add_opponents();
		playBtn.setText("PLAY");
		playBtn.addActionListener(this);
	}
	
	private void add_opponents() {
		gbatmp = (game_base) gameTypeCBox.getSelectedItem();
		player1Box.removeAllItems();
		player1Box.addItem(gbatmp.HUMAN);
		player1Box.addItem(gbatmp.EASY);
		player1Box.addItem(gbatmp.MEDIUM);
		player1Box.addItem(gbatmp.HARD);
		player2Box.removeAllItems();
		player2Box.addItem(gbatmp.HUMAN);
		player2Box.addItem(gbatmp.EASY);
		player2Box.addItem(gbatmp.MEDIUM);
		player2Box.addItem(gbatmp.HARD);
	}

	public void bindGameBoard(game_board gameboard) {
		this.gbo = gameboard;
	}
	
	public void newGame() {
		gba = (game_base) gameTypeCBox.getSelectedItem();
		gbo.bindGameBase(gba);
		
		gba.setPlayer1((player) player1Box.getSelectedItem());
		gba.setPlayer2((player) player2Box.getSelectedItem());
		gameWon = false;
		isP1 = true;
		status.setText("Player 1 Turn ");
		if (gba.Player1.getIsAI())
			player1Move(0);
	}
	
	public void actionPerformed(ActionEvent e) {
		newGame();
	}
	
	private void player1Move(int colInd) {
		gbo.addPiece(gba.Player1.makeMove(gbo.gameBoardToArray(), colInd));
		isP1 = false;
		P1movemade = true;
		status.setText("Player 2 Turn ");
	}

	private void player2Move(int colInd) {
		gbo.addPiece(gba.Player2.makeMove(gbo.gameBoardToArray(), colInd));
		isP1 = true;
		P2movemade = true;
		status.setText("Player 1 Turn ");		
	}
	
	public void moveMade(int colInd) {
		if (!gameWon){
			P1movemade = false;
			P2movemade = false;
			if (isP1 && !gba.Player1.getIsAI()) {
				player1Move(colInd);
			} else if (!isP1 && !gba.Player2.getIsAI()) {
				player2Move(colInd);
			}
			
			if (checkGameWin(1) || checkGameWin(2))
				return;
			
			if (!P1movemade && isP1 && gba.Player1.getIsAI()) {
				player1Move(colInd);
			}
			if (!P2movemade && !isP1 && gba.Player2.getIsAI()) {
				player2Move(colInd);
			}
			
			if (checkGameWin(1) || checkGameWin(2))
				return;
			
			if (gba.noMoreMoves(gbo.gameBoardToArray())){
				gameEnd("It is a TIE! ");
				return;
			}
		}
		
	}
	
	private boolean checkGameWin(int plind) {
		if (gba.checkWin(gbo.gameBoardToArray(), plind)) {
			if (plind == 1)
				isP1 = true;
			else
				isP1 = false;
			gameEnd("Player " + plind + " Wins");
			return true;
		}
		return false;
	}
	
	private void gameEnd(String str) {
		status.setText(str);
		gbo.gameWin();
		gameWon = true;
	}
}
