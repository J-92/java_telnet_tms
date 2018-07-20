package msgServer;
import java.util.Hashtable;
import java.util.Properties;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Class to execute the login command.
 */ 
public class LoginCommand implements Command
{
  private BufferedWriter out;
  private BufferedReader in;
  private MsgSvrConnection conn;
  private Connection jdbc;
  /**
   * Construct a new LoginCommand object
   * @param BufferedReader in A reader to read the request from the client 
   * connection
   * @param BufferedWriter out A writer to write the response to the client
   * @param MsgServerConnection serverConn The class modelling the connection 
   * between this server and the client
   */
  public LoginCommand(BufferedReader in, BufferedWriter out, 
		      MsgSvrConnection serverConn, Connection jdbc)
  {
    this.out = out;
    this.in = in;
    this.conn = serverConn;
    this.jdbc = jdbc;
  }
  
  /**
   * Execute the command.
 * @throws SQLException 
   */
  public void execute() throws IOException, SQLException
  {
	  out.write("Login command reached.\r\n");
	  out.flush();
	  // if no-one is logged in for this connection at the moment....
	  if (conn.getCurrentUser() == null) {
	  (new JDBC_QueryDB(out,jdbc)).execute();
	  // We first have to read the rest of the request from the BufferedReader
	  // First read the user name
	  out.write("Enter a valid user name from the table.");
	  out.flush();
	  String username = in.readLine();
	  // Now read the password
	  out.write("Enter their password.");
	  out.flush();
	  String password = in.readLine();
      // to print out logging info, use the server connection's
      // userMsg method and ensure verbose is set to true in the 
      // MsgSvrConnection class
      conn.userMsg("LoginCommand: Got user: " + 
		   username + " ("+password+")");
      conn.userMsg("LoginCommand: Server thinks passwd is " +
		   conn.getServer().getUserPassword(username));
      // If the user name and password are not null...
      if (password != null && username != null &&
	  // and the password is correct...
    		  conn.getServer().getUserPassword(username).equals(password)) 
        {
	  // OK, we can log this user in
	  // Set the current user for this connection
          conn.setCurrentUser(username);
	  // Reply to the client to say the request was successful
          out.write("200\r\n"); 
          out.flush();        
        }else{
	    // Use the ErrorCommand class to report errors
	    // In this case, the password is incorrect
	    (new ErrorCommand(in, out,conn, 
			      "Incorrect Password")).execute();                  
	  }
    } else 
      {
	// Use the ErrorCommand class to report errors
	// In this case, someone has already been logged in on this connection
        (new ErrorCommand(in, out,conn, 
			  "Another user is currently logged in")).execute();        
      }

  }
}
