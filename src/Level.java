import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;


public class Level {

	static int range = 100;
	public static int W = 10, H=10;
	static int[][] bombs = new int[10][2]; //an array holding the coordinates of each bomb
	private boolean[][] visited = new boolean[10][10];
	//^used for clearAdjTiles() and for permitting against right clicks after a tile has been revealed
	public static Grid[][] grid = new Grid[W][H];

	public Level(){	
		generateGrid();//initialize all tiles to be blank
		for(int i=0;i<10;i++)
			for(int j=0; j<2; j++)
				bombs[i][j]=-1;//initialize array to avoid NullPointerException
		for(int i=0;i<10;i++)
			for(int j=0; j<10; j++)
				visited[i][j]=false;//initialize array to avoid NullPointerException
		setBombs();//generate 10 coordinates which have isBomb=true
		setRanks();//after generating our 10 bombs, increment tiles adjacent to each bomb
	}

	public static boolean MouseLeft(){
		return ClickListener.MouseLeft();
	}

	public static boolean MouseRight(){
		return ClickListener.MouseRight();
	}

	public void generateGrid(){//initial rendering of grid; sets all tiles to blank
		for(int y=0;y<grid.length;y++){
			for(int x=0; x<grid[0].length; x++){
				grid[x][y] = new Grid(new Rectangle(x*Tile.size,y*Tile.size,Tile.size,Tile.size),Tile.blank);
			}
		}

	}

	public void setRanks(){
		for(int y=0;y<grid.length;y++){
			for(int x=0; x<grid[0].length; x++){
				if(grid[x][y].isBomb){//found a bomb? get to incrementin'
					if(x>0){//if(tileIsAwayFromLeftEdge)
						grid[x-1][y].rank++;//left tile
						if(y>0)
							grid[x-1][y-1].rank++;//upper left tile
						if(y<9)
							grid[x-1][y+1].rank++;//lower left tile
					}
					if(x<9){//if(tileIsAwayFromRightEdge)
						grid[x+1][y].rank++;//right tile
						if(y>0)
							grid[x+1][y-1].rank++;//upper right tile
						if(y<9)
							grid[x+1][y+1].rank++;//lower right tile
					}
					if(y>0)//if(tileIsAwayFromUpperEdge)
						grid[x][y-1].rank++;//upper tile
					if(y<9)//if(tileIsAwayFromLowerEdge)
						grid[x][y+1].rank++;//lower tile

				}


			}
		}

	}

	public void setBombs(){
		boolean duplicateBomb=true; // checks for bombs with the same x,y coordinates 
		Random numGenerator = new Random();
		int xcoord,ycoord;
		for(int numBombs=0;numBombs<10;numBombs++){
			xcoord = numGenerator.nextInt(10);
			ycoord = numGenerator.nextInt(10);
			duplicateBomb=true;
			while(duplicateBomb){//keep generating random numbers until a unique coordinate is found
				for(int j=0;j<numBombs;j++){
					if(xcoord==bombs[j][0] && ycoord==bombs[j][1]){//if a bomb exists at this set of coords, generate new coords and break out
						duplicateBomb=true;
						xcoord = numGenerator.nextInt(10);
						ycoord = numGenerator.nextInt(10);
						break;
					}
					duplicateBomb=false;//no ? fahgettabotit - http://www.youtube.com/watch?v=z-CL9qVG9iI
				}
				if(!duplicateBomb || numBombs==0){//unique coordinates are stored in bombs[][]; loop is ended
					bombs[numBombs][0]=xcoord;
					bombs[numBombs][1]=ycoord;
					break;
				}	
			}
			grid[xcoord][ycoord].isBomb=true;// set boolean to show this tile is a bomb
		}
	}

	public void render(Graphics g){
		for(int y=0;y<grid.length;y++)
			for(int x=0; x<grid[0].length; x++)
				grid[x][y].render(g);//draws each individual tile
	}

	public void update(){
		click();
	}

	public void click(){
		for(int x=0;x<grid.length;x++){
			for(int y=0; y<grid[0].length; y++){
				if(MouseRight()){
					if(grid[x][y].contains(new Point(Game.point.x,Game.point.y))){
						if(!visited[x][y]){//only toggle if the tile has not been revealed
							grid[x][y].toggle();
						}
						ClickListener.flipMR();
					}	
				}
				if(MouseLeft()){//left click
					if(grid[x][y].id==Tile.blank || grid[x][y].id==Tile.qmark){//if the tile has not already been revealed
						if(grid[x][y].contains(new Point(Game.point.x,Game.point.y))){//checks whether or not the mouse click is contained inside the rectangle
							visited[x][y]=true;
							if(grid[x][y].revealTile()){//if the tile is rank zero, reveal adjacent tiles
								clearAdjTiles(x,y);
							}
							if(grid[x][y].id==Tile.bomb){//if a bomb is encountered, reveal all bombs and stop running the game
								revealBombs();
								Game.running=false;
							}
						}

					}
				}

			}
		}
	}

	private void revealBombs() {//go through list of bombs coords and reveal tiles
		for(int i=0;i<10;i++){
			this.grid[bombs[i][0]][bombs[i][1]].revealTile();
		}
	}

	private void clearAdjTiles(int x, int y) {//recursively clears tiles around those of rank zero
		if(x>0){//if(tileIsAwayFromLeftEdge)
			if(grid[x-1][y].id!=Tile.flag)
				if(grid[x-1][y].revealTile()  && !visited[x-1][y]){
					visited[x-1][y]=true;
					clearAdjTiles(x-1,y);//left tile
				}
			if(y>0)
				if(grid[x-1][y-1].id!=Tile.flag)
					if(grid[x-1][y-1].revealTile() && !visited[x-1][y-1]){
						visited[x-1][y-1]=true;
						clearAdjTiles(x-1,y-1);//upper left tile
					}
			if(y<9){
				if(grid[x-1][y+1].id!=Tile.flag)
					if(grid[x-1][y+1].revealTile() && !visited[x-1][y+1]){
						visited[x-1][y+1]=true;
						clearAdjTiles(x-1,y+1);//lower left tile
					}
			}
		}
		if(x<9){//if(tileIsAwayFromRightEdge)
			if(grid[x+1][y].id!=Tile.flag)
				if(grid[x+1][y].revealTile() && !visited[x+1][y]){
					visited[x+1][y]=true;
					clearAdjTiles(x+1,y);//right tile
				}
			if(y>0)
				if(grid[x+1][y-1].id!=Tile.flag)
					if(grid[x+1][y-1].revealTile() && !visited[x+1][y-1]){
						visited[x+1][y-1]=true;
						clearAdjTiles(x+1,y-1);//upper right tile
					}
			if(y<9)
				if(grid[x+1][y+1].id!=Tile.flag)
					if(grid[x+1][y+1].revealTile() && !visited[x+1][y+1]){
						visited[x+1][y+1]=true;
						clearAdjTiles(x+1,y+1);//lower right tile
					}
		}
		if(y>0)//if(tileIsAwayFromUpperEdge)
			if(grid[x][y-1].id!=Tile.flag)
				if(grid[x][y-1].revealTile() && !visited[x][y-1]){
					visited[x][y-1]=true;
					clearAdjTiles(x,y-1);//upper tile
				}
		if(y<9)//if(tileIsAwayFromLowerEdge)
			if(grid[x][y+1].id!=Tile.flag)
			if(grid[x][y+1].revealTile() && !visited[x][y+1]){
				visited[x][y+1]=true;
				clearAdjTiles(x,y+1);//lower tile
			}
	}
}