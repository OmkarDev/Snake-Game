package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import maths.Vector2f;
import window.Window;

public class Game extends Window implements KeyListener {

	private static final long serialVersionUID = 1L;

	/*
	 * Made by Omkar Dixit
	 * */
	
	int cols, rows;
	int w = 16;

	Vector2f vel;
	Vector2f head;
	ArrayList<Vector2f> list;
	int len = 0;

	Vector2f apple;

	public Game(int width, int height, String title) {
		super(width, height, title);
		cols = width / w;
		rows = height / w;
		vel = new Vector2f(0, 0);
		head = new Vector2f(0, 0);
		list = new ArrayList<Vector2f>();
		apple = randomPos();
	}

	public Vector2f randomPos() {
		int x = ThreadLocalRandom.current().nextInt(0, cols);
		int y = ThreadLocalRandom.current().nextInt(0, rows);
		return new Vector2f(x, y);
	}

	public void update() {
		for (int i = 0; i < list.size() - 1; i++) {
			Vector2f l = list.get(i);
			if(l.x == head.x && l.y == head.y) {
				len = 0;
				list = new ArrayList<Vector2f>();
			}
		}
		head.x += vel.x;
		head.y += vel.y;
		if(head.y < 0)head.y = rows-1;
		if(head.x < 0)head.x = cols-1;
		if(head.y > rows-1)head.y = 0;
		if(head.x > cols-1)head.x = 0;
		if (list.size() > len) {
			list.remove(0);
		}
		list.add(new Vector2f(head.x, head.y));
		if (head.x == apple.x && head.y == apple.y) {
			len++;
			apple = randomPos();
		}
	}

	public void draw(Graphics2D g) {
		background(g, 0xf5f5f5);
		g.setColor(new Color(0,200,0));
		g.fillRect((int) head.x * w, (int) head.y * w, w, w);
		g.setColor(Color.green);
		for (int i = 0; i < list.size() - 1; i++) {
			Vector2f l = list.get(i);
			g.fillRect((int) l.x * w, (int) l.y * w, w, w);
		}
		g.setColor(Color.red);
		g.fillRect((int) apple.x * w, (int) apple.y * w, w, w);
		grid(g);
		g.setColor(new Color(0,0,0,100));
		g.setFont(new Font("Ubuntu",Font.BOLD,30));
		g.drawString("Score: "+len, 10, 30);
	}

	public void grid(Graphics2D g) {
		g.setColor(Color.black);
		for (int i = 0; i < cols; i++) {
			g.drawLine(i * w, 0, i * w, rows * w);
		}
		for (int i = 0; i < rows; i++) {
			g.drawLine(0, i * w, cols * w, i * w);
		}
	}

	public static void main(String[] args) {
		Game game = new Game(640, 480, "Snake Game");
		game.addKeyListener(game);
		game.display();
	}

	public void keyTyped(KeyEvent e) {}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (vel.x <= 0) {
				vel.x = -1;
				vel.y = 0;
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (vel.x >= 0) {
				vel.x = 1;
				vel.y = 0;
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			if (vel.y <= 0) {
				vel.x = 0;
				vel.y = -1;
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (vel.y >= 0) {
				vel.x = 0;
				vel.y = 1;
			}
		}
	}

	public void keyReleased(KeyEvent e) {}

}
