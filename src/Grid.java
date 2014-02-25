import java.awt.Rectangle;
import java.awt.Graphics;

public class Grid extends Rectangle{
	private static final long serialVersionUID = 1L;
	public int[] id = {-1, -1};
	private Tile[][] board = new Tile[10][10]; //stores all tiles on the board
	private Tile[] mines = new Tile[10]; //stores placed mines for easy reveal after game over
	
	//initialize the grid to contain all blank tiles on the board, then populate them with mines and ranks
	public init(){
		//create grid of blank tiles
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){ //i is y coordinate, j is x
				board[i][j] = new Tile(); //create a tile object for this location on the board
				board[i][j].setLoc(j, i); //tell the tile where it is on the board
			}
		}
		
		//populate tiles with mines (and increment ranks for surrounding tiles)
		for(int i = 0; i < 10; i++){
			int x = Math.random() * 9; //generates integer between 0 and 9
			int y = Math.random() * 9; //generates integer between 0 and 9
			
			if(!board[y][x].isMine()){ //check that the tile is not already a mine
				board[y][x].setMine(); //place a mine on the tile
				mines[i] = board[y][x]; //add tile to local database of mines
				for(int i = -1; i <= 1; i++){ //increment surrounding tiles' rank
					for(int j = -1; j<=1; j++){
						//increase rank by one (ignores tiles that have mines, and tiles that don't exist)
						if(y+j >=0 && x+i >=0 && y+j < 10 && x+i <=10)
							board[y+j][x+i].rankUp();
					}
				}
			}
			else{
				i--; //Don't place a mine (on top of a mine) and repeat this step
			}
		}
	}
	
	//reveal all mines when the game has ended
	public void explode(){
		for(int i = 0; i < 10; i++){
			mines[i].reveal();
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
