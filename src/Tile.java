import java.awt.Image;

public class Tile {
	public static int[] blank = {0,0};//coordinate system to be used when referring to 'image'
	public static int[] flag = {1,0};
	public static int[] zero = {3,0};
	public static int[] one = {0,1};
	public static int[] two = {1,1};
	public static int[] three = {2,1};
	public static int[] four = {3,1};
	public static int[] five = {0,2};
	public static int[] six = {1,2};
	public static int[] seven = {2,2};
	public static int[] eight = {3,2};
	public static int[] qmark = {4,0};
	public static int[] bomb = {2,0};

	public static int size = 20; // pixel width/height for each tile
	public static Image image; // source image for all tiles

	public Tile(){
		image = RL.getImage("tile2.jpg");
	}
}