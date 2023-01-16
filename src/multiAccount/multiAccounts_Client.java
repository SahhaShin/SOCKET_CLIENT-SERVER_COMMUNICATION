package multiAccount;
import java.io.*;
import java.net.*;
import java.util.*;

public class multiAccounts_Client {

	public static void main(String[] args) {
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
		

	}//main
	
	static class ClientSender extends Thread{
		Socket socket;
		DataOutputStream out;
		String name;
		
		ClientSender(Socket socket, String name){
			this.socket=socket;
			this.name=name;
			
			try {
				//4. 메세지를 보내기 위해 아웃풋스트림을 만든다.
				out=new DataOutputStream(socket.getOutputStream());
			}catch(Exception e){
				e.printStackTrace();
			}
		}//ClientSender(Socket socket, String name)
		
		public void run() {
			//5. 메세지를 입력한다.
			Scanner scanner = new Scanner(System.in);
			try {
				//6. 입력된 문장을 서버로 보낸다.
				if(out != null) {
					out.writeUTF(name);
				}
				
				//7. 계속 문장을 입력받는다.
				while(out!=null) {
					String message = scanner.nextLine();
					
					//8. 입력된 문장 quit이면 종료한다.
					if(message.equals("quit")) {
						break;
					}
					out.writeUTF("["+name+"]"+message);
				}//while
				out.close();
				socket.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		
		
		
		}//run
	}//inner class
	
	static class ClientReceiver extends Thread{
		Socket socket;
		DataInputStream in;
		ClientReceiver(Socket socket){
			this.socket=socket;
			try {
				in=new DataInputStream(socket.getInputStream());
			}catch(Exception e){
				e.printStackTrace();
			}
		}//ClientReceiver(Socket socket)
		
		public void run() {
			while(in != null) {
				try {
					System.out.println(in.readUTF());
				}catch(Exception e){
					e.printStackTrace();
					break;
				}
			}//while
			try {
				in.close();
				socket.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}//run
	}//inner class
}//class
