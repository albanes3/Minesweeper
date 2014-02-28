import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class ClickListener implements MouseListener,MouseMotionListener{
	private static  boolean mouseRight = false;
	private static  boolean mouseLeft = false;
	public static boolean useMouse=true;

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
		if(useMouse){
			if(e.getButton() == MouseEvent.BUTTON1){
				mouseLeft = true;
			}
			else if(e.getButton() == MouseEvent.BUTTON3){
				mouseRight = true;
			}
		}
	}


	public void mouseReleased(MouseEvent e) {
		if(useMouse){
			if(e.getButton() == MouseEvent.BUTTON1){
				mouseLeft = false;
			}
			else if(e.getButton() == MouseEvent.BUTTON3){
				mouseRight = false;
			}
		}
	}


	public void mouseDragged(MouseEvent e) {
		if(useMouse){
			Game.point.setLocation(e.getX(),e.getY());
		}
	}


	public void mouseMoved(MouseEvent e) {
		if(useMouse){
			Game.point.setLocation(e.getX(),e.getY());
		}
	}

	public static void flipMR(){
		mouseRight=!mouseRight;
	}

	public static void falsifyML(){
		mouseLeft=false;
	}

}
