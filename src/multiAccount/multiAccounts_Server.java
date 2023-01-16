package multiAccount;
import java.io.*;
import java.net.*;
import java.util.*;

public class multiAccounts_Server{
	// 1. 서버는 클라이언트들의 정보를 저장하기 위해 해시맵을 가지고 있다.
	HashMap clients;
	multiAccounts_Server(){
		clients = new HashMap();
		//2. 어떤 스레드가 해시맵을 사용하고 있는 도중에는 다른 클래스는 사용 못하도록 합니다. (동기화)
		Collections.synchronizedMap(clients);
	}
	
	
	public void start() {
		ServerSocket serverSocket = null; // 서버소켓
		Socket socket = null; //통신소켓
		try {
			//3. 서버소켓을 연다. 클라이언트와 9999 통로를 통해 통신할 것이다.
			serverSocket = new ServerSocket(9999);
			System.out.println("start server...");
			
			//4. 클라이언트의 접속을 계속 기다린다. 접속 성공이 되면 클라이언트의 정보를 알고 있는 소켓이 만들어진다.(IP,PORT_NUM)
			while(true) {
				socket = serverSocket.accept();
				System.out.println(socket.getInetAddress()+" : "+socket.getPort()+" connect!");
				
				//5. 이름을 입력하고, 소켓을 만든다. (클라이언트 소켓 정보를 이용해 데이터가 오면 읽어들인다.) 그 후 브로드캐스팅을 한다.
				ServerReceiver thread = new ServerReceiver(socket);
				thread.start();
			}//while
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
		
	void sendToAll(String msg) {
		//6. 해시맵의 모든 키 값들을 가져와 반복문을 돌며 키에 outputstream을 모든 클라이언트에게 브로드캐스팅한다.
		Iterator iterator = clients.keySet().iterator();
		while(iterator.hasNext()) {
			try {
				DataOutputStream out = (DataOutputStream)clients.get(iterator.next());
				out.writeUTF(msg);
					
			}catch(IOException e) {
					e.printStackTrace();
			}
				
				
		}//while
			
	}

	public static void main(String[] args) {
		new multiAccounts_Server().start();
		

	}
	
	class ServerReceiver extends Thread{
		//1. 소켓 정보를 받아 출력 스트림을 만듭니다.
		Socket socket;
		DataInputStream in;
		DataOutputStream out;
		
		ServerReceiver(Socket socket){
			
			try {
				in = new DataInputStream(socket.getInputStream());
				out = new DataOutputStream(socket.getOutputStream());
				
			}catch(Exception e) {
				e.printStackTrace();
				
			}
		} //ServerReceiver(Socket socket)
		public void run() {
			
			
			//2. thread.start()가 실행되면 run 메소드가 실행된다.
			String name = "";
			try {
				//3. 사용자 이름을 받습니다.
				name = in.readUTF();
				
				//4. 같은 이름이 이미 있다면, in,out,socket을 close 합니다.
				if(clients.get(name) != null) {
					out.writeUTF("#Already exist name : "+name);
					out.writeUTF("#Please reconnect by other name");
					System.out.println(socket.getInetAddress()+" : "+socket.getPort()+" disconnect!");
					in.close();
					out.close();
					socket.close();
					socket = null;
					
				}
				//5. 같은 이름이 없다면, 함께 소통하는 클라이언트들에게 브로드캐스트를 한다.
				else {
					sendToAll("#"+name+" join!");
					//6. 해시맵에 새로운 클라이언트를 추가한다.
					clients.put(name, out);
					
					//7. 함께 채팅하는 클라이언트의 메세지를 받으면 읽고 브로드캐스팅한다.
					while(in != null) {
						sendToAll(in.readUTF());
					}//while
				}//else									
			
			}catch(IOException e) {
				e.printStackTrace();
			}finally {
				//8. 클라이언트가 빠져나간다면 해시맵에서 삭제해준다.
				if(socket != null) {
					sendToAll("#"+name+" exit!");
					clients.remove(name);
					System.out.println(socket.getInetAddress()+ " : "+ socket.getPort()+" disconnect!");
					
				}//if
				
			}//finally
	
		}//run
	}//inner class
}//class
