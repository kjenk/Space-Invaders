import java.awt.Color;
import java.awt.Graphics;


public class AlienBullet extends GameObject {
	final static int HEIGHT = 10;
	final static int WIDTH = 4;
	final static int VELOCITYY = 20;
	//shoot bullet from bottom of alien
	public AlienBullet(Alien p) {
		super(p.x + p.width/2, p.y + p.height, 0, VELOCITYY, WIDTH, HEIGHT);
	}

	@Override
	public void accelerate() {
		if (x < 0 || x > rightBound)
			velocityX = 0;
		if (y < 0 || y > bottomBound)
			velocityY = 0;

	}

	@Override
	public void clip(){
		if (y > bottomBound)
			destr = true;
	}
	//destroy other object
	public void destroy(GameObject other){
		switch(intersects(other)){
			case NONE: break;
			default: other.destr = true; this.destr = true; break;
		}
	}
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(x, y, WIDTH, HEIGHT);

	}


}
