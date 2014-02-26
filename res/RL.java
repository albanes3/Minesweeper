import java.awt.Image;
import java.awt.Toolkit;

public class RL {

	static RL loader = new RL();

	public static Image getImage(String filename){
		return Toolkit.getDefaultToolkit().getImage(loader.getClass().getResource(filename));
	}
}
//resource loader for images
