
public class player {
	private String name;
	private int plind;
	private boolean isAI;
	private place_piece place;
	
	public player(String name, boolean isAI) {
		this.name = name;
		this.isAI = isAI;
	}
	
	public void setplInd(int ind) {
		this.plind = ind;
	}
	
	public int getplInd() {
		return plind;
	}
	
	public boolean getIsAI(){
		return isAI;
	}
		
	public String toString() {
		return name;
	}

	public void bindplace(place_piece place) {
		this.place = place;
	}
	
	public piece makeMove(Integer[][] boardarray, int colInd) {
		return place.place_Piece(boardarray, colInd, plind);
	}
}
