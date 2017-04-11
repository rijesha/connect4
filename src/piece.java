import java.awt.Dimension;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class piece extends JLabel {
			private static final long serialVersionUID = 1L;
			
	        HashMap<Integer,ImageIconPair> iconMap = new HashMap<Integer, ImageIconPair>();
	        private int key = 0;
	        private int col = 0;
	        private int row = 0;
	        
	        public piece(ImageIcon p1, ImageIcon p1Faded, ImageIcon p2, ImageIcon p2Faded) {
	        	iconMap.put(0, new ImageIconPair(null,null));
	        	iconMap.put(1, new ImageIconPair(p1, p1Faded));
	        	iconMap.put(2, new ImageIconPair(p2, p2Faded));
	        	setNormalIcon();
	            setHorizontalAlignment(CENTER);
	            setPreferredSize(new Dimension(100,100));
	        }

	        public Integer getKey() {
				return key;
			}
	        
	        public void setKey(int key) {
				this.key = key;
			}
	        
			public void changeIcon(int key) {
				this.key = key;
				setNormalIcon();
			}

			public void updatePiece(piece p) {
				key = p.getKey();
				iconMap = p.getIconMap();
				setRow(p.getRow());
				setCol(p.getCol());
				setNormalIcon();
			}
			
			private HashMap<Integer, ImageIconPair> getIconMap() {
				return iconMap;
			}

			public int getRow() {
				return row;
			}

			public void setRowRaw(int row) {
				this.row = row;
			}

			public void setRow(int row) {
				this.row = 5-row;
			}
			
			public int getCol() {
				return col;
			}

			public void setCol(int col) {
				this.col = col;
			}

			public void clear() {
				key = 0;
				setNormalIcon();
			}
			
			public void setFadedIcon(int key) {
				setIcon(iconMap.get(key).getFadedImageIcon());
			}
			
			public void setNormalIcon() {
				setIcon(iconMap.get(key).getImageIcon());
			}
			
			private class ImageIconPair{
				private ImageIcon img;
				private ImageIcon imgFaded;
				
				public ImageIconPair(ImageIcon image, ImageIcon imageFaded) {
					img = image;
					imgFaded = imageFaded;
				}
				
				public ImageIcon getImageIcon() {
					return img;
				}
				
				public ImageIcon getFadedImageIcon() {
					return imgFaded;
				}
			}
}