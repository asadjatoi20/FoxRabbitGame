package fox;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class model extends JPanel implements ActionListener {

	//Variable to be used later
	
	private Dimension d; //Dimension of Frame
    private final Font smallFont = new Font("Arial", Font.BOLD, 14); // font used for score

    private final int BLOCK_SIZE = 22; //pixel size of each block
    private final int N_BLOCKS = 21; // number of x and y block..  20 for game, 1 for border.
    private final int SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE;
    private int rabbit_x[]= new int[100];  // array to generate future x coordinates of rabbits
    private int rabbit_y[]= new int[100]; //  array to generate future y coordinates of rabbits
    private int rabbit_num; // rabbits count
    private int fox_x, fox_y; // fox initial coordinates 
    private int moves, score; //moves of fox and score count such as rabbits eaten

    private Image bush, fox, rabbit; 
    
    
    // Random Function to generate random position of rabbits spawn
    static int getRandom(int min , int max) {
		Random r = new Random();
		return r.nextInt((max-min) + 1) + min;
	}
    
    //constructor 
    model(){
    	
    	loadImages();
        initVariables();
        addKeyListener(new TAdapter()); 
        setFocusable(true);  //  used to always listen keys.

    }
    
    
    //initialize variable
    private void initVariables() {

        d = new Dimension(800, 800);
        fox_x = 10; fox_y=10;
        score=0;
        moves=0;
        rabbit_num=3;
        for(int i=0; i<rabbit_num;i++) {
        rabbit_x[i]= getRandom(1,20);
        rabbit_y[i]= getRandom(1,20);}
      
    }
    
    //load images
private void loadImages() {
    	
        bush = new ImageIcon("bush.png").getImage();
        fox = new ImageIcon("ghost.gif").getImage();
        rabbit = new ImageIcon("rabbit.png").getImage();


    }
    
    //defualt method for Graphics 2D library
public void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g;
    
    g2d.setColor(new Color(0, 181, 27));
    g2d.fillRect(0, 0, d.width, d.height);
    drawMaze(g2d);
    drawScore(g2d);
    drawRabbit(g2d);
    checkScore();
    Toolkit.getDefaultToolkit().sync();
    g2d.dispose();
}


//check win or loss condition
public void checkStatus() {
	if(rabbit_num>=50) {
		JOptionPane.showMessageDialog(null, "YOU LOSS!");
		System.exit(0);
	}
	if( rabbit_num==0) {
		JOptionPane.showMessageDialog(null, "YOU WIN!");
		System.exit(0);

	}
}
    


public void drawRabbit(Graphics2D g){
	for(int i=0; i<rabbit_num; i++)
	g.drawImage(rabbit, rabbit_x[i] *22, rabbit_y[i]*22,this);
	
}


//when fox eats rabit it removes rabbit and updates score
public void checkScore() {
	for(int i=0; i<rabbit_num; i++) {
		if(rabbit_x[i]== fox_x && rabbit_y[i]== fox_y) {
			score++;
			rabbit_num--;
			rabbit_x[i]=99;
			rabbit_y[i]=99;
		}
	}
}


//updates rabbits position each time fox moves
public void updateRabbit(){
	for(int i=0; i<rabbit_num; i++) {
		int temp = getRandom(0,4);

		if(temp==0 && rabbit_x[i]!=20)
		  rabbit_x[i]++;
		if(temp==1 && rabbit_y[i]!=20)
		rabbit_y[i]++;
		if(temp==2 && rabbit_x[i]!=1)
			rabbit_x[i]--;
		if(temp==3 && rabbit_y[i]!=1)
			rabbit_y[i]--;


	}
}
  

//generate new rabit after every 3 moves
public void generateRabbit() {
		
		generateRabbits();
		/*if(moves%3==0) {
			rabbit_x[rabbit_num]=getRandom(1,20);
			rabbit_y[rabbit_num]=getRandom(1,20);
			rabbit_num++;
		}*/
	
	}
// method to generate double of the rabbits
 public void generateRabbits() {
	 if(moves%3==0) {
		 int temp = rabbit_num;
		 for(int i=0; i<temp; i++) {
			 rabbit_x[rabbit_num]=getRandom(1,20);
			 rabbit_y[rabbit_num]=getRandom(1,20);
			 rabbit_num++;
		 }
	 }
 }

//draw the border
private void drawMaze(Graphics2D g2d) {

	       for(int i=0 ; i<=462; i+=22) {
	    	   g2d.drawImage(bush, i,0,this);
	    	   g2d.drawImage(bush, 0,i,this);
	    	   g2d.drawImage(bush, i,462,this);
	    	   g2d.drawImage(bush, 462,i,this);

	      }
	       if(fox_x!=0 && fox_x!=0)
	    g2d.drawImage(fox,fox_x*22,fox_y*22,this);       
	    }
	 
//key listener	 
class TAdapter extends KeyAdapter {

	        @Override
	        public void keyPressed(KeyEvent e) {

	            int key = e.getKeyCode();

	            if (true) {
	                if (key == KeyEvent.VK_LEFT && fox_x!=1 ) {
	                	--fox_x;
	                	++moves;
	                	updateRabbit();
	                	generateRabbit();
	                    checkStatus();
	                    
	                } else if (key == KeyEvent.VK_RIGHT &&  fox_x!=20  ) {
	                	++fox_x;
	                	++moves;
	                	updateRabbit();
	                	generateRabbit();
	                    checkStatus();

	                } else if (key == KeyEvent.VK_UP && fox_y!=1 ) {
	                	--fox_y;
	                	++moves;
	                	updateRabbit();
	                	generateRabbit();

	                } else if (key == KeyEvent.VK_DOWN  && fox_y!=20 ) {
	                    ++fox_y;
	                	++moves;
	                	updateRabbit();
	                	generateRabbit();
	                    checkStatus();  } 
	            } 
	        }
	}

// display score nd other info
private void drawScore(Graphics2D g) {
	        g.setFont(smallFont);
	        g.setColor(Color.black);
	        String s = "Rabbits in Medow: " + rabbit_num+ "   Rabbits Eaten :   "+ score  +"   You are on Tile : " + (fox_x-1) + "," + (fox_y-1);
	        g.drawString(s, 2, SCREEN_SIZE + 50);
	        
	    }


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
