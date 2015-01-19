import java.awt.*;


import javax.swing.*;

public class Game {
	
	private Game() {
		//Frame for game
		final JFrame frame = new JFrame();
		frame.setLocation(150, 150);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Menu at top for player options and exiting
		MainMenu menu = new MainMenu();
		frame.add(menu, BorderLayout.NORTH);
		//Instructions
		frame.add(new InstructionPanel(), BorderLayout.CENTER);;
        frame.pack();
        frame.setVisible(true);
	}

	/*
	 * Rather than directly building the top level frame object in the main
	 * method, we use the invokeLater utility method to ask the Swing framework
	 * to invoke the method 'run' of the Runnable object we pass it, at some
	 * later time that is convenient for it. (The key technical difference is
	 * that this will cause the new object to be created by a different
	 * "thread".)
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Game();
			}
		});
	}

}
