import java.awt.Image;

public class Tile {
	public static int[] red = {0,0};
	public static int[] white = {3,0};
	public static int size = 20;
	private static int rank = 0; //how many mines are adjacent?
	public static boolean mine = 0; //is this a mine?
	public static int status = 0; //represents visual state to user - blank (0), marked (1), ? (2), or revealed (3)
	public static Image image;
	
	private static int x_loc; //x location on the board
	private static int y_loc; //y location on the board
	
	public Tile(){
		image = RL.getImage("tile.jpg");
	}
	
	//Increase the tile's rank when a mine is placed
	public void rankUp(){
		if(!mine == 1){
			rank++;
		}
	}
	
	//return the rank of the tile
	public int getRank(){
		return rank;
	}
	
	//reveal the tile
	public void reveal(){
		status = 3; //set revealed status
		if(mine) this.explode(); //boom.
		else if(rank == 0){ //if 0 tile, reveal surrounding tiles
			//TODO: get surrounding tiles from grid and call this method on them
		}
	}
	
	private explode(){
		//TODO: Tell Game object the game has ended.
	}
}
