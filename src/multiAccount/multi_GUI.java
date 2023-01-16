package multiAccount;

import java.awt.*;
import java.awt.event.*;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.*;

import multiAccount.multiAccounts_Client.ClientReceiver;
import multiAccount.multiAccounts_Client.ClientSender;

public class multi_GUI extends JFrame implements ActionListener {
	
	protected JTextField nameField;
	protected JTextField textField;
	protected JTextArea textArea;
	
	public multi_GUI() {
		setTitle("Text Area Test");
		nameField = new JTextField(30);
		nameField.addActionListener(this);
		
		textField = new JTextField(30);
		textField.addActionListener(this);
		
		textArea = new JTextArea(10, 30);
		textArea.setEditable(false);
		
		add(nameField, BorderLayout.NORTH);
		add(textField, BorderLayout.SOUTH);
		add(textArea, BorderLayout.CENTER);
		
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		String name=nameField.getText();
		String text=textField.getText();
		textArea.append(name+"->"+text+"\n");
		textField.selectAll();
		textArea.setCaretPosition(textArea.getDocument().getLength());
		
	}

	public static void main(String[] args) {
		//new multi_GUI();
		
		try {
			//1. 소켓을 만듭니다.
			Socket socket = new Socket("127.0.0.1",9999);
			
			//2. 클라이언트의 이름을 입력 받습니다.
			Scanner scanner = new Scanner(System.in);
			System.out.println("name : ");
			String name = scanner.nextLine();
			
			//3. 메세지를 보내는 thread와 받는 thread를 만들어 시작한다.
			Thread sender = new Thread(new ClientSender(socket,name));
			Thread receiver = new Thread(new ClientReceiver(socket));
			sender.start();
			receiver.start();
		}catch(Exception e) {
			e.printStackTrace();
		}

	}

	

}
