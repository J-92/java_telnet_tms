package msgServer;

import java.io.BufferedReader;
import java.io.BufferedWriter; 
import java.io.IOException;

public class RemindersUpdate implements Command{
	private BufferedReader in;
	private BufferedWriter out;
	private MsgSvrConnection conn;
 
	public RemindersUpdate(BufferedReader in, BufferedWriter out, MsgSvrConnection serverConn){
		this.in = in;
		this.out = out;
		this.conn = serverConn;
		}

	public void execute() throws IOException{
		

				out.write("" + MsgProtocol.REMINDER_UPDATES + "\r\n");
				out.write("" + 1 + "\r\n");
				out.write("" + "TESTING TESTING REMINDERS UPDATE" + "\r\n");
				out.flush();
	}
}