import javax.swing.JFrame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
/***
 * This is the main class from the Connect4 Game
 * Program can be run from the command line with no additional arguments.
 * @author Rijesh
 *
 */
public class project_main extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args) {
		new project_main();
	}

	public project_main() {
		super("project_main");
		
		game_board gbo = new game_board();
		game_control gc = new game_control();
		gc.bindGameBoard(gbo);
		gbo.bindGameControl(gc);
		
		setSize(800,800);
		setMinimumSize(getSize());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(new GridBagLayout());
		
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(gc,gbc);
		
		gbc.gridy = 1;
		add(gbo,gbc);
		
		setVisible(true);
		gc.newGame();
	}
}