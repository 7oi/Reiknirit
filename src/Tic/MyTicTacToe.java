package Tic;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class MyTicTacToe implements ActionListener {
	/*Instance Variables*/
	private JFrame window = new JFrame("Tic-Tac-Toe");
	private JButton button1 = new JButton("x");
	private JButton button2 = new JButton("x");
	private JButton button3 = new JButton("x");
	private JButton button4 = new JButton("x");
	private JButton button5 = new JButton("x");
	private JButton button6 = new JButton("x");
	private JButton button7 = new JButton("x");
	private JButton button8 = new JButton("x");
	private JButton button9 = new JButton("x");
	
	
	
	public MyTicTacToe(){
		
		/*Create Window*/
		window.setSize(300,300);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLayout(new GridLayout(3,3));
		
		/*Add Buttons To The Window*/
		window.add(button1);
		window.add(button2);
		window.add(button3);
		window.add(button4);
		window.add(button5);
		window.add(button6);
		window.add(button7);
		window.add(button8);
		window.add(button9);
		
		/*Add The Action Listener To The Buttons*/
		button1.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.addActionListener(this);
		button5.addActionListener(this);
		button6.addActionListener(this);
		button7.addActionListener(this);
		button8.addActionListener(this);
		button9.addActionListener(this);
		
		/*Make The Window Visible*/
		window.setVisible(true);
	}
		
	public void actionPerformed(ActionEvent a) {

		/*Display X's or O's on the buttons*/
		if(a.getSource() == button1){
		button1.setText("X");
		} else if(a.getSource() == button2){
		button2.setText("X");
		} else if(a.getSource() == button3){
		button3.setText("X");
		} else if(a.getSource() == button4){
		button4.setText("X");
		} else if(a.getSource() == button5){
		button5.setText("X");
		} else if(a.getSource() == button6){
		button6.setText("X");
		} else if(a.getSource() == button7){
		button7.setText("X");
		} else if(a.getSource() == button8){
		button8.setText("X");
		} else if(a.getSource() == button9){
		button9.setText("X");
		}
		}
	
	public static void main(String[] args){
		new MyTicTacToe();
	}
}
