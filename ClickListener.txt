import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class ClickListener implements MouseListener,MouseMotionListener{

	
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1){
			Game.mouseLeft = true;
		}
	}

	
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1){
			Game.mouseLeft = false;
		}
	}

	
	public void mouseDragged(MouseEvent e) {
		Game.point.setLocation(e.getX(),e.getY());
	}

	
	public void mouseMoved(MouseEvent e) {
		Game.point.setLocation(e.getX(),e.getY());
	}

}
