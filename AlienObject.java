//For convenience, a class that Alien extends.
public abstract class AlienObject extends GameObject {

	private boolean move_right;
	private boolean move_left;
	private boolean move_down;
	private boolean img_left;
	
	//number of movements that the alien moves in each direction 
	//before moving vertically
	private int movements;
	private int count_moves;
	
	//store velocity that is entered
	private int store_velocityX;
	private int store_velocityY;
	boolean last_left;
	AlienType type;
	public AlienObject(int x, int y, int velocityX, int velocityY, int width,
			int height, int num_moves, AlienType type) {
		super(x, y, velocityX, 0, width, height);
		move_right = true;
		img_left = true;
		movements = num_moves;
		store_velocityX = velocityX;
		store_velocityY = velocityY;
		count_moves = 0;
		this.type = type;
	}
	
	//moving method, and setting boolean for image. 
	@Override
	public void move() {
		x += velocityX;
		y += velocityY;
		if(movements != 0)
			count_moves++;
		if(img_left)
			img_left = false;
		else
			img_left = true;
		accelerate();
		clip();

	}
	
	public boolean isLeftImg(){
		return img_left;
	}
	
	public void accelerate() {
		if(movements != 0){
			if(count_moves == movements){
				move_down = true;
				velocityX = 0;
				velocityY = store_velocityY;
				count_moves = 0;
			}
			else if(move_down && move_left){
				move_left = false;
				move_down = false;
				move_right = true;
				velocityX = Math.abs(store_velocityX);
				velocityY = 0;
			}
			else if(move_down && move_right){
				move_right = false;
				move_down = false;
				move_left = true;
				velocityX = -Math.abs(store_velocityX);
				velocityY = 0;
			}
		}
		else{
			if (x < 0)
				velocityX =  Math.abs(velocityX);
			else if (x > rightBound)
				velocityX = -Math.abs(velocityX);
		}
			
	}
	
	//which type of alien
	public AlienType getType(){
		return type;
	}
	// Bounce the ball, if necessary




}
