import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

class FeedbackBar extends JPanel implements ItemListener{
	private JPanel Errors, PlayerInput;
	private JLabel errortitle, errortext;
	public JButton play;
	public JCheckBox firstbox;
	private boolean playerfirst = false;
	private Font font = new Font("Arial", Font.PLAIN, 20);
	private Color color = new Color(129, 232, 255);
	
	public FeedbackBar(){
		setPreferredSize(new Dimension(1440, 40));
		setLayout(new GridLayout(1, 2));
		
		Errors = new JPanel();
		PlayerInput = new JPanel();
		add(Errors);
		add(PlayerInput);
		Errors.setLayout(new FlowLayout(FlowLayout.LEADING));
		PlayerInput.setLayout(new FlowLayout(FlowLayout.RIGHT));
		Errors.setBackground(Color.blue);
		PlayerInput.setBackground(Color.blue);
		
		errortitle = new JLabel("Errors: ");
		Errors.add(errortitle);
		setFont(errortitle, color);
		
		errortext = new JLabel("");
		Errors.add(errortext);
		setFont(errortext, Color.red);
		
		
		play = new JButton("Play");
		PlayerInput.add(play);
		
		firstbox = new JCheckBox("Player First");
		firstbox.addItemListener(this);
		PlayerInput.add(firstbox);
	}
	
	private void setFont(JLabel j, Color c){
		j.setFont(font);
		j.setForeground(c);
	}
	
	public void setErrorText(String s){
		errortext.setText(s);
	}
	
	public boolean playerFirst(){
		return playerfirst;
	}
	
	public void itemStateChanged(ItemEvent e) {
        if(e.getSource() == firstbox){
            if(firstbox.isSelected()) 
				playerfirst = true;
            else
				playerfirst = false;
        }
    }
}