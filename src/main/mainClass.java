package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import ServerNet.ServerThread;

public class mainClass {

	public static void main(String[] args) {
		
		ArrayList<Socket> list = new ArrayList<Socket>();
		// 클라이언트가 다른 클라이언트에게  패킷을 보내기 위해서 서버에 리스트로 소켓을 저장을 한다.
		ServerSocket serverSocket;
		// 서버 소켓, 문지기소켓 
		Socket clientSocket = null;
		// 클라이언트 소켓, 클라이언트 소켓의 정보를 저장.
		try {		
			serverSocket = new ServerSocket(9000);
			// 포트 번호 9000으로 설정. binding과 listen을 진행.
			while(true) {
				
				System.out.println("클라이언트 기다리는 중....");
				clientSocket = serverSocket.accept();
				// 클라이언트가 connect을 시도하면 accept로 받아들임. 
				System.out.println("접속한 클라이언트 IP:"+clientSocket.getInetAddress()
									+" Port:"+clientSocket.getPort());
				// 클라이언트의 정보 출력.
				list.add(clientSocket);
				// 접속한 클라이언트의 정보 저장.
				new ServerThread(clientSocket,list).start();
				// 스레드를 통해서 클라이언트가 소켓을 보내는지 대기하고 보내면 다른 클라이언트들에게 메세지를 보냄.
			}			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
