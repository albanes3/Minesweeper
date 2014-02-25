import java.awt.*;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable{
	public static int width = 210;
	public static int height = width+20;
	public static Dimension size = new Dimension(width,height);
	private Image image;
	public static Level level;
	
	public static Point point = new Point(0,0);
	public static  boolean mouseLeft = false;
	
	public static boolean running = false;

	public Game(){
		addMouseListener(new ClickListener());
		addMouseMotionListener(new ClickListener());
	}
	
	public void start(){
		new Tile();
		running = true;
		level = new Level();
		Thread thread = new Thread(this);
		thread.start();
	}
	
	public void stop(){
		running = false;
	}

	public static void main(String args[]){
		Game game = new Game();
		JFrame j = new JFrame();
		
		j.setVisible(true);
		j.add(game);
		j.setPreferredSize(size);
		j.pack();
		j.setResizable(false);
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		game.start();
	}
	
	
	
	public void update(){
		level.update();
	}
	
	public void render(){
		Graphics g = image.getGraphics();
		level.render(g);
		g.drawRect(0, 0, 200, 200);
		
		g = getGraphics();
		g.drawImage(image, 0, 0, width, height, 0, 0, width, height, null);
		g.dispose();
	}
	
	public void run(){
		image = createVolatileImage(width,height);
		
		while(running){
			update();
			render();
			
			try{
				Thread.sleep(15);
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
