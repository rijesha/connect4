
/***
 * This is the CLI for the Connect4 Game
 * Program can be run from the command line with no additional arguments.
 * @author Rijesh
 *
 */
public class cli_main {

	public static void main(String[] args) {
		new cli_main();
	}

	public cli_main() {
		game_board gbo = new game_board();
		game_control gc = new game_control();
		gc.bindGameBoard(gbo);
		gbo.bindGameControl(gc);
		
	}
}