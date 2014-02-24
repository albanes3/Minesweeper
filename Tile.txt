import java.awt.Image;

public class Tile {
	public static int[] red = {0,0};
	public static int[] white = {3,0};
	public static int size = 20;
	public static Image image;
	
	public Tile(){
		image = RL.getImage("tile.jpg");
	}
}
