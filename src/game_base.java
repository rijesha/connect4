import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import javax.swing.ImageIcon;

public abstract class game_base {	
	protected String gameName;
	
	protected String p1Url = null;
	protected String p1FadedUrl = null;
	protected String p2Url = null;
	protected String p2FadedUrl = null;
	
	protected ImageIcon p1;
	protected ImageIcon p1Faded;
	protected ImageIcon p2;
	protected ImageIcon p2Faded;
	
	public player HUMAN  = new player("Human", false);
	public player EASY   = new player("AI Easy", true);
	public player MEDIUM = new player("AI Medium", true);
	public player HARD   = new player("AI Hard", true);
	
	public player Player1 = HUMAN;
	public player Player2 = HUMAN;
	
	public game_base(String gameName){
		this.gameName = gameName;
		renderImageIcons();
		bindPlayers();
	}
	
	@Override
	public String toString(){
		return gameName;
	};
	
	public abstract game_base returnNewGameBase();
	
	protected abstract void defineImageUrls();
	
	public void renderImageIcons(){
		defineImageUrls();
		p1 = urlToImageIcon(p1Url);
		p1Faded = urlToImageIcon(p1FadedUrl);
		p2 = urlToImageIcon(p2Url);
		p2Faded = urlToImageIcon(p2FadedUrl);
	}

    protected ImageIcon urlToImageIcon(String url) {
    	return new ImageIcon(this.getClass().getResource(url));
    }
    
    public piece generateNewPiece() {
    	return new piece(p1, p1Faded, p2, p2Faded);
    }
    
	protected void bindPlayers(){
		HUMAN.bindplace(new Place_Human());
		EASY.bindplace(new Place_Easy());
		MEDIUM.bindplace(new Place_Medium());
		HARD.bindplace(new Place_Hard());
	}
	
	public void setPlayer1(player pl){
		Player1 = pl;
		pl.setplInd(1);
	}
	
	public void setPlayer2(player pl){
		Player2 = pl;
		pl.setplInd(2);
	}
	
	protected int getEmptyRow(Integer[][] boardarray, int colInd) throws NoSuchRow {
		for (int r = 0; r < 6; r++){
			if (boardarray[colInd][r] == 0)
				return r;
		}
		throw new NoSuchRow();
	}
	
	public boolean noMoreMoves(Integer[][] arr) {
		for (int i=0;i<7;i++) {
			if (arr[i][5]==0)
		        return false;
		  }
		return true;
	}
	
	public Integer[][] copyArray(Integer[][] src) {
		Integer[][] dst = new Integer[7][6];
		
		for (int i = 0; i < 7; i++){
			for (int j = 0; j < 6; j++){
				dst[i][j] = src[i][j];
			}
		}
		return dst;
	}
		
	public boolean checkWin(Integer[][] arr, int player) {
		for (int c=0;c<7;c++) {
			for (int r=0;r<6;r++) {
				if(checkHorizontalWin(arr, player, r, c) || checkVerticalWin(arr, player, r, c) || checkDiagonalWin(arr, player, r, c)) {
					return true;
				}
			}
		}
		return false;
	}
	

	
	protected abstract boolean checkHorizontalWin(Integer[][] boardarray, int player, int row, int col);
	
	protected abstract boolean checkVerticalWin(Integer[][] boardarray, int player, int row, int col);
	
	protected abstract boolean checkDiagonalWin(Integer[][] boardarray, int player, int row, int col);
	
	private class intPair implements Comparable<intPair>, Predicate<intPair>{
		private Integer ind;
		public Integer val;
		
		public intPair(Integer ind, Integer val) {
			this.ind = ind;
			this.val = val;
		}
		
		public Integer getval() {
			return val;
		}

		public Integer getindex() {
			return ind;
		}
		
		@Override
		public int compareTo(intPair arg0) {
			return val.compareTo(((intPair) arg0).getval());
		}
		
		@Override
		public String toString() {
			return ind.toString() + " " + val.toString();
		}

		@Override
		public boolean test(intPair arg0) {
			if (((intPair) arg0).getval().intValue() != this.val.intValue())
				return true;
			return false;
		}
		
	}
		
	public piece recursiveAIWrapper(Integer[][] boardarray, int pl, int level) {
		List<intPair> arr = new ArrayList<intPair>();
		for (int i = 0; i <= 6; i++){
			Integer[][] tmpba = copyArray(boardarray);
			try {
				tmpba[i][getEmptyRow(boardarray, i)] = 2;
				arr.add(new intPair(i, recursive_AI(tmpba, level, pl)));	
			} catch (NoSuchRow e) {
			}
		}
		if (pl == 2)
			arr.removeIf(Collections.max(arr));
		else
			arr.removeIf(Collections.min(arr));
		
		piece p = generateNewPiece();
		p.setCol(arr.get(new Random().nextInt(arr.size())).getindex());
		int i = 3;
		while (true){
			try {
				p.setRow(getEmptyRow(boardarray, p.getCol()));
				return p;
			} catch (NoSuchRow e) {
				p.setCol(arr.get(new Random().nextInt(arr.size())).getindex());
				i--;
				if (i < 0) {
					p.setCol(new Random().nextInt(7));
				}
			}
		}
	}

	public Integer recursive_AI(Integer[][] boardarray, int levels, int pl) {
		int oppl = (pl == 1) ? 2 : 1;
		int ranking = 0;
		for (int i = 0; i <= 6; i++){
			Integer[][] tmpba = copyArray(boardarray);
			try {
				tmpba[i][getEmptyRow(boardarray, i)] = 1;
				if (checkWin(tmpba, oppl)) {
					return -1000*levels;
				}
				else if (checkWin(tmpba, pl)) {
					return 1000*levels;
				}
				else if (levels > 1) {
					ranking =- recursive_AI(tmpba, levels/2, oppl)/6;
				}
			} catch (NoSuchRow e) {
			}
		}
		return ranking;
	}

	private class Place_Easy extends place_piece {
		public piece place_Piece(Integer[][] boardarray, int colInd, int plind) {
			piece p = generateNewPiece();
			while (true) {
				p.setCol(new Random().nextInt(7));
				try {
					p.setRow(getEmptyRow(boardarray, p.getCol()));
					return p;
				} catch (NoSuchRow e) {
				}
			}	
		}
	}
	
	private class Place_Hard extends place_piece {
		public piece place_Piece(Integer[][] boardarray, int colInd, int plind) {
			return recursiveAIWrapper(boardarray, plind, 6);
		}
	}
	
	private class Place_Medium extends place_piece {
		public piece place_Piece(Integer[][] boardarray, int colInd, int plind) {
			return recursiveAIWrapper(boardarray, plind, 1);
		}
	}
	
	private class Place_Human extends place_piece {
		public piece place_Piece(Integer[][] boardarray, int colInd, int plind) {
			piece p = generateNewPiece();
			p.setCol(colInd);
			try {
				p.setRow(getEmptyRow(boardarray, colInd));
				return p;
			} catch (NoSuchRow e) {
				return null;
			}
		}
	}

	public class NoSuchRow extends Exception {
		private static final long serialVersionUID = 1L;

		public NoSuchRow(String message) {
	        super(message);
	    }

		public NoSuchRow() {
		}
	}
}
