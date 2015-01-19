import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
//tank file
public class Tank extends GameObject {
	final static int HEIGHT = 24;
	final static int WIDTH = 40;
	private BufferedImage image;
	private static Map<String, BufferedImage> cache = new HashMap<String, BufferedImage>();

	public Tank(int courtwidth, int courtheight) {
		super((courtwidth - WIDTH) / 2, courtheight - HEIGHT - 20, 0, 0, WIDTH, HEIGHT);
		try {
			//tank image upload
			BufferedImage img1;
			img1 = ImageIO.read(new File("tank.jpg"));
			cache.put("tank.jpg", img1);
			image = img1;

		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	public void accelerate() {
		if (x < 0 || x > rightBound)
			velocityX = 0;
		if (y < 0 || y > bottomBound)
			velocityY = 0;
	}

	public void draw(Graphics g) {
		g.drawImage(image, x, y, WIDTH, HEIGHT, null);
	}
}
