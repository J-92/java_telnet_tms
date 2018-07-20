package msgServer;
import java.util.Properties;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.net.ServerSocket;
/**
 * A class to model the message server itself  
 */
public class MessageServer{
	public static final int DEFAULT_PORT = 9801;
	private int port;
	private Properties userInfo;
	private MessageCollection messages;
	private boolean verbose;
	///// 1.0.0.0) MessageServer START /////
	private final String JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	private final String JDBC_URL = "jdbc:derby:s14139866db;create=true";
	///// 1.0.0.0) MessageServer END /////
	 /**
	   * Construct a new MessageServer
	   * @param int portNumber The port number on which the server will listen.
	   * The default port number is 9801
	   */
	public MessageServer(int portNumber) throws IOException{
		port = portNumber;
		userInfo = new Properties();
		FileInputStream fin = null;
		messages = new MessageCollection();
		try{
			fin = new FileInputStream(MsgProtocol.PASSWORD_FILE);
			userInfo.load(fin);
		}catch(IOException e){
			throw new IOException("Can't open the password file: " +
			      MsgProtocol.PASSWORD_FILE);
		}finally{
			if (fin != null){
				try{
					fin.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
	}
	  /**
	   * Construct a new MessageServer using the default port of 9801
	   */	
	public MessageServer() throws IOException{
		this(DEFAULT_PORT);
		}

	  /**
	   * Start the server
	   */
	public void startService() throws 
		InstantiationException, 
		IllegalAccessException, 
		ClassNotFoundException, 
		SQLException{
		ServerSocket serverSocket = null;
		Socket clientConnection = null;
		///// 1.0.0.1) MessageServer START /////
		Class.forName(JDBC_DRIVER).newInstance();
		Connection jdbc = DriverManager.getConnection(JDBC_URL);
		///// 1.0.0.1) MessageServer END /////
		try {
			verbose=true;
			userMsg("MessageServer: Starting message service on port " + port);
			serverSocket = new ServerSocket(port);
			while(true){
				clientConnection = serverSocket.accept();
				userMsg("MessageServer: Accepted from " +
				clientConnection.getInetAddress());
				MsgSvrConnection conn = new MsgSvrConnection(clientConnection, this, jdbc);
                conn.setVerbose(true);
                conn.start();
                }
			}catch(IOException e){
				System.out.println(e);
				}finally{
					userMsg("Message Server closing down");
					try{
						if(clientConnection != null){
							clientConnection.close();
							}
					}catch(IOException e){e.printStackTrace();}
					try{
						if (serverSocket != null) {
							serverSocket.close();
							}
					} catch (IOException e) {e.printStackTrace();}
				}
		}
	  /**
	   * Start the program.<br>
	   * Usage: java MessageServer [portNumber].
	   * @params String[] args The only argument is an optional port number
	   */
	public static void main(String[] args) throws 
		IOException, 
		ClassNotFoundException, 
		InstantiationException, 
		IllegalAccessException, 
		SQLException{
		int portNumber;
		if (args.length == 1){
			portNumber = Integer.parseInt(args[0]);
		}else{
			portNumber = DEFAULT_PORT;
		}
		MessageServer server = new MessageServer(portNumber);
		server.setVerbose(false);
        server.startService();
    }
	/**
	   * Query to obtain the message collection from the server.
	   * @return MessageCollection The collection of messages that are
	   * waiting to be delivered to the users.
	   */
	public MessageCollection getMessages(){
		return messages;
    }
	  /**
	   * Query to get the password for a specific user.
	   * @param String user The username of the user whose password we want to know
	   * @return String the password of this user
	   */
	public String getUserPassword(String user){
		return userInfo.getProperty(user);
	}
	  /**
	   * Query to find out if a username represents a valid user.  If the username
	   * is in the password file, return true, else return false
	   * @return boolean True if the user is in the password file, false otherwise 
	   */
	public boolean isValidUser(String username){
		return (userInfo.getProperty(username) != null);
	}
	  /**
	   * Get the value of the verbose flag which determines whether or not
	   * logging is turned on.
	   * @return boolean The current value of the verbose flag
	   */
	public boolean getVerbose(){
		return verbose;
	}
	  /**
	   * Set the verbose flag
	   * @param boolean verbose The new value for the verbose flag
	   */
	public void setVerbose(boolean verbose){
		this.verbose = verbose;
	}

	  /**
	   * Print out a message to the user dependent on the value of the verbose flag
	   */
	private void userMsg(String msg) {
		if (verbose){
			System.out.println("MessageServer: " + msg);
		}
	}
	
	///// 1.0.0.2) MessageServer START /////
	/**
	 * Store the user and password in the properties object and file
	 */
	public void storeUserInfo(String usr, String pass) throws IOException{
	  userInfo.setProperty(usr,pass);
	  FileOutputStream fout = new FileOutputStream(MsgProtocol.PASSWORD_FILE);
	  userInfo.store(fout,usr+pass);
	  fout.close();
	}
	/**
	 * Remove the user and password in the properties object and file
	 */
	public void removeUserInfo(String usr) throws IOException{
		userInfo.remove(usr);
		FileOutputStream fout = new FileOutputStream(MsgProtocol.PASSWORD_FILE);
		userInfo.store(fout,null);
		fout.close();
	}
	///// 1.0.0.2) MessageServer END /////
}  
