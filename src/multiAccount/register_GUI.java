package multiAccount;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class register_GUI extends JFrame implements ActionListener {
	
	protected JTextField nameField;
	protected JLabel infoField;
	
	public register_GUI(){
		setTitle("Register");
		setSize(500,500);
		JPanel panel = new JPanel();
		
		JLabel label1 = new JLabel("your name");
		
		nameField = new JTextField(30);
		nameField.addActionListener(this);
		
		JButton button = new JButton("완료");
		button.addActionListener(this);
		
		infoField = new JLabel("welcome!");
		
		panel.add(label1);
		panel.add(nameField);
		panel.add(button);
		panel.add(infoField, BorderLayout.SOUTH);
		add(panel);
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String name=nameField.getText();
		JButton b = (JButton) e.getSource();
		String complete = b.getText();
		if(name==null && complete=="완료") {
			infoField.setText("enter your name!!!");
		}
		
		else {
			infoField.setText(name+" thank you~!");
			new lobby_GUI();
			setVisible(false);
		}
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		register_GUI ab = new register_GUI();
	}

}
