import java.awt.Rectangle;
import java.awt.Graphics;

public class Grid extends Rectangle{
	private static final long serialVersionUID = 1L;
	public int[] id = {-1, -1};
	
	public Grid(Rectangle size, int[] id){
		setBounds(size);
		this.id=id;
	}
	
	public void render(Graphics g){
		g.drawImage(Tile.image, x, y, x+width, y+height, id[0]*Tile.size,id[1]*Tile.size, (id[0]+1)*(Tile.size), (id[1]+1)*(Tile.size),null);
	}
}
