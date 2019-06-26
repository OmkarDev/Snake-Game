package window;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class Window extends JPanel {

	private static final long serialVersionUID = 1L;
	
	int width, height;
	String title;
	JFrame frame;

	public Window(int width, int height, String title) {
		this.width = width;
		this.height = height;
		this.title = title;
		frame = new JFrame();
		setFocusable(true);
		requestFocus();
	}

	public abstract void draw(Graphics2D g);

	public abstract void update();

	Timer time = new Timer();

	TimerTask update = new TimerTask() {

		@Override
		public void run() {
			update();
		}
	};

	TimerTask render = new TimerTask() {
		@Override
		public void run() {
			repaint();
			Toolkit.getDefaultToolkit().sync();
		}
	};

	public void run() {
		time.scheduleAtFixedRate(update, 1, 85);
		time.scheduleAtFixedRate(render, 1, 1);
	}

	public void paint(Graphics g) {
		draw((Graphics2D) g);
	}
	
	public void background(Graphics2D g,int col) {
		g.setColor(new Color(col));
		g.fillRect(0, 0, width, height);
	}

	public void display() {
		frame.setResizable(false);
		frame.setTitle(title);
		frame.add(this);
		frame.pack();
		frame.setSize(width, height + frame.getInsets().top);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		run();
	}
}
