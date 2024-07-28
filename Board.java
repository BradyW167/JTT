import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

class Board extends JPanel{
	private tac[] tacs = new tac[9];
	private tic[] tics = new tic[9];
	private int[] openmoves;
	private int opencount;
	private int lastmove;
	private int gamestate;
	private boolean drawgame = false;
	private boolean playerfirst;
	private String[] gamestatestrings;
	
	private static Font font = new Font("Arial", Font.BOLD, 60);
	
	public Board(){
		setSize(1440,690);
		openmoves = new int[]{1,2,3,4,5,6,7,8,9};
		opencount = 9;
		gamestate = 0;
		gamestatestrings = new String[]{"YOU TIED!", "YOU LOST!", "YOU WON!"};
		// Set tac circle coordinates
		for(int i = 0; i < 9; i++){
			if(i < 3)
				tacs[i] = new tac(1440 * (i+1) / 4, 120, i+1); //178
			else if(i < 6)
				tacs[i] = new tac(1440 * (i-2) / 8 + 360, 345, i+1);
			else
				tacs[i] = new tac(1440 * (i-5) / 4, 570, i+1); //532
		}
		// Set adjacent tac values
		tacs[0].setAdj(new int[][]{{1,2}, {3,7}, {4,8}});
		tacs[1].setAdj(new int[][]{{0,2}, {3,6}, {5,8}});
		tacs[2].setAdj(new int[][]{{0,1}, {4,6}, {5,7}});
		tacs[3].setAdj(new int[][]{{4,5}, {0,7}, {1,6}});
		tacs[4].setAdj(new int[][]{{3,5}, {0,8}, {2,6}});
		tacs[5].setAdj(new int[][]{{3,4}, {1,8}, {2,7}});
		tacs[6].setAdj(new int[][]{{7,8}, {1,3}, {2,4}});
		tacs[7].setAdj(new int[][]{{6,8}, {0,3}, {2,5}});
		tacs[8].setAdj(new int[][]{{6,7}, {0,4}, {1,5}});
		
		// Set tic line coordinates
		tics[0] = new tic(tacs[0], tacs[2]);
		tics[1] = new tic(tacs[3], tacs[5]);
		tics[2] = new tic(tacs[6], tacs[8]);
		tics[3] = new tic(tacs[0], tacs[7]);
		tics[4] = new tic(tacs[0], tacs[8]);
		tics[5] = new tic(tacs[1], tacs[6]);
		tics[6] = new tic(tacs[1], tacs[8]);
		tics[7] = new tic(tacs[2], tacs[6]);
		tics[8] = new tic(tacs[2], tacs[7]);
	}
	
	public void start(boolean playerfirst){
		playerfirst = playerfirst;
		drawgame = true;
		// If firstbox is not checked computer goes first
		if(!playerfirst) computerMove();
	}
	
	public boolean gameStarted(){
		return drawgame;
	}
	
	public void reset(boolean playerfirst){
		openmoves = new int[]{1,2,3,4,5,6,7,8,9};
		opencount = 9;
		gamestate = 0;
		for(int i = 0; i < 9; i++){
			tacs[i].resetOwner();
		}
		// If firstbox is not checked computer goes first
		if(!playerfirst) computerMove();
		System.out.println("Board reset");
	}
	
	public boolean playerMove(int playermove){
		boolean moveaccepted = false;
		int moveindex = 0;
		
		// Check if playermove is in openmove array
		for(int i = 0; i < opencount; i++){
			if(playermove == openmoves[i]){
				moveindex = i; // Save index of the chosen tac for removal
				moveaccepted = true;
				break;
			}
		}
		
		if(moveaccepted){
			lastmove = playermove;
			// Sets the input tac to be player-owned
			tacs[playermove - 1].select(true);
			// Removes move from the openmoves array
			removeTac(moveindex);
			return true;
		}else
			return false;
	}
	
	public void computerMove(){
		// Get random number from 0 up to number of unmarked tacs
		int randindex = (int)((opencount - 1) * Math.random());
		
		// Get tac number at random index from the unmarked tacs
		int computermove = openmoves[randindex];
		removeTac(randindex);
		
		lastmove = computermove;
		// Set the tac to be computer-owned
		tacs[computermove - 1].select(false);
	}
	
	private void removeTac(int toremove){
		// Shift open moves to front of array
		shiftArray(openmoves, toremove);
		opencount--;
	}
	
	private static void shiftArray(int[] n, int m){
		// Base case - last element
		if(m == n.length - 1){
			n[m] = 0;
			return;
		}
		// Recursive case - not last element
		n[m] = n[m+1];
		shiftArray(n, m + 1);
	}
	
	public void setGameState(boolean isplayer){
		int[][] lastadjacent = tacs[lastmove - 1].getAdj();
		int adj1, adj2;
		//System.out.println("Player: " + isplayer + " Last Move: " + lastmove);
		// Checking for three in a row
		for(int i = 0; i < 3; i++){
			adj1 = lastadjacent[i][0];
			adj2 = lastadjacent[i][1];
			if(checkAdjacentOwnership(adj1, adj2, isplayer)){
				if(isplayer){
					System.out.println("You won Jerry-tac-toe!");
					gamestate = 3; // Player wins
					return;
				}else{
					System.out.println("You lost Jerry-tac-toe!");
					gamestate = 2; // Computer wins
					return;
				}
			}
		}
		
		//Checking for tie
		if(openmoves[0] == 0){
			System.out.println("Game tie");
			gamestate = 1;
			return;
		}
		return; // No win or tie
	}
	
	public int getGameState(){
		return gamestate;
	}
	
	private boolean checkAdjacentOwnership(int pos1, int pos2, boolean isplayer){
		int pos1owner = tacs[pos1].getOwner();
		int pos2owner = tacs[pos2].getOwner();
		
		if(isplayer && pos1owner == 1 && pos2owner == 1) return true; // Adjacent are both owned by player, who moved last
		
		if(!isplayer && pos1owner == -1 && pos2owner == -1) return true; // Adjacent are both owned by computer, who moved last
		
		return false; // Adjacent is not owned by the last player that moved
	}
	
	private void drawMarks(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(5)); // Set thickness of the lines
		
		int tx, ty, owner, size;
		
		for(int i = 0; i < 9; i++){
			owner = tacs[i].getOwner();
			if(owner == 1){
				tx = tacs[i].getX();
				ty = tacs[i].getY();
				size = tac.getSize();
				g2d.setColor(Color.red);
				// Draw one line from top-left to bottom-right
				g2d.drawLine(tx, ty, tx + size, ty + size);
				// Draw another line from bottom-left to top-right
				g2d.drawLine(tx, ty + size, tx + size, ty);
			}else if(owner == -1){
				tx = tacs[i].getX();
				ty = tacs[i].getY();
				size = tac.getSize();
				g.setColor(Color.magenta);
				g.drawOval(tx, ty, size, size);
			}
		}
	}
	
	private void drawStartText(Graphics g){
		String s = "Click Play to Start";
		g.setFont(font);
		g.setColor(Color.black);
		FontMetrics fm = g.getFontMetrics();
		int textWidth = fm.stringWidth(s);
		int textHeight = fm.getHeight();
		g.drawString(s, (1440 - textWidth) / 2, 690 / 2);
	}
	
	private void drawEndText(Graphics g){
		// Return if no win or tie
		if(gamestate == 0) return;
		
		// Gets text for game state
		String s = gamestatestrings[gamestate - 1];
		
		g.setFont(font);
		g.setColor(Color.black);
		// Get font metrics to calculate text position
		FontMetrics fm = g.getFontMetrics();
		int textWidth = fm.stringWidth(s);
		int textHeight = fm.getHeight();
		g.drawString(s, (1440 - textWidth) / 2, 360);
	}
	
	protected void paintComponent(Graphics g){
		g.setColor(Color.white);
		g.fillRect(0,0,1440,690);
		
		// Draw only intro text if start button not pressed
		if(drawgame == false){
			drawStartText(g);
			return;
		}
		for(int i = 0; i < 9; i++)
			tics[i].draw(g);
		for(int i = 0; i < 9; i++)
			tacs[i].draw(g);
		// Draws all symbols for marked tacs
		drawMarks(g);
		// Draws text in center if game is won or tied
		drawEndText(g);
	}
}