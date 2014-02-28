import java.awt.Rectangle;
import java.awt.Graphics;

public class Grid extends Rectangle{
	private static final long serialVersionUID = 1L;
	public int[] id = {-1, -1};
	public int rank = 0;
	public boolean isBomb=false;
	public static int numRevealed = 0;
	private int status = 0; //represents visual state to user - blank (0), marked (1), ? (2), or revealed (3)

	public Grid(Rectangle size, int[] id){
		setBounds(size);
		this.id=id;
	}

	public void render(Graphics g){
		g.drawImage(Tile.image, x, y, x+width, y+height, id[0]*Tile.size,id[1]*Tile.size, (id[0]+1)*(Tile.size), (id[1]+1)*(Tile.size),null);
	}

	public void toggle(){ //changes the state of the tile between unmarked, marked, and ?
		if (this.id != Tile.blank && this.id != Tile.flag && this.id != Tile.qmark)
			return;
		if(status == 2){
			this.id=Tile.blank;
			status = 0;
			return;
		}
		else if(status == 1){
			this.id=Tile.qmark;
			Level.gameCounter++;
		}
		else {
			Level.gameCounter--;
			this.id=Tile.flag;
		}
		status++;
	}

	public boolean revealTile(){//sets the image of the tile based on rank and the isBomb boolean
		ClickListener.falsifyML();
		if(this.id==Tile.flag)
			return false;
		if(this.id!=Tile.blank && this.id!=Tile.qmark)
			return false;
		if(this.isBomb){//checks for isBomb boolean
			this.id=Tile.bomb;
			return false;
		}
		else if(this.rank==1)
			this.id=Tile.one;
		else if(this.rank==2)
			this.id=Tile.two;
		else if(this.rank==3)
			this.id=Tile.three;
		else if(this.rank==4)
			this.id=Tile.four;
		else if(this.rank==5)
			this.id=Tile.five;
		else if(this.rank==6)
			this.id=Tile.six;
		else if(this.rank==7)
			this.id=Tile.seven;
		else if(this.rank==8)
			this.id=Tile.eight;
		else{
			this.id=Tile.zero;
			numRevealed++;
			return true;
		}
		numRevealed++;
		return false;
	}
}
