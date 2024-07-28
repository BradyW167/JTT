import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

class tic{
	private tac start;
	private tac end;
	private int[] coords;
	
	public tic(tac start, tac end){
		this.start = start;
		this.end = end;
		this.coords = getLineCoords(start, end);
	}
	
	private int[] getLineCoords(tac s, tac e){
		int x1 = s.getSize() / 2 + s.getX();
		int y1 = s.getSize() / 2 + s.getY();
		int x2 = e.getSize() / 2 + e.getX();
		int y2 = e.getSize() / 2 + e.getY();
		int[] nums = {x1, y1, x2, y2};
		return nums;
	}
	
	public void draw(Graphics g){
		g.setColor(Color.black);
		g.drawLine(coords[0], coords[1], coords[2], coords[3]);
	}
}