import java.awt.Rectangle;
import java.awt.Graphics;

public class Grid extends Rectangle{
	private static final long serialVersionUID = 1L;
	public int[] id = {-1, -1};
	private Tile[][] board = new Tile[10][10];
	
	//initialize the grid to contain all blank tiles on the board, then populate them with mines and ranks
	public init(int board_width, int board_height, int num_mines){
		//create grid of blank tiles
		for(int i = 0; i < board_height; i++){
			for(int j = 0; j < board_width; j++){
				board[i][j] = new Tile(); //create a tile object for this location on the board
			}
		}
		
		//populate tiles with mines (and simultaneously increment ranks for surrounding tiles)
		for(int i = 0; i < num_mines; i++){
			int x = (Math.random() * board_width) - 1;
			int y = (Math.random() * board_height) - 1;
			
			if(!board[y][x].isMine()){ //check that the tile is not already a mine
				board[y][x].setMine(); //place a mine on the tile
				for(int i = -1; i <= 1; i++){
					for(int j = -1; j<=1; j++){
						board[y+j][x+i].rankUp(); //increase rank by one (ignores tiles that have mines)
					}
				}
			}
			else{
				i--; //Don't place a mine (on top of a mine) and repeat this step
			}
		}
	}
	
	public Grid(Rectangle size, int[] id){
		setBounds(size);
		this.id=id;
	}
	
	public void render(Graphics g){
		g.drawImage(Tile.image, x, y, x+width, y+height, id[0]*Tile.size,id[1]*Tile.size, (id[0]+1)*(Tile.size), (id[1]+1)*(Tile.size),null);
	}
}
