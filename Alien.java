import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;


public class Alien extends AlienObject {
	final static int DIAMETER = 21;
	private BufferedImage left_image;
	private BufferedImage right_image;
	
	private static Map<String, BufferedImage> cache = new HashMap<String, BufferedImage>();
	
	public Alien(int x, int y, int velocityX, int velocityY, int moves, AlienType type1) {
		super(x, y, velocityX, velocityY, 25, 19, moves, type1);
		try {
			//two images for left and right movement
			BufferedImage img1;
			BufferedImage img2;
			switch(type1){
			//match for each case which images
			case TOP:
				img1 = ImageIO.read(new File("leftnub.jpg"));
				cache.put("leftnub.jpg", img1);
				img2 = ImageIO.read(new File("rightnub.jpg"));
				cache.put("rightnub.jpg", img2);
				left_image = img1;
				right_image = img2; break;
			case MIDDLE:
				img1 = ImageIO.read(new File("leftantennae.jpg"));
				cache.put("leftantennae.jpg", img1);
				img2 = ImageIO.read(new File("rightantennae.jpg"));
				cache.put("rightantennae.jpg", img2);
				left_image = img1;
				right_image = img2; break;
			case BOTTOM:
				img1 = ImageIO.read(new File("leftboss.jpg"));
				cache.put("leftboss.jpg", img1);
				img2 = ImageIO.read(new File("rightboss.jpg"));
				cache.put("rightboss.jpg", img2);
				left_image = img1;
				right_image = img2; break;
			}
					

		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	
	public void draw(Graphics g) {
		//won't need to be used, but default if alien is destroyed
		if(destr){
			g.setColor(Color.BLACK);
			g.fillRect(x, y, 25, 19);
		}
		else {
			switch(type){
			//specific case for image size for bottommost-largest
			case BOTTOM:
				if(isLeftImg())
					g.drawImage(left_image, x, y, 50, 38, null);
				else
					g.drawImage(right_image, x, y, 50, 38, null);
				break;
			//case for top and bottom sizes
			default:
				if(isLeftImg())
					g.drawImage(left_image, x, y, 38, 29, null);
				else
					g.drawImage(right_image, x, y, 38, 29, null);
				break;
			
			}
	}}
}
