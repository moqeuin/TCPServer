package ServerNet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ServerThread extends Thread {
	
	Socket clientSocket;
	ArrayList<Socket> list;
	
	public ServerThread(Socket clientSocket, ArrayList<Socket> list) {
		this.clientSocket = clientSocket;
		this.list = list;
		// 접속한 클라이언트와 나머지 클라이언트 정보 리스트를 불러들임.
	}
	@Override
	public void run() {
		try {
			
			while(true) {
				
				BufferedReader br = new BufferedReader
						   (new InputStreamReader(clientSocket.getInputStream()));
				String msg = br.readLine();
				// 소켓을 통해서 클라이언트가 메세지를 보내면 받아들임.
				System.out.println("클라이언트로부터 받은 패킷 : "+msg);
				Socket sendSocket = clientSocket;
				// 메세지를 보내는 클라이언트.
				for (int i = 0; i < list.size(); i++) {
					if(sendSocket!=list.get(i)) {
						PrintWriter pw = new PrintWriter(list.get(i).getOutputStream());
						pw.println(msg);
						//메세지를 보낸 클라이언트를 제외하고 모든 클라이언트들에게 메세지를 보냄.
						pw.flush();
					}
				}
				Thread.sleep(300);
			}
		} catch (Exception e) {
			
			// 접속이 끊킨 클라이언트의 소켓을 리스트에서 제거하고 소켓을 닫음.
		
			System.out.println("접속을 끊은 클라이언트 IP:" + clientSocket.getInetAddress()+
			                   "_Port number : " + clientSocket.getPort());
			list.remove(clientSocket);
			for (int i = 0; i < list.size(); i++) {
				System.out.println("접속중인 클라이언트 IP:" + list.get(i).getInetAddress()+
		                   "_Port number :" + list.get(i).getPort());			
			}		
			try {
				clientSocket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}			 
		}		
	}	
}