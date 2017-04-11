public class classic_game extends game_base {
		
	public classic_game(){
		super("Classic Game");
	}
	
	public classic_game(String string) {
		super(string);
	}
	
	public game_base returnNewGameBase() {
		return new classic_game();
	}

	@Override
	protected void defineImageUrls() {
		p1Url = "classic_p1.png";
		p1FadedUrl = "classic_p1_b.png";
		p2Url = "classic_p2.png";
		p2FadedUrl = "classic_p2_b.png";
	}

	protected boolean checkHorizontalWin(Integer[][] boardarray, int pl, int row, int col) {
		return genericCheckWin(boardarray,pl, row, col, 0, 5, 0, 3, 0, 1);
	}

	protected boolean checkVerticalWin(Integer[][] boardarray, int pl, int row, int col) {
		return genericCheckWin(boardarray,pl, row, col, 0, 2, 0, 6, 1, 0);
	}
	
	protected boolean checkDiagonalWin(Integer[][] boardarray, int pl, int row, int col) {
		if (genericCheckWin(boardarray,pl, row, col, 3, 5, 3, 6, -1, -1))
			return true;
		return genericCheckWin(boardarray,pl, row, col, 3, 5, 0, 3, -1, 1);
	}
	
	protected boolean genericCheckWin(Integer[][] boardarray, int pl, int row, int col, int rowmin, int rowmax, int colmin, int colmax, int delr, int delc){
		if ((rowmin <= row && row <= rowmax) && (colmin <= col && col <= colmax) ) {
			if (boardarray[col][row] == pl && boardarray[col + 1*delc][row + 1*delr] == pl && boardarray[col + 2*delc][row + 2*delr] == pl && boardarray[col + 3*delc][row + 3*delr] == pl){ 
				return true;
			}
		}
		return false;
	}

	
}
