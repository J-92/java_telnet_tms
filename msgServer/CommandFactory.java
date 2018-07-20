package msgServer;
import java.io.BufferedReader; 
import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.Connection;
 
/**
 * Factory to read the command identifier and return a Command class
 * that can be used to process the rest of the command
 */
public class CommandFactory 
{
  /**
   * Read the command identifier and return a Command class
   * @return Command The class that will process this particular command
   */
  ///// 1.0.0.3) CommandFactory START /////
  public static msgServer.Command getCommand(BufferedReader in, 
					     BufferedWriter out, 
					     MsgSvrConnection serverConn, Connection jdbc) 
    throws IOException{
  ///// 1.0.0.3) CommandFactory START /////
    String cmdString = null;
    try{
    	///// 1.0.0.0) CommandFactory START /////
    	out.write("==================\r\n");
    	out.write("101 LOGIN:\r\n");
    	out.write("102 LOGOUT:\r\n");
    	out.write("103 SEND:\r\n");
    	out.write("104 MESSAGES_AVAILIABLE:\r\n");
    	out.write("105 GET_NEXT_MESSAGE:\r\n");
    	out.write("106 GET_ALL_MESSAGES:\r\n");
    	out.write("107 REGISTER_USER:\r\n");
    	out.write("108 REGISTER_USER_UPDATE:\r\n");
    	out.write("109 REMINDERS:\r\n");
    	out.write("110 REMINDERS_UPDATE:\r\n");
    	out.write("==================\r\n");
    	out.write("Enter a command: ");
    	out.flush();
    	///// 1.0.0.0) CommandFactory END /////
    	// Read the command identifier (101, 102, 103, 104, 105 or 106)
    	cmdString = in.readLine();
    	// convert the string to an integer
    	int command = Integer.parseInt(cmdString);
    	// print out some logging information
    	serverConn.userMsg("Read command " + command);
    	// Now decide which command identifier we have just read...
	switch (command) 
	  {
	  ///// 1.0.0.1 CommandFactory START /////
	  case MsgProtocol.LOGIN: // 101
	    return new LoginCommand(in, out, serverConn, jdbc);
	  ///// 1.0.0.1 CommandFactory END /////
	  case MsgProtocol.LOGOUT: // 102
	    return new LogoutCommand(in, out, serverConn); 
	  case MsgProtocol.SEND: // 103
	    return new SendCommand(in, out, serverConn);
	  case MsgProtocol.MESSAGES_AVAILABLE: // 104
	    return new GetNumMsgCommand(in, out, serverConn);
	  case MsgProtocol.GET_NEXT_MESSAGE: // 105
	    return new GetMsgCommand(in, out, serverConn);
	    /*
	     * Add more case statements below this comment to process 
	     * the other commands
	     */
	  ///// 1.0.0.2 CommandFactory START /////
	  case MsgProtocol.GET_ALL_MESSAGES: //106
		    return new GetAllMsgsCommand(in, out, serverConn);
	  case MsgProtocol.REGISTER_USER: //107
		    return new RegisterUser(in, out, serverConn, jdbc);
	  case MsgProtocol.REGISTER_USER_UPDATE: //108
		    return new RegisterUserUpdate(in, out, serverConn, jdbc);
	  case MsgProtocol.REMINDERS: //109
		    return new Reminders(in, out, serverConn);
	  case MsgProtocol.REMINDER_UPDATES: //110
		    return new RemindersUpdate(in, out, serverConn);
	  ///// 1.0.0.2 CommandFactory END /////
	    /*
	     * Don't add anything below this line
	     */
	  default: 
	    // If the command is not listed above, we don't have such a command
	    return new ErrorCommand(in, out, serverConn, 
				    "No such command: " + command); 
	  }
      } catch (NumberFormatException e) 
	{
	  // The string sent as command identifier wasn't an integer!
	  return new ErrorCommand(in, out, serverConn, 
				  "Incorrect Command Identifier: " + 
				  cmdString );
	}
  }
}
