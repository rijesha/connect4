import java.util.HashMap;
import java.util.Random;

import javax.swing.ImageIcon;

public class victorious_game extends classic_game {
	protected ImageIcon p1Faded;
	protected ImageIcon p2Faded;

	private HashMap<Integer, ImageIcon> vicMap;
	private HashMap<Integer, ImageIcon> catMap;
	
	
	public victorious_game(){
		super("Victorious Game");
	}
	
	
	@Override
	public void renderImageIcons(){
		vicMap = new HashMap<Integer, ImageIcon>();
		catMap = new HashMap<Integer, ImageIcon>();
		
		vicMap.put(0,urlToImageIcon("vic.gif"));
		vicMap.put(1,urlToImageIcon("vic2.gif"));
		vicMap.put(2,urlToImageIcon("vic3.gif"));
		vicMap.put(3,urlToImageIcon("vic4.gif"));
		
		catMap.put(0,urlToImageIcon("cat.gif"));
		catMap.put(1,urlToImageIcon("cat2.gif"));
		catMap.put(2,urlToImageIcon("cat3.gif"));
		catMap.put(3,urlToImageIcon("cat4.gif"));
				
		p1Faded = urlToImageIcon("classic_p1_b.png");
		p2Faded = urlToImageIcon("classic_p2_b.png");
	}
	
	@Override
	public piece generateNewPiece() {
		int tmp = new Random().nextInt(4);
    	return new piece(vicMap.get(tmp), p1Faded, catMap.get(tmp), p2Faded);
    }
}
