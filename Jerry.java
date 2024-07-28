/*
This program selects moves randomly from an array filled with the numbers of all tacs that have not been marked
by either player. The array stores all unmarked tacs at the front and shifts them down whenever a tac becomes
marked. The random number generator gives an index from 0 up to the int variable counting how many tacs
are left unmarked for tac selection from the openmoves array.
*/

import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Jerry extends JFrame implements ActionListener{
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == move){
			// Return if game not started yet
			if(board.gameStarted() == false) return;
			
			// Game has ended if gamestate is not 0
			if(board.getGameState() != 0){
				repaint();
				return;
			}
			
			// Converting text field input to integer
			String s = tacnumber.getText();
			int tacNumber = Integer.parseInt(s);
			
			// Returns boolean true for legal move
			boolean legalmove = board.playerMove(tacNumber);
			if(!legalmove){
				feed.setErrorText("Illegal Move");
				return;
			}
			feed.setErrorText("");
			board.setGameState(true);
			
			// Game has ended if gamestate is not 0
			if(board.getGameState() != 0){
				repaint();
				return;
			}
			
			board.computerMove();
			board.setGameState(false);
		}else if(e.getSource() == reset){
			// Return if game not started yet
			if(board.gameStarted() == false) return;
			
			board.reset(feed.playerFirst());
		}else if(e.getSource() == feed.play){
			// Return if game is over
			if(board.getGameState() != 0) return;
			board.start(feed.playerFirst());
		}
		repaint();
	}
	
	class Closer extends WindowAdapter{
		public void windowClosing(WindowEvent e){
			System.out.println("Window closed");
			System.exit(0); // quits the program
		}
	}
	
	Board board;
	FeedbackBar feed;
	
	JButton move, reset;
	JTextField tacnumber;
	
	public Jerry(){
		setTitle("Jerry Tic-Tac-Toe");
		addWindowListener(new Closer());
		setSize(1440,810);
		
		Container glass = getContentPane();
		glass.setLayout(new BorderLayout());
		
		move = new JButton("Enter");
		move.addActionListener(this);
		reset = new JButton("Reset Game");
		reset.addActionListener(this);
		tacnumber = new JTextField("");
		tacnumber.setFont(new Font("Arial", Font.PLAIN, 24));
		
		// Create JPanels for all three sections
		board = new Board();
		feed = new FeedbackBar();
		feed.play.addActionListener(this);
		JPanel bottom = new JPanel();
		bottom.setLayout(new BorderLayout());
		bottom.setPreferredSize(new Dimension(1440, 40));
		
		bottom.add(move,"West");
		bottom.add(tacnumber,"Center");
		bottom.add(reset,"East");
		
		glass.add(feed, "North");
		glass.add(board, "Center");
		glass.add(bottom, "South");
		
		setVisible(true);
	}
	
	public static void main(String [] args){
		Jerry g = new Jerry();
	}
}