import java.awt.Rectangle;
import java.awt.Graphics;

public class Level {
	public static int W = 10, H=10;
	
	public static Grid[][] grid = new Grid[W][H];
	
	public Level(){
		generateGrid();
	}
	
	public void generateGrid(){
		grid = new Grid[W][H];
		
		for(int y=0;y<grid.length;y++)
			for(int x=0; x<grid[0].length; x++)
				grid[x][y] = new Grid(new Rectangle(x*Tile.size,y*Tile.size,Tile.size,Tile.size),Tile.white);
	}
	
	public void render(Graphics g){
		for(int y=0;y<grid.length;y++)
			for(int x=0; x<grid[0].length; x++)
				grid[x][y].render(g);
	}
}
