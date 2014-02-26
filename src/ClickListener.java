import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class ClickListener implements MouseListener,MouseMotionListener{
	public static  boolean mouseRight = false;
	private static  boolean mouseLeft = false;

	public static boolean MouseLeft(){
		return mouseLeft;
	}

	public static boolean MouseRight(){
		return mouseRight;
	}

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
			mouseLeft = true;
		}
		else if(e.getButton() == MouseEvent.BUTTON2){
			mouseRight = true;
		}
	}


	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1){
			mouseLeft = false;
		}
		else if(e.getButton() == MouseEvent.BUTTON2){
			mouseRight = false;
		}
	}


	public void mouseDragged(MouseEvent e) {
		Game.point.setLocation(e.getX(),e.getY());
	}


	public void mouseMoved(MouseEvent e) {
		Game.point.setLocation(e.getX(),e.getY());
	}

}
