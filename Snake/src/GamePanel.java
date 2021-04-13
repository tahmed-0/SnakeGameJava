//Tufael Ahmed
//Created on 4.5.21

import java.awt.event.*;


import javax.swing.JPanel;

import java.awt.*;

import javax.swing.*;
import java.util.Random;



public class GamePanel extends JPanel implements ActionListener {
	
			
		
	
	
	
	static final int Screen_Width = 600;
	static final int Screen_Height = 600;
	
	static final int Unit_Size = 25;
	static final int Game_Units = (Screen_Width*Screen_Height)/Unit_Size;
	static final int DELAY = 75;
	final int x[] = new int[Game_Units]; //all the x coordinates
	final int y[] = new int[Game_Units]; //all the y coordinates
	
	int bodyParts = 6;
	int applesEaten;
	int appleX;
	int appleY;
	
	char directions = 'R';  //R U L D
	boolean running = false;
	Timer timer;
	
	Random random;
	
	
	
	
	
	GamePanel() {
		random = new Random();
		this.setPreferredSize(new Dimension(Screen_Width, Screen_Height));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
		
	}
	
	
	
	public void startGame() {
		
		newApple();
		running = true;
		timer = new Timer(DELAY, this);
		
		timer.start();
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);

	}
	
	public void draw(Graphics g) {
		if(running) {

		
		for(int i = 0; i < Screen_Height/Unit_Size; i++) {
			
			//optional grid line
			
			//Y axis
			//g.drawLine(i*Unit_Size, 0, i*Unit_Size, Screen_Height);
			
			//X axis
			//g.drawLine(0, i*Unit_Size, Screen_Width, i*Unit_Size);
			//g.setColor(Color.gray);
			
			
		}
		//apple
		g.setColor(Color.red);
		g.fillOval(appleX, appleY, Unit_Size, Unit_Size);
		
		//drawing snake
		
		for(int i = 0; i < bodyParts; i++) {
			if(i == 0) {
				g.setColor(Color.green);
				g.fillRect(x[i], y[i], Unit_Size, Unit_Size);
			}
			else {
				g.setColor(new Color(45,180,0));
				
				//random colors on snake
				//g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
				
				g.fillRect(x[i], y[i], Unit_Size, Unit_Size);
			}
		}
		
		//Score
		
		g.setColor(Color.yellow);
		g.setFont(new Font("Ink Free", Font.BOLD, 40));
		FontMetrics metrics =  getFontMetrics(g.getFont());
		g.drawString("Score: " +applesEaten, (Screen_Width - metrics.stringWidth("Score: " +applesEaten))/2, g.getFont().getSize());
		
		
	}
		else {
			gameOver(g);
		}
	
}
		
			
			
			
		
	public void newApple() {
		appleX = random.nextInt((int)(Screen_Width/Unit_Size))*Unit_Size;
		appleY = random.nextInt((int)(Screen_Height/Unit_Size))*Unit_Size;

		
		
	}
	
	public void move() {
		for(int i = bodyParts; i>0;i--) {
			x[i] = x[i - 1];
			y[i] = y[i - 1];
		}
		
		switch(directions) {
		
		case 'U':
			y[0] = y[0] - Unit_Size;
			break;
			
		case 'D':
			y[0] = y[0] + Unit_Size;
			break;
			
		case 'L':
			x[0] = x[0] - Unit_Size;
			break;
		case 'R':
			x[0] = x[0] + Unit_Size;
			break;
 		}
		
		
		
	}
	public void checkApple() {
		
		if((x[0] == appleX && y[0] == appleY)) {
			bodyParts++;
			
			applesEaten++;
			newApple();
		}
		
	}
	public void checkCollisions() {
		
		//checks if head collides with the body
		for(int i = bodyParts; i > 0; i--) {
			if((x[0] == x[i]) && (y[0] == y[i])) {
				running = false;
			}
		}
		
		//checks if head touches left border
		
		if(x[0] < 0) {
			running = false;
		}
		
		//checks if head touches right border
		
		if(x[0] > Screen_Width) {
			running = false;
		}
		
		//check if head touches top border
		
		if(y[0] < 0) {
			running = false;
		}
		
		//check if head touches bottom border
		
		if(y[0] > Screen_Height) {
			running = false;
		}
		
		if(!running) {
			timer.stop();
		}
		
		
		
	}
	public void gameOver(Graphics g) {
		
		//display the score after game over
		g.setColor(Color.yellow);
		g.setFont(new Font("Ink Free", Font.BOLD, 40));
		FontMetrics metrics1 =  getFontMetrics(g.getFont());
		g.drawString("Score: " +applesEaten, (Screen_Width - metrics1.stringWidth("Score: " +applesEaten))/2, g.getFont().getSize());
		
		
		//Game over text 
		
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 75));
		FontMetrics metrics2 =  getFontMetrics(g.getFont());
		g.drawString("Game Over", (Screen_Width - metrics2.stringWidth("Game Over"))/2, Screen_Height/2);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(running) {
			move();
			checkApple();
			checkCollisions();
			
			
		}
		repaint();
		

		
		
		
	}
	
	
	public class MyKeyAdapter extends KeyAdapter {
		@Override 
		
		public void keyPressed(KeyEvent e) {
			
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(directions != 'R' ) {
					directions  = 'L';
					
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(directions != 'L' ) {
					directions  = 'R';
					
				}
				break;
				
			case KeyEvent.VK_UP:
				if(directions != 'D' ) {
					directions  = 'U';
					
				}
				break;
				
			case KeyEvent.VK_DOWN:
				if(directions != 'U' ) {
					directions  = 'D';
					
				}
				break;
			}
			
		}
	}
	

}

//17.41
	


