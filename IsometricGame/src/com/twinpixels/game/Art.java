package com.twinpixels.game;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Art {
	public static Art i;
	
	public Art(){
		
	}
	
	public static void init(){
		i = new Art();
	}
	
	public Bitmap[][] sprites = loadAndCut("/chars.png",32,32);
	
	private Bitmap[][] loadAndCut(String name, int w, int h){
		
		BufferedImage img;
		try {
			img = ImageIO.read(Art.class.getResource(name));
		} catch (IOException e) {
			throw new RuntimeException("Failed to load "+name);
		}
		
		int xSlices = img.getWidth()/w;
		int ySlices = img.getHeight()/h;
		
		Bitmap[][] result = new Bitmap[xSlices][ySlices];
		
		for(int x=0;x<xSlices;x++){
			for(int y=0;y<ySlices;y++){
				result[x][y] = new Bitmap(w,h);
				img.getRGB(x*w, y*h, w, h, result[x][y].pixels, 0, w);
			}
		}
		
		return result;
	}
	
}
