import java.awt.*;
import java.io.*;

import javax.swing.*;

public class Game extends Canvas implements Runnable{
	public static boolean gtfo=false;
	public static boolean hitBomb=false;
	private static final long serialVersionUID = 1L;
	public static int width = 290;
	public static int height = width-35;
	public static Dimension size = new Dimension(width,height);
	private Image image;
	public static boolean gamewon=false;
	public static File file = new File("topten.txt");
	public static Level level;
	public static JFrame j;
	public static JTextArea bombCount, timeCount;
	public static long startTime,currentTime,stopTime;
	private static FileWriter fw;
	public static int gameStart=0;

	public static Point point = new Point(0,0);
	private static BufferedWriter bw;
	public static String[] toptens = new String[10];

	public static boolean running = false;
	public static boolean windowrunning = false;

	public Game(){
		//create file
		try{
			if (!file.exists()) {
				file.createNewFile();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		for(int i=0; i<toptens.length;i++)
			toptens[i]="content";


		addMouseListener(new ClickListener());
		addMouseMotionListener(new ClickListener());
	}
	public static boolean MouseLeft(){//checks for left click
		return ClickListener.MouseLeft();
	}

	public static boolean MouseRight(){//checks for right click
		return ClickListener.MouseRight();
	}

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

	public void start(){
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



	public void update(){
		level.update();//calls click() inside of Level class to check for new clicks
	}

	public void render(){
		if(gameStart>0){
			String time = new String("Time: " + (int)((currentTime-startTime)/100)/10 + "." + (int)((currentTime-startTime)/100)%10);
			timeCount.setText(time);
		}
		else{
			timeCount.setText("Time: 0.0");
		}
		Graphics g = image.getGraphics();
		level.render(g);
		g.drawRect(0, 0, 200, 200); //draws our 10x10 grid
		g = getGraphics();
		g.drawImage(image, 0, 0, width, height, 0, 0, width, height, null);//generates the outer window
		g.dispose();
		
		bombCount.setText("Bombs:" + Level.gameCounter);
	}



	public void run(){//when thread.start() is called, it automatically jumps to this function
		gameStart=0;
		new Tile();
		windowrunning=true;
		running = true;
		level = new Level();
		
		image = createVolatileImage(width,height);

		while(windowrunning){
			if(gameStart==1){
				startTime = System.currentTimeMillis();
				gameStart++;
			}
			if(running)
				currentTime = System.currentTimeMillis();
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
				stopTime = System.currentTimeMillis();
				Level.endGame(true);
				gamewon=false;
			}
			if(gtfo){
				windowrunning=false;
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
		startTime=System.currentTimeMillis();
		ClickListener.useMouse=true;
		running = true;
		gameStart=0;
	}

}
