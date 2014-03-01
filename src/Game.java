import java.awt.*;
import java.io.*;
import javax.swing.*;

public class Game extends Canvas implements Runnable{
	private static final long serialVersionUID = 1L;
	
	public static Dimension size = new Dimension(width,height);
	
	private Image image;
	
	public static File file = new File("topten.txt");
	private static FileWriter fw;
	private static BufferedWriter bw;
	
	public static Level level; //Runs the 10x10 grid of tiles
	public static JFrame j;
	public static JTextArea bombCount, timeCount; //Fields to display elapsed time and bombs remaining
	public static Point point = new Point(0,0); //Checks against tile location to determine clicks
	
	public static long startTime,currentTime,stopTime;
	public static int width = 290; //frame width
	public static int height = width-35; //frame height
	public static int gameStart=0;

	public static boolean running = false; //stores whether the game is running or not
	public static boolean windowrunning = false; //stores whether the entire application is running or should close
	public static boolean gtfo=false; //tells the application to close the window and stop running
	public static boolean hitBomb=false;
	public static boolean gamewon=false;
	
	public static String[] toptens = new String[10]; //array storing top ten high scores

	//Constructor
	public Game(){
		//create file for top tens
		try{
			if (!file.exists()) {
				file.createNewFile();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		
		
		for(int i=0; i<toptens.length;i++)
			toptens[i]=""; //initialize the top tens array


		addMouseListener(new ClickListener());
		addMouseMotionListener(new ClickListener());
	}
	
	//main
	public static void main(String args[]){
		Game game = new Game();
		j = new JFrame();
		GameMenu gm = new GameMenu();
		timeCount = new JTextArea();
		bombCount = new JTextArea();
		bombCount.setText("Bombs: 10");

		j.add(gm.resetButt);

		j.add(timeCount);
		j.add(bombCount);
		timeCount.setBounds(210,70,70,20);
		bombCount.setBounds(210, 40, 70, 20);
		timeCount.setText("Time: 0.0");
		timeCount.setEditable(false);
		bombCount.setEditable(false);

		j.add(game);
		j.setPreferredSize(size);
		j.pack();
		j.setJMenuBar(gm);
		j.setVisible(true);
		j.setResizable(false);
		
        // Determine the center of the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int w = j.getSize().width;
        int h = j.getSize().height;
        int x = (dim.width-w)/2;
        int y = (dim.height-h)/2;

        // Move the window
		j.setLocation(x,y);
		
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.start();
	}

	public void start(){
		Thread thread = new Thread(this);
		thread.start();//jump to run() below
	}

	public void stop(){
		running = false;
	}

	public void update(){
		level.update();//calls click() inside of Level class to check for new clicks
	}

	public void render(){
		//Set display for time counter
		if(gameStart>0){
			String time = new String("Time: " + (int)((currentTime-startTime)/100)/10 + "." + (int)((currentTime-startTime)/100)%10);
			timeCount.setText(time);
		}
		else{
			timeCount.setText("Time: 0.0");
		}
		
		//Render the frame
		Graphics g = image.getGraphics();
		level.render(g);
		g.drawRect(0, 0, 200, 200); //draws our 10x10 grid
		g = getGraphics();
		g.drawImage(image, 0, 0, width, height, 0, 0, width, height, null);//generates the outer window
		g.dispose();
		bombCount.setText("Bombs:" + Level.gameCounter);
	}


	//when thread.start() is called, it automatically jumps to this function
	public void run(){
		gameStart=0;
		new Tile();
		windowrunning = true;
		running = true;
		level = new Level();
		
		image = createVolatileImage(width,height);

		//Run the application until the user tells it to close
		while(windowrunning){
			if(gameStart==1){
				startTime = System.currentTimeMillis();
				gameStart++;
			}
			
			if(running)
				currentTime = System.currentTimeMillis();
			
			update();//go to update()
			render();//renders the grid after checking conditions and changing tile images

			//If you hit a bomb, end the game
			if(hitBomb){
				Level.revealBombs(Level.grid);
				update();
				render();
				Level.endGame(false);
				hitBomb=false;
			}
			
			//If you win, end the game
			if(gamewon){
				update();
				render();
				stopTime = System.currentTimeMillis();
				Level.endGame(true);
				gamewon=false;
			}
			
			//Close the window
			if(gtfo){
				windowrunning=false;
				j.setVisible(false);
				j.dispose();
			}
			
			//Wait a bit so as not to render more than necessary
			try{
				Thread.sleep(15);
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		
		//Close the window when the application has been told to stop running
		if(gtfo){
			windowrunning=false;
			j.setVisible(false);
			j.dispose();
		}
	}

	//Reset the game board and current time/numbombs
	public static void resetGame(){	
		level=new Level();
		Grid.numRevealed = 0;
		hitBomb=false;
		startTime=System.currentTimeMillis();
		ClickListener.useMouse=true;
		running = true;
		gameStart=0;
	}

	//Write high scores after game has been won
	public static void writeScore(String name, long score){
		try{
			fw = new FileWriter(file,true);
			bw = new BufferedWriter(fw);
			bw.write(name);
			bw.newLine();
			bw.write("" + score);
			bw.newLine();
			bw.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	//Mouse Event checkers
	public static boolean MouseLeft(){//checks for left click
		return ClickListener.MouseLeft();
	}
	public static boolean MouseRight(){//checks for right click
		return ClickListener.MouseRight();
	}

}
