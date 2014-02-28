import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.*;

public class Game extends Canvas implements Runnable{
	public static boolean gtfo=false;
	public static boolean hitBomb=false;
	private static final long serialVersionUID = 1L;
	public static int width = 210;
	public static int height = width+80;
	public static Dimension size = new Dimension(width,height);
	private Image image;
	public static boolean gamewon=false;
	private File file = new File("topten.txt");
	public static Level level;
	public static JFrame j;

	public static Point point = new Point(0,0);
	private static BufferedWriter bw;
	public static String[] toptens = new String[10];

	public static boolean running = false;
	public static boolean windowrunning = false;

	public Game(){
		String content = "This is the other content to write into file";

		//create file if it doesn't exist
		try{
			if (!file.exists()) {
				file.createNewFile();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		for(int i=0; i<toptens.length;i++)
			toptens[i]="content";
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String currentline = br.readLine();
			int i = 0;
			while(currentline != null){
				toptens[i] = currentline; 
				System.out.println(toptens[i]);
				System.out.println(currentline + "hi");
				i++;
				currentline = br.readLine();
			}
			br.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		try{
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
		}catch(IOException e){
			e.printStackTrace();
		}
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
		/*new Tile();
		windowrunning=true;
		running = true;
		level = new Level();*/
		Thread thread = new Thread(this);
		thread.start();//jump to run() below
	}

	public void stop(){
		running = false;
	}

	public static void main(String args[]){
		Game game = new Game();
		j = new JFrame();
		GameMenu gm = new GameMenu();

		j.setVisible(true);
		j.setJMenuBar(gm);
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
		new Tile();
		windowrunning=true;
		running = true;
		level = new Level();
		image = createVolatileImage(width,height);

		while(windowrunning){
			update();//go to update()
			render();//renders the grid after checking conditions and changing tile images
			if(hitBomb){
				Level.revealBombs(Level.grid);
				update();
				render();
				Level.endGame(false);
				hitBomb=false;
			}
			if(gamewon){
				update();
				render();
				Level.endGame(true);

				//write to topten.txt file
				//Files.write(file.getPath(), "HI!");

				gamewon=false;
			}
			if(gtfo){
				windowrunning=false;
				try{
					bw.close();
				}catch(IOException e){
					e.printStackTrace();
				}
				j.setVisible(false);
				j.dispose();
			}
			try{
				Thread.sleep(15);
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		if(gtfo){
			windowrunning=false;
			j.setVisible(false);
			j.dispose();
		}
	}


	public static void resetGame(){	
		level=new Level();
		Grid.numRevealed = 0;
		hitBomb=false;
		ClickListener.useMouse=true;
		running = true;
	}

}
