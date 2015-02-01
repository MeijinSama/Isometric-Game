package com.twinpixels.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.*;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable{	
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 320;
	public static final int HEIGHT = 240;
	public static final int SCALE = 2;
	
	private boolean keepRunning = true;
	private BufferedImage screenImage;
	private Bitmap screenBitmap;
	
	public Game(){
		Dimension size = new Dimension(WIDTH*SCALE, HEIGHT*SCALE);
		 
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
	}
	
	public void start(){
		new Thread(this, "Game Thread").start();
	}
	
	public void stop(){
		keepRunning = false;
	}
	
	public void init(){
		Art.init();
		screenImage = new BufferedImage(WIDTH,HEIGHT, BufferedImage.TYPE_INT_ARGB);
		screenBitmap = new Bitmap(screenImage);
	}
	
	public void run(){
		init();
		
		while(keepRunning){
			tick();
			render(screenBitmap);
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			swap();
		}
	}
	
	private void swap() {
		BufferStrategy bs = getBufferStrategy();
		if(bs==null){
			this.createBufferStrategy(2);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		int screenW = getWidth();
		int screenH = getHeight();
		int W = WIDTH*SCALE;
		int H = HEIGHT*SCALE;
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, screenW, screenH);
		g.drawImage(screenImage, (screenW*W)/2, (screenH*H)/2, W, H, null);
		g.dispose();
	}

	private void render(Bitmap screen) {
		screen.draw(Art.i.sprites[0][0], 0, 0);
	}

	private void tick() {
		
	}

	public static void main(String[] args){
		Game gameComponent = new Game();
		
		JFrame frame = new JFrame("Game");
		frame.add(gameComponent);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		gameComponent.start();
	}
}
