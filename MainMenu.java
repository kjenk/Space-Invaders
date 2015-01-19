import java.awt.*;

import java.awt.event.*;

import javax.swing.*;
@SuppressWarnings("serial")
public class MainMenu extends JPanel {
	final int PANELWIDTH = 300;
	final int PANELHEIGHT = 50;
	SpaceInvadersFrame game_frame;
	JFrame instruct_frame;
	boolean other_frame_open;
	public MainMenu(){
		setPreferredSize(new Dimension(PANELWIDTH, PANELHEIGHT));
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setBackground(Color.BLACK);
		setFocusable(true);
		final JButton one_player = new JButton("One Player");
		final JButton two_player = new JButton("Two Player");
		final JButton exit = new JButton("Exit");
		one_player.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game_frame = new SpaceInvadersFrame(false);
				game_frame.pack();
				game_frame.setVisible(true);
				other_frame_open = true;
				game_frame.invader().reset();
				game_frame.setVisible(true);
			}
		});
		two_player.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game_frame = new SpaceInvadersFrame(true);
				game_frame.pack();
				game_frame.setVisible(true);
				other_frame_open = true;
				game_frame.invader().reset();
				game_frame.setVisible(true);
								
			}
		});
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
								
			}
		});
		add(one_player);
		add(two_player);
		add(exit);
		
	}
	public boolean open_frame(){
		return other_frame_open;
	}
	
}
