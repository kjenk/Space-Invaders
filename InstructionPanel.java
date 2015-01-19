//JPanel containing instructions.
import java.awt.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class InstructionPanel extends JPanel {
	public InstructionPanel(){
		setPreferredSize(new Dimension(500, 350));
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setBackground(Color.BLACK);
		setFocusable(true);
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g); // Paint background, border
		g.setColor(Color.WHITE);
	    g.setFont(new Font("Courier New", Font.BOLD, 20));
		g.drawString("Space Invaders is a game that involves ", 10, 30);
		g.drawString("trying to destroy aliens that inch", 10, 50);
		g.drawString("toward you to rack up points.", 10, 70);
		g.drawString("This implementation includes aliens that", 10, 110);
		g.drawString("shoot back at you that you have to avoid", 10, 130);
		g.drawString("in order to avoid death, as well as an", 10, 150);
		g.drawString("option for two players.", 10, 170);
		g.drawString("All you need to play is the keyboard.", 10, 210);
		g.drawString("Controls are: Left arrow for moving left", 10, 230);
		g.drawString("(\"A\" for Player 2), Right arrow for", 10, 250);
		g.drawString("moving right (\"D\" for Player 2), and", 10, 270);
		g.drawString("Space bar for shooting", 10, 290);
		g.drawString("(\"W\" for Player 2).", 10, 310);
	}
}
