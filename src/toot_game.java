
public class toot_game extends game_base {

	public toot_game() {
		super("TOOT");
	}

	@Override
	protected void defineImageUrls() {
		p1Url = "tots_p1.png";
		p1FadedUrl = "tots_p1_b.png";
		p2Url = "tots_p2.png";
		p2FadedUrl = "tots_p2_b.png";
	}

	@Override
	public game_base returnNewGameBase() {
		return new toot_game();
	}

	protected boolean checkHorizontalWin(Integer[][] boardarray, int pl, int row, int col) {
		if (pl == 1)
			return genericCheckWinP1(boardarray, row, col, 0, 5, 0, 3, 0, 1);
		else 
			return genericCheckWinP2(boardarray, row, col, 0, 5, 0, 3, 0, 1);
	}

	protected boolean checkVerticalWin(Integer[][] boardarray, int pl, int row, int col) {
		if (pl == 1)
			return genericCheckWinP1(boardarray, row, col, 0, 2, 0, 6, 1, 0);
		else
			return genericCheckWinP2(boardarray, row, col, 0, 2, 0, 6, 1, 0);
	}
	
	protected boolean checkDiagonalWin(Integer[][] boardarray, int pl, int row, int col) {
		if (pl == 1) {
			if (genericCheckWinP1(boardarray, row, col, 3, 5, 3, 6, -1, -1))
				return true;
			return genericCheckWinP1(boardarray, row, col, 3, 5, 0, 3, -1, 1);
		} else {
			if (genericCheckWinP2(boardarray, row, col, 3, 5, 3, 6, -1, -1))
				return true;
			return genericCheckWinP2(boardarray, row, col, 3, 5, 0, 3, -1, 1);
		}
	}
	
	protected boolean genericCheckWinP1(Integer[][] boardarray, int row, int col, int rowmin, int rowmax, int colmin, int colmax, int delr, int delc){
		if ((rowmin <= row && row <= rowmax) && (colmin <= col && col <= colmax) ) {
			if (boardarray[col][row] == 2 && boardarray[col + 1*delc][row + 1*delr] == 1 && boardarray[col + 2*delc][row + 2*delr] == 1 && boardarray[col + 3*delc][row + 3*delr] == 2){ 
				return true;
			}
		}
		return false;
	}

	protected boolean genericCheckWinP2(Integer[][] boardarray, int row, int col, int rowmin, int rowmax, int colmin, int colmax, int delr, int delc){
		if ((rowmin <= row && row <= rowmax) && (colmin <= col && col <= colmax) ) {
			if (boardarray[col][row] == 1 && boardarray[col + 1*delc][row + 1*delr] == 2 && boardarray[col + 2*delc][row + 2*delr] == 2 && boardarray[col + 3*delc][row + 3*delr] == 1){ 
				return true;
			}
		}
		return false;
	}
	
}
