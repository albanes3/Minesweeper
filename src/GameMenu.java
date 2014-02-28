import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.*;

import javax.swing.*;

public class GameMenu extends JMenuBar implements ActionListener{
	private JFrame j = new JFrame();
	public GameMenu(){
		//JMenuBar menuBar = new JMenuBar(); //create the panel for the menu
		JMenu gameMenu = new JMenu("Game"), helpMenu = new JMenu("Help"); //create the two menus

		//create menu items for both menus
		JMenuItem resetMenuButton = new JMenuItem("Reset", KeyEvent.VK_R), 
				topTenButton = new JMenuItem("Top Ten", KeyEvent.VK_T),
				exitButton = new JMenuItem("eXit", KeyEvent.VK_X),
				helpButton = new JMenuItem("Help", KeyEvent.VK_H),
				aboutButton = new JMenuItem("About", KeyEvent.VK_A);

		//Details for "Game" menu
		gameMenu.setMnemonic(KeyEvent.VK_G);

		topTenButton.addActionListener(this);
		topTenButton.setActionCommand("Top Ten");

		exitButton.addActionListener(this);
		exitButton.setActionCommand("exit");

		resetMenuButton.addActionListener(this);
		resetMenuButton.setActionCommand("Reset");

		gameMenu.add(resetMenuButton);
		gameMenu.add(topTenButton);
		gameMenu.add(exitButton);


		//Details for "Help" menu
		helpMenu.setMnemonic(KeyEvent.VK_H);

		helpButton.addActionListener(this);
		helpButton.setActionCommand("help");

		aboutButton.addActionListener(this);
		aboutButton.setActionCommand("About");

		helpMenu.add(helpButton);
		helpMenu.add(aboutButton);

		//add menus to menu bar
		this.add(gameMenu);
		this.add(helpMenu);
	}

	public void actionPerformed(ActionEvent e) {
		if("exit".equals(e.getActionCommand())){
			Game.gtfo=true;
		}
		else if("help".equals(e.getActionCommand())){
			//j.setVisible(true);
			JOptionPane.showMessageDialog(null,"Rules:\n\n- Left click on a tile to reveal the square.\n- Numbers on tiles represent the number of adjacent bombs.\n- Right click on a tile to mark it as a bomb.\n- Right click again to mark it as a possible bomb.\n- Right click a third time to clear markings.\n- The game is over once you click on a bomb.\n- To win, reveal all tiles that are not bombs.\n- There is a counter at the bottom representing the number of bombs left.\n ");
		}
		else if("About".equals(e.getActionCommand())){
			JOptionPane.showMessageDialog(null,"By: Mike Albanese and Trevor Evans\n\n                    CS342\n" );
		}
		else if("Reset".equals(e.getActionCommand())){
			Game.resetGame();
		}
		else if("Top Ten".equals(e.getActionCommand())){
			String toptenString = "Top Ten";
			JOptionPane.showMessageDialog(null, toptenString);
		}

	}
}


