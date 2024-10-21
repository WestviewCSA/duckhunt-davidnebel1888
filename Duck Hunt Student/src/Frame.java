import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Frame extends JPanel implements ActionListener, MouseListener, KeyListener {
	//Create an Asteroid Object
	Music bang = new Music("baseball_hit.wav", false);
	Music boing = new Music("boing_x.wav", false);
	Music chomp = new Music("croc_chomp_x.wav", false);
	Font bigFont = new Font("Serif", Font.BOLD, 100);
	Font medFont = new Font("Serif", Font.BOLD, 100);
	Crow crow = new Crow();
	Realskeledog dog = new Realskeledog(); //what is your dog object
	
	//create a Ground object
	Background b = new Background();
	
	//score related vars and timer
	int roundTimer;
	int score;
	long time;
	int currRound = 1;
	
	//init any variales, ojects etc for the "start"
	//of the game
	
	public void init()
	{
		//init the round timer and score
		roundTimer = 30;
		score = 0;
		time = 0;
		
		//stuff to init the duck1 object
		dog.setScale(.5, .5);
		
		//stuff to init the do object
		dog.setWidthHeight(200, 200);
		dog.setScale(2, 2);
		dog.setXY(0, 800);
		
		//gound state
		
		
		
		crow.setWidthHeight(200, 200);
		crow.setScale(3,3); 
		crow.setVx(3);
		crow.setVy(3);
		
		b.setScale(9.0, 6.0);
		b.setXY(0, 0);
		
	}
	//resetting for multiple rounds etc
	public void reset() {
		
	}
	
	public void nextRound() {
		
		//reset the roundCounter
		score = 0;
		roundTimer = 30;
		currRound++;
		
		//re-calibrate your objects
		crow.setXY(250, 250);
		
		//ideas: mabe additional objects appear?
		//thye start off off the screen like in-1000y
		//maybe previous he additional charaters have 0 vx and yand off the screen
		int randVx =(int)(Math.random()*(4))+1;
		int randVy =(int)(Math.random()*(4))+1;
		crow.setVx(randVx + currRound);
		crow.setVy(randVy + currRound);
	}
	public void paint(Graphics g) {
		super.paintComponent(g);
		
		
		b.paint(g);
		g.drawString(score + "", 100, 50);
		
		//add 16 to time since paint is called every time
		time += 20; //time elapse updates
		
		if(time%1000 == 0)
		{
			roundTimer -= 1;//has it been 1 second?
			if(roundTimer == 0)
			{
				nextRound();
				t.stop();//What do you do after one complete round
			}
		}
		if(roundTimer==30) {
			Font messageFont = new Font("Serif", Font.BOLD, 30);
			g.setFont(messageFont);
			g.drawString("Press the space bar for the next round", 50, 0);
			
			if(score==0)	{
				score++;
				Font scoreFont = new Font("Serif", Font.BOLD, 30);
				setFont(scoreFont);
				g.drawString("score is " + score, 100, 0);
			}
		}
		
		g.setFont(bigFont);
		
		g.drawString(""+this.roundTimer, 200, 100);
		g.setFont(medFont);
		g.drawString("Round "+this.currRound, 5, 700);
		//draw the round String
		
		//Layer your objects as you
		
		crow.paint(g);
		dog.paint(g);


		crow.paint(g);
		
		//logic for resetting he do position
		//and or making it bounce around

		
		
		if(crow.getX() > 700 || crow.getX()<=0) {
			crow.setVx(crow.getVx()*-1);
		}
		if(crow.getVx() == 0 && crow.getVy() > 0 && crow.getY() > 600) {
			//crow needs to stop moving!
			crow.setVy(0);
			crow.setVx(0);
			dog.setVy(-1); //dog goes up
			boing.play();

		}
		
		if(crow.getY() > 601 ||crow.getY() < 0) {
			crow.setVy(-crow.getVy());
//			crow.setX(200); //for respawning
//			crow.setY(150);
			boing.play();
 		}

		
		//dog should go back down once it hits an imaginary ceiling (aka its y location is just enougt
		if(dog.getY()< 440) { //you will need to play with different numbers here
			dog.setVy(3);
			
		}
		
		if(dog.getY()> 620 && dog.getVy()!=0 ) { //once the "dog" exits the screen again
			int randVx =(int)(Math.random()*(4))+1;
			int randVy =(int)(Math.random()*(4))+1;
			int randX =(int)(Math.random());
			int randY =(int)(Math.random());
				crow.setVx(randVx);
				crow.setVy(randVy);
				crow.setX(randX);
				crow.setY(randY);
				
				crow.setY(200);
				dog.setVy(0);
		}		
				
		
		
	}
	
	public static void main(String[] arg) {
		Frame c = new Frame();
	}
		
		
	public Frame() {
		JFrame f = new JFrame("Duck Hunt");
		f.setSize(new Dimension(900, 600));
		f.setBackground(Color.blue);
		f.add(this);
		f.setResizable(false);
		f.setLayout(new GridLayout(1,2));
		f.addMouseListener(this);
		f.addKeyListener(this);
		
		init(); //call our init method to give properties to the object and variables
		
		
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	
	//make the timer visible to the other methods
	Timer t = new Timer(16, this);
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
 
	@Override
	public void mousePressed(MouseEvent mouse) {
		// TODO Auto-generated method stub
		//perform a rectangle collision with the mouse and 
		//the object
		Rectangle rMouse = new Rectangle(mouse.getX(), mouse.getY(), 25, 25); //guess ad chekc size or mouse
		
		//2nd rectangle will be for your object (aka the duck)
		Rectangle rMain = new Rectangle(
				crow.getX(), crow.getY(),
				crow.getWidth(), crow.getHeight()
				);
		

			//check if they're colliding

		if(rMouse.intersects(rMain)) //do the 2 rects intersect?
		{
			score++;
		}	
			

			crow.setVy(10);
			crow.setVx(0);
			
			
			//there was a successful click!
			//dog needs to move in the same x position as your main character
			//make sure dog is currently off the screen (bottom - y location related
			//make the dog y velocity negative so it goes up
			dog.setX(crow.getX()); // may need to add some offset positive or negative to center
			dog.setY(500); //in case the dog is way in the abyss, lets bring it back to some Y position

			bang.play();
			chomp.play();
			
		}
	

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println(arg0.getKeyCode());
		
		//space bar continues the round
		//if the tier is stopped we can start it again
		if(arg0.getKeyCode()==32) {
			//start the timer again
			if(!t.isRunning()) {
				t.start();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
