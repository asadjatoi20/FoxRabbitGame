package fox;

import javax.swing.JFrame;


public class fox extends JFrame {
 
	public fox() {
		add(new model());
	}
	
	
	public static void main(String[] args) {
		fox pac = new fox();
		pac.setVisible(true);
		pac.setTitle("FOX VS RABBIT");
		pac.setSize(500,600);
		pac.setDefaultCloseOperation(EXIT_ON_CLOSE);
		pac.setLocationRelativeTo(null);
		
	}

}
