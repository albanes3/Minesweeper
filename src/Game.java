import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Game extends Canvas implements Runnable{
	private static final long serialVersionUID = 1L;
	public static int width = 210;
	public static int height = width+20;
	public static Dimension size = new Dimension(width,height);
	private Image image;
	public static Level level;

	public static Point point = new Point(0,0);

	public static boolean running = false;

	public Game(){
		addMouseListener(new ClickListener());
		addMouseMotionListener(new ClickListener());
	}

	public static boolean MouseLeft(){//checks for left click
		return ClickListener.MouseLeft();
	}

	public static boolean MouseRight(){//checks for right click
		return ClickListener.MouseRight();
	}

	public void start(){
		new Tile();
		running = true;
		level = new Level();
		Thread thread = new Thread(this);
		thread.start();//jump to run() below
	}

	public void stop(){
		running = false;
	}

	public static void main(String args[]){
		Game game = new Game();
		JFrame j = new JFrame();
		
		JMenuBar menuBar = new JMenuBar(); //create the panel for the menu
		JMenu gameMenu = new JMenu("Game"), helpMenu = new JMenu("Help"); //create the two menus
		
		//create menu items for both menus
		JMenuItem resetMenuButton = new JMenuItem("Reset", KeyEvent.VK_R), 
				topTenButton = new JMenuItem("Top Ten", KeyEvent.VK_T),
				exitButton = new JMenuItem("eXit", KeyEvent.VK_X),
				helpButton = new JMenuItem("Help", KeyEvent.VK_H),
				aboutButton = new JMenuItem("About", KeyEvent.VK_A);
		
		//Details for "Game" menu
		gameMenu.setMnemonic(KeyEvent.VK_G);
		gameMenu.add(resetMenuButton);
		gameMenu.add(topTenButton);
		gameMenu.add(exitButton);
		  
		
		//Details for "Help" menu
		helpMenu.setMnemonic(KeyEvent.VK_H);
		helpMenu.add(helpButton);
		helpMenu.add(aboutButton);
		
		//add menus to menu bar
		menuBar.add(gameMenu);
		menuBar.add(helpMenu);
		

		j.setVisible(true);
		j.setJMenuBar(menuBar);
		j.add(game);
		j.setPreferredSize(size);
		j.pack();
		j.setResizable(false);
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		game.start();
	}



	public void update(){
		level.update();//calls click() inside of Level class to check for new clicks
	}

	public void render(){
		Graphics g = image.getGraphics();
		level.render(g);
		g.drawRect(0, 0, 200, 200); //draws our 10x10 grid
		g = getGraphics();
		g.drawImage(image, 0, 0, width, height, 0, 0, width, height, null);//generates the outer window
		g.dispose();
	}



	public void run(){//when thread.start() is called, it automatically jumps to this function
		image = createVolatileImage(width,height);

		while(running){
			update();//go to update()
			render();//renders the grid after checking conditions and changing tile images

			try{
				Thread.sleep(15);
			} catch(Exception e){
				e.printStackTrace();
			}
		}

		saveHighScore();
		startNewGame();
	}


	public void saveHighScore(){//if high score, stick in an array to be displayed by the menu

	}

	private void startNewGame() {//creates a new Level object

	}

}
