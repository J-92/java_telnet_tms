package msgServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
//A work in progress, connect via cmd telnet prompt instead.
public class EchoClient{ 
	private static Socket socket;
	private static BufferedReader SocketIn;
	private static PrintWriter SocketOut;
	private static BufferedReader UserIn;
	public static void main(String[] args) throws UnknownHostException, IOException {
		socket = new Socket("localhost",9801);
		SocketIn = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
		SocketOut = new PrintWriter(socket.getOutputStream());
		UserIn = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Connected to echo server");
		try{
			String response = SocketIn.readLine();//Server should've sent a response.
			System.out.println(response);
			String request = UserIn.readLine();
			while(request != null && !request.equals("quit")){
				SocketOut.println(request);
				SocketOut.flush();
				while(SocketIn.ready()){
					System.out.println(SocketIn.readLine());
				}
				request=UserIn.readLine();
			}
			}catch (UnknownHostException e) {
			 System.out.println(e);
			}catch (IOException e) {
			 System.out.println(e);
			}finally{
				try{
					if(socket != null){
						socket.close();
						}
				}catch (IOException e){}
			}
	}
	
}