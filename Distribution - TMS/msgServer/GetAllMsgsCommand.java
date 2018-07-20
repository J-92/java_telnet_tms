package msgServer;
import java.io.BufferedReader;
import java.io.BufferedWriter; 
import java.io.IOException;

public class GetAllMsgsCommand implements Command
{
  private BufferedReader in;
  private BufferedWriter out;
  private MsgSvrConnection conn;

  public GetAllMsgsCommand(BufferedReader in, BufferedWriter out, MsgSvrConnection serverConn)
  { 
    this.in = in;
    this.out = out;
    this.conn = serverConn;
  }

  public void execute() throws IOException
  {
	  out.write("Enter your user name: \r\n");
	  out.flush();
	  String username = in.readLine();
	  out.write("Entered: "+username+"\r\n");
	  out.flush();
	  out.write("Current user: "+conn.getCurrentUser()+"\r\n");
	  out.flush();
	  if(conn.getCurrentUser().equals(username)){
		  Message[] msgs = conn.getServer().getMessages().getAllMessages(username);
		  out.write("200\r\n");
		  if (msgs != null){
			  for(Message m:msgs){
				  out.write("|Sender: "+m.getSender()+"|Date: "+m.getDate()+"|Content: "+m.getContent()+"\r\n");
			  }
		  }else{
			  out.write("no new messages\r\n");  
		  }
		  out.flush();
	  }else{
		  out.write("Login to see your messages\r\n");
		  out.flush();
	  }
	
}

}
