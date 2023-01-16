package account;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Accounts_Client {

	public static void main(String[] args) {
		try {
			//1. 서버 쪽의 소켓을 만들기 위해 소켓을 생성한다. 9999 서버 소켓으로 접속을 시도한다.
			Socket socket = new Socket("127.0.0.1", 9999);
			System.out.println("Connection Success!");
			
			//2. 클라이언트가 메세지를 입력한다.
			Scanner scanner = new Scanner(System.in);
			String message = scanner.nextLine();
			
			//3. 소켓에 연결되어 있는 상대방에게 메세지를 보낸다.
			OutputStream out = socket.getOutputStream();
			//4. 위의 OutputStream은 바이트스트림이기 때문에 한글 꺠짐을 방지해야한다.
			DataOutputStream dos = new DataOutputStream(out);
			dos.writeUTF(message);
			
			//5. 소켓에 연결되어있는 상대방이 주는 메세지를 기다린다. (읽어드린다)
			InputStream in = socket.getInputStream();
			//6. 위의 InputStream은 바이트스트림이기 때문에 한글 꺠짐을 방지해야한다.
			DataInputStream dis = new DataInputStream(in);
			System.out.println("Receive : " + dis.readUTF());
			
			//7. 아웃풋스트림/인풋스트림/클라이언트와 연결된 소켓을 닫습니다.
			dis.close();
			dos.close();
			socket.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		

	}//main

}//class
