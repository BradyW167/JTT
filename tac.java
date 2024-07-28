import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

class tac{
	private int x, y, tx, ty;
	private static int size = 100;
	private String num;
	private int owner = 0;
	
	private static Font font = new Font("Arial", Font.BOLD, 28);
	private int textWidth;
	private int textHeight;
	private int[][] adj;
	
	public tac(int x, int y, int num){
		// Subtract half of size to account for upper left placement
		this.x = x - size / 2;
		this.y = y - size / 2;
		this.num = String.valueOf(num);
		adj = new int[3][2];
	}
	
	public void select(boolean player){
		owner = player ? 1 : -1;
	}
	
	public void setAdj(int[][] adjacency){
		adj = new int[3][2];
		for (int i = 0; i < 3; i++) {
			adj[i][0] = adjacency[i][0];
			adj[i][1] = adjacency[i][1];
		}
	}
	
	public int[][] getAdj(){
		return adj;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public void resetOwner(){
		owner = 0;
	}
	
	public int getOwner(){
		return owner;
	}
	
	public static int getSize(){
		return size;
	}
	
	public void printCoords(){
		System.out.println("X:" + this.x + " Y: " + this.y);
	}
	
	public void draw(Graphics g){
		g.setFont(font);
		// Get font metrics to calculate text width
		FontMetrics fm = g.getFontMetrics();
		textWidth = fm.stringWidth(num);
		textHeight = fm.getHeight();
		// Center font coordinates on tacs
		tx = (x + size / 2) - (textWidth / 2);
		ty = (y + size / 2) + (textHeight / 4);
		// Draw tacs
		g.setColor(Color.blue);
		g.fillOval(x, y, size, size);
		// Draw number labels
		g.setColor(Color.black);
		g.drawString(num, tx, ty);
	}
}