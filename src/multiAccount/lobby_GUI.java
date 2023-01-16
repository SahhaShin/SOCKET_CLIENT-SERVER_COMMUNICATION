package multiAccount;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class lobby_GUI extends JFrame implements ActionListener {
	
	int img_x=150, img_y=150;
	protected JTextField textField;
	protected JTextArea textArea;
	
	public lobby_GUI() {
		Frame f = new Frame();
		f.setSize(1000,1000);//크기설정
		f.setLocation(0,0);//위치 설정 
		f.setTitle("My Frame");
		
		f.setLayout(new FlowLayout()); //배치 관리자를 FlowLayout으로 변경한다. 이를 추가하지 않으면 버튼이 화면 전체를 차지.
		//f.setLayout(new BorderLayout());//배치 관리자를 BorderLayout으로 변경한다.
		//getContentPane().setBackground(Color.yellow); //콘텐츠의 상위 판을 칠한다.
		
		JButton button1 = new JButton("1");
		button1.addActionListener(this);
		button1.setLocation(img_x,img_y);
		
		JButton start = new JButton("game start!");
		start.setPreferredSize(new Dimension(1000,50));
	
		//start.addActionListener(this);
		
		//채팅
		
		textField = new JTextField("enter your message!");
		textField.setPreferredSize(new Dimension(1000,50));
		
		textArea = new JTextArea();
		textArea.setPreferredSize(new Dimension(1000,50));
		textArea.setEditable(false);
		
		
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel(); // 1000 300
		JPanel panel3 = new JPanel(); // 1000 250 ->area 1000 50
		JPanel panel4 = new JPanel(); // 1000 250 ->area 1000 200
		JPanel panel5 = new JPanel(); // 1000 50
		
		panel2.setLayout(new FlowLayout());
		panel3.setLayout(new FlowLayout());
		panel4.setLayout(new FlowLayout());
		panel5.setLayout(new FlowLayout());
		
		panel1.setPreferredSize(new Dimension(1000,500));
		panel2.setPreferredSize(new Dimension(1000,500));
		panel3.setPreferredSize(new Dimension(1000,50));
		panel4.setPreferredSize(new Dimension(1000,50));
		panel5.setPreferredSize(new Dimension(1000,50));
	
		panel1.add(button1);
		panel1.requestFocus();
		panel1.setFocusable(true);
		
		panel3.add(textArea);
		panel2.add(panel3);
		
		panel4.add(textField);
		panel2.add(panel4);
		
		panel5.add(start);
		panel2.add(panel5);
		
		f.add(panel1, BorderLayout.NORTH);
		f.add(panel2, BorderLayout.SOUTH);
		
		
		panel1.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				int keycode = e.getKeyCode();
				switch(keycode) {
				case KeyEvent.VK_UP: img_y-=10; break;
				case KeyEvent.VK_DOWN: img_y+=10; break;
				case KeyEvent.VK_LEFT: img_x-=10; break;
				case KeyEvent.VK_RIGHT: img_x+=10; break;
				
				}
				button1.setLocation(img_x,img_y);
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			
		});
		textField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String text=textField.getText();
				textArea.append(text+"\n");
				textField.selectAll();
				textArea.setCaretPosition(textArea.getDocument().getLength());
			}
			
		});

		

		f.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
		
		
		

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
