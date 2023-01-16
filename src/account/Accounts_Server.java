package account;
import java.net.*;
import java.io.*;


public class Accounts_Server {

	public static void main(String[] args) {
		//1. 서버소켓을 연다.
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(9999);
			System.out.println("server ready");
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		//2.서버는 계속 메세지를 받아 처리하기 때문에 while문을 쓴다.
		while(true) {
			try {
				//3. 클라이언트 응답 대기(blocking) & accept(새로운 소켓 만들어짐)
				Socket socket = serverSocket.accept();
				System.out.println("client connect success!");
				
				//4. 상대방과 connect된 소켓에서 메세지를 읽습니다.
				InputStream in = socket.getInputStream();
				
				//5. 위의 InputStream은 바이트스트림이기 때문에 한글 꺠짐을 방지해야한다.
				DataInputStream dis = new DataInputStream(in);
				String message = dis.readUTF();
				
				//6. 클라이언트에 보낼 스트림을 만든다.
				OutputStream out = socket.getOutputStream();
				
				//7. 위의 OutputStream은 바이트스트림이기 때문에 한글 꺠짐을 방지해야한다.
				DataOutputStream dos = new DataOutputStream(out);
				
				//8. 상대 클라이언트에게 write한다.
				dos.writeUTF("[ECHO]"+message+" (from server)");
				
				//9. 아웃풋스트림/인풋스트림/클라이언트와 연결된 소켓을 닫습니다.
				dos.close();
				dis.close();
				socket.close();
				System.out.println("client socket close...");
				
			}catch(IOException e) {
				e.printStackTrace();
			}
		}//while

	}//main

}//class
