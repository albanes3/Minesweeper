import java.awt.Rectangle;
import java.awt.Graphics;

public class Grid extends Rectangle{
	private static final long serialVersionUID = 1L;
	public int[] id = {-1, -1};
	public int rank = 0;
	public boolean isBomb=false;
	private static int status = 0; //represents visual state to user - blank (0), marked (1), ? (2), or revealed (3)
	
	public Grid(Rectangle size, int[] id){
		setBounds(size);
		this.id=id;
	}
	
	public void render(Graphics g){
		g.drawImage(Tile.image, x, y, x+width, y+height, id[0]*Tile.size,id[1]*Tile.size, (id[0]+1)*(Tile.size), (id[1]+1)*(Tile.size),null);
	}
	
	public void toggle(){ //changes the state of the tile between unmarked, marked, and ?
		if(status == 2){
			this.id=Tile.blank;
			status = 0;
			return;
		}
		else if(status == 1)
			this.id=Tile.qmark;
		else 
			this.id=Tile.flag;
		status++;
		//TODO:
		//	right click does not initiate this method correctly; check click() method inside of Level class
}
	
	public int[] revealTile(){//sets the image of the tile based on rank and the isBomb boolean
		if(this.isBomb)//checks for isBomb boolean
			return Tile.bomb;
		if(this.rank==1)
			return Tile.one;
		if(this.rank==2)
			return Tile.two;
		if(this.rank==3)
			return Tile.three;
		if(this.rank==4)
			return Tile.four;
		if(this.rank==5)
			this.id=Tile.five;
		if(this.rank==6)
			return Tile.six;
		if(this.rank==7)
			return Tile.seven;
		if(this.rank==8)
			return Tile.eight;
		return Tile.zero;
		//TODO:
			//change the code so it directly edits the Grid object (i.e. this.id=Tile.bomb); I had issues getting this to work; 
				// - this will also require changes to the click() method in the Level class
			//instead return a boolean based on whether it should check for tiles with no adjacent bombs
	}
}
