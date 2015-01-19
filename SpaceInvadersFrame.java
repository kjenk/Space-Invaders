import java.awt.*;

import java.awt.event.*;

import javax.swing.*;
@SuppressWarnings("serial")
public class SpaceInvadersFrame extends JFrame {
	SpaceInvaders invaders;
	public SpaceInvadersFrame(boolean two_players){
		setLocation(150, 150);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		invaders = new SpaceInvaders(two_players);
		add(invaders, BorderLayout.CENTER);
		final JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		final JButton reset = new JButton("Reset");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				invaders.reset();
			}
		});
		final JButton exit = new JButton("Exit");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		panel.add(reset);
		panel.add(exit);

	}
	public SpaceInvaders invader(){
		return invaders;
	}
}
