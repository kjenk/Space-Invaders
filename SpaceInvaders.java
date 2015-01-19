import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

@SuppressWarnings("serial")
public class SpaceInvaders extends JPanel {
	//Tanks, in case of two-player
	private Tank tank;
	private Tank tank_two;
	//Bullets for both tanks and aliens
	private ArrayList<Bullet> bullets;
	private ArrayList <AlienBullet> alien_bullets;
	//Alien list
	private ArrayList<Alien> aliens;
	/*interval for: regular graphic updates, alien movement, shots by aliens,
	and block for shooting (to avoid space bar spamming).*/
	private int interval = 35; // Milliseconds between updates.
	private int int_alien = 1000; // Milliseconds between updates.
	private int int_alien_shot = 3000;
	private long int_shot = 750;
	/*time of tank's last shot, second tank's last shot, amount of time
	it took to win, and when the game started*/
	private long last_shot;
	private long last_shot_two;
	/*timer for regular updates, alien movement, and alien shots*/
	private Timer timer;       // Each time timer fires we animate one step.
	private Timer alien_timer;
	private Timer alien_shot_timer;
	/*court size*/
	final int COURTWIDTH = 900;
	final int COURTHEIGHT = 750;
	/*paddle speed*/
	final int PADDLE_VEL = 4;
	/*booleans for two-player, losing, and winning*/
	boolean two_player = false;
	boolean lose = false;
	boolean win = false;
	/*ints for score, lives left, and which Alien is going to shoot,
	 * as well as a generator to determining the latter*/
	int score = 0;
	int lives = 6;
	int randomAlien;
	Random generator = new Random();

	public SpaceInvaders(boolean two_players) {
		two_player = two_players;
		//Setting JPanel
		setPreferredSize(new Dimension(COURTWIDTH, COURTHEIGHT));
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setBackground(Color.BLACK);
		setFocusable(true);
		
		//Setting all of the timers explained above.
		timer = new Timer(interval, new ActionListener() {
			public void actionPerformed(ActionEvent e) { tick(); }});
		timer.start(); 
		alien_timer = new Timer(int_alien, new ActionListener(){
			public void actionPerformed(ActionEvent e){alien_tick();}
		});
		alien_timer.start();
		alien_shot_timer = new Timer(int_alien_shot, new ActionListener(){
			public void actionPerformed(ActionEvent e){alien_shot_tick();}
		});
		alien_shot_timer.start();
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				//Key configs if at win/loss stage
				if(win || lose){
					if (e.getKeyCode() == KeyEvent.VK_Y){
						lives = 6;
						reset();
					}
					else if (e.getKeyCode() == KeyEvent.VK_N){
						System.exit(0);
					}
				}
				else{
					//Key config for 1-player/"tank 1"
					if (e.getKeyCode() == KeyEvent.VK_LEFT)
						tank.setVelocity(-PADDLE_VEL, 0);
					else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
						tank.setVelocity(PADDLE_VEL, 0);
					else if (e.getKeyCode() == KeyEvent.VK_R)
						reset();
					else if (e.getKeyCode() == KeyEvent.VK_SPACE){
						if(last_shot == 0 || System.currentTimeMillis() - last_shot > int_shot){
							last_shot = System.currentTimeMillis();
							if(bullets == null){
								bullets = new ArrayList<Bullet>();
							}
							bullets.add(new Bullet(tank));
						}					
					}
					//Key config for 2-player's tank 2
					if(two_player){
						if (e.getKeyCode() == KeyEvent.VK_A)
							tank_two.setVelocity(-PADDLE_VEL, 0);
						else if (e.getKeyCode() == KeyEvent.VK_D)
							tank_two.setVelocity(PADDLE_VEL, 0);
						else if (e.getKeyCode() == KeyEvent.VK_W){
						if(last_shot_two == 0 || System.currentTimeMillis() - last_shot_two > int_shot){
							last_shot_two = System.currentTimeMillis();
							if(bullets == null){
								bullets = new ArrayList<Bullet>();
							}
							bullets.add(new Bullet(tank_two));
						}
					}
					
				}
			}
			}
			public void keyReleased(KeyEvent e){{
				tank.setVelocity(0, 0);
				if(two_player)
					tank_two.setVelocity(0, 0);
			}
		}});
		
		//Setting configs for which Alien will shoot
		alien_bullets = new ArrayList<AlienBullet>();
		randomAlien = generator.nextInt(55);
		
		// After a PongCourt object is built and installed in a container
		// hierarchy, somebody should invoke reset() to get things started... 
	}
	public boolean lost(){
		return lose;
	}
	
	//Reset configs
	public void reset() {
		//lose false, win false, score reset
		lose = false;
		win = false;
		score = 0;
		//lose a life if you reset
		if(lives > 1)
			lives--;
		else
			lose = true;
		
		//clear bullets for tank and alien
		if(bullets != null)
			bullets.clear();
		if(alien_bullets != null)
			alien_bullets.clear();
		
		//set array of aliens
		aliens = new ArrayList<Alien>();
		for(int i = 0; i < 55; i++){
			int y = i/11;
			int x = i % 11;
			if(y == 0)
				aliens.add(new Alien((100+x*50), 100 + (70 * y), 12, 12, 15, AlienType.TOP));
			else if(y <= 2)
				aliens.add(new Alien((100+x*50), 100 + (70 * y), 12, 12, 15, AlienType.MIDDLE));
			else
				aliens.add(new Alien((50+x*60), 100 + (70 * y), 12, 12, 15, AlienType.BOTTOM));
			
		}
		
		//set tank based on player number
		tank = new Tank(COURTWIDTH, COURTHEIGHT);
		if(two_player)
			tank_two = new Tank(COURTWIDTH + 100, COURTHEIGHT);
		grabFocus();
	}

	void tick() {
		//Count score
		score = 0;
		int count_dead = 0;
		for(Alien alien : aliens){
			alien.setBounds(getWidth(), getHeight());
			if(alien.isDestroyed()){
					switch(alien.getType()){
					case TOP: score += 750;
					case MIDDLE: score += 500;
					case BOTTOM: score += 250;
				}
				count_dead++;
			}
			if(alien.y >= tank.y){
				lose = true;
				break;
			}
		}
		
		//For counting dead
		if(count_dead == 55)
			win = true;
		
		//setting tank up
		tank.setBounds(getWidth(), getHeight());
		tank.move();
		if(two_player){
			tank_two.setBounds(getWidth(), getHeight());
			tank_two.move();
		}
		
		//setting bullets up, tanks and aliens both
		if(bullets != null)
			for(Bullet bullet : bullets){
				if(bullet != null){
					bullet.setBounds(getWidth(), getHeight());
					bullet.move();
				}
			}
		tank.setBounds(getWidth(), getHeight());
		tank.move();
		if(bullets != null){
			for(Bullet bullet: bullets){
				if(bullet != null){
					for(Alien alien : aliens){
						if(!alien.isDestroyed())
							bullet.destroy(alien);
					}
				}
			}
		}
		if(alien_bullets != null){
			for(AlienBullet bullet : alien_bullets){
				if(bullet != null){
					bullet.setBounds(getWidth(), getHeight());
					bullet.move();
				}
		}
		if(alien_bullets != null){
			for(AlienBullet bullet: alien_bullets){
				if(bullet != null){
					if(!tank.isDestroyed())
						bullet.destroy(tank);
					if(two_player){
						if(!tank_two.isDestroyed())
							bullet.destroy(tank_two);
					}
				}
			}
		}
		
		//lose a life & reset if tank(s) get(s) destroyed
		if(two_player){
			if(tank_two.isDestroyed() && tank.isDestroyed() && lives > 0)
				reset();
		}
		else if(tank.isDestroyed() && lives > 0)
			reset();
		repaint(); // Repaint indirectly calls paintComponent.

		}
	}
	
	//Alien shooting configs
	void alien_shot_tick(){
		while(aliens.get(randomAlien).isDestroyed())
			randomAlien = generator.nextInt(55);
		alien_bullets.add(new AlienBullet(aliens.get(randomAlien)));
		randomAlien = generator.nextInt(55);
	}
	
	//Alien movement configs
	void alien_tick(){
		int dead_count = 0;
		for(Alien alien : aliens){
			alien.move();
			if(alien.isDestroyed())
				dead_count++;
		}
		if(dead_count > 8){
			if(dead_count > 26){
				if(dead_count > 40){
					if(dead_count == 54)
						alien_timer.setDelay(150);
					else
						alien_timer.setDelay(350);
				}
				else
					alien_timer.setDelay(500);
				}
			else
				alien_timer.setDelay(750);
		}
		repaint();
		
	}
	public boolean getLose(){
		return lose;
	}
	public int getScore(){
		return score;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g); // Paint background, border
		g.setColor(Color.WHITE);
	    g.setFont(new Font("Courier New", Font.BOLD, 22)); //font
	    //win image. Yes or No to continue
	    if(win){
	    	g.drawString("Score = " + score, getWidth()/2 - 100, getHeight()/2 + 100);
	    	g.drawString("Play again? [Y/N]", getWidth()/2 - 100, getHeight()/2 + 200);
	    	g.setFont(new Font("Courier New", Font.BOLD, 150));
	    	g.drawString("YOU WIN!!!", 100, getHeight()/2 - 100);
	    }
	    //lose image
	    else if(lose){
	    	g.drawString("Play again? [Y/N]", getWidth()/2 - 100, getHeight()/2 + 200);
	    	g.setFont(new Font("Courier New", Font.BOLD, 150));
	    	g.drawString("YOU LOSE", 100, getHeight()/2 - 100);

	    }
	    //default image
	    else{
	    	//show score and lives
		    g.drawString("SCORE", 10, 22);
		    g.drawString("  " + score, 10, 40);
		    g.drawString("LIVES", getWidth() - 100, 22);
		    g.drawString("  " + lives, getWidth() - 100, 40);
		    //draw bullets unless destroyed, or remove them
			if(bullets != null){
				Iterator<Bullet> iter = bullets.iterator();
				while(iter.hasNext()){
					   Bullet bullet = iter.next();
					   if (bullet != null && !bullet.isDestroyed())
						   bullet.draw(g);
					   else
						   iter.remove();
					 }
			}
			//draw aliens unless destroyed
			if(aliens != null){
				for(Alien alien : aliens){
					if(alien != null && !alien.isDestroyed())	
						alien.draw(g);
				}
			}
			//draw bullets unless destroyed, and remove otherwise
			if(alien_bullets != null){
				Iterator<AlienBullet> iter = alien_bullets.iterator();
				while(iter.hasNext()){
					   AlienBullet bullet = iter.next();
					   if (bullet != null && !bullet.isDestroyed())
						   bullet.draw(g);
					   else
						   iter.remove();
					 }
			}
			//draw tank unless destroyed
			if(tank != null && !tank.isDestroyed())	
				tank.draw(g);
			if(two_player){
				if(tank_two != null && !tank_two.isDestroyed())	
					tank_two.draw(g);
			}
	    }
	}
}
