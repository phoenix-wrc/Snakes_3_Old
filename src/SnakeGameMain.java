


import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;

public class SnakeGameMain extends JPanel implements ActionListener {
	
	public static JFrame jFrame;
	public static final int SCALE = 32;
	public static final int WIDTH = 20;
	public static final int HEIGHT = 20;
	public static int speed=7;
	
	
	Snake s = new Snake(5,6,5,5);
	Apple apple = new Apple(Math.abs((int) (Math.random()*SnakeGameMain.WIDTH-1)),Math.abs((int) (Math.random()*SnakeGameMain.HEIGHT-1)));
	Timer timer = new Timer(1000/speed,this);
	
	public SnakeGameMain() {
		timer.start();
		addKeyListener(new KeyBoard());
		setFocusable(true);
		
	}
	
	public void paint (Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0,0,SCALE*WIDTH,SCALE*HEIGHT);
		
		for (int x = 0; x < SCALE*WIDTH; x+=SCALE) {
			g.setColor(Color.white);
			g.drawLine(x, 0, x, SCALE*HEIGHT) ;
		}
		
		for (int y = 0; y < SCALE*HEIGHT; y+=SCALE) {
			g.setColor(Color.white);
			g.drawLine(0, y, SCALE*HEIGHT, y) ;
		}
		
		for (int l = 0; l < s.length; l++) {
			g.setColor(Color.GREEN);
			g.fillRect(s.sX[l]*SCALE+4, s.sY[l]*SCALE+4, SCALE-8, SCALE-8);
			g.setColor(Color.gray);
			g.fillRect(s.sX[0]*SCALE+3, s.sY[0]*SCALE+3, SCALE-6, SCALE-6);		
		}
		g.setColor(Color.PINK);
		g.fillOval(apple.posX*SCALE+4, apple.posY*SCALE+4, SCALE-8, SCALE-8);

	}
	
	public static void main(String[] args) {
		jFrame = new JFrame("Zмейка");
		jFrame.setSize(SCALE*WIDTH+7,SCALE*HEIGHT+30);
		
		jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		jFrame.setResizable(false);
		jFrame.setLocationRelativeTo(null);
		
		jFrame.add(new SnakeGameMain());
		
		jFrame.setVisible(true);
		

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		s.move();
		if ((s.sX[0] == apple.posX)&&(s.sY[0] == apple.posY)) {
			apple.setRandomPosition();
			s.length++;
		}
		for ( int l = 1 ; l<s.length; l++) {
			if((s.sX[l] == apple.posX)&&(s.sY[l] == apple.posY)) {
				apple.setRandomPosition();
			}
			if((s.sX[0] == s.sX[l])&&(s.sY[0] == s.sY[l])) {
				timer.stop();
				JOptionPane.showMessageDialog(null,"Ты проиграл, начать заново?");
				jFrame.setVisible(false);
				s.length = 2;
				s.direction = 0;
				
				apple.setRandomPosition();
				jFrame.setVisible(true);
				timer.start();
				
			}

		}
		repaint();
		
	}
	
	public class KeyBoard extends KeyAdapter {
		public void keyPressed (KeyEvent event) {
			int key = event.getKeyCode();
			
			if((key == KeyEvent.VK_UP) && (s.direction != 2)) s.direction = 0;
			if((key == KeyEvent.VK_DOWN) && (s.direction != 0)) s.direction = 2;
			if((key == KeyEvent.VK_RIGHT) && (s.direction != 3)) s.direction = 1;
			if((key == KeyEvent.VK_LEFT) && (s.direction != 1)) s.direction = 3;
		}
	}

}
