package msgServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Class to execute the login command.
 */ 
public class RegisterUser implements Command{
	private BufferedReader in;
	private BufferedWriter out;
	private MsgSvrConnection conn;
	private Connection jdbc;
	private String usr;
	private String pass;
	private String age;
	
	public RegisterUser(BufferedReader input, BufferedWriter output, MsgSvrConnection serverConn, Connection jdbc) throws IOException{
		this.in = input;
		this.out = output;
		this.conn = serverConn;
		this.jdbc=jdbc;
		}
	public void execute() throws IOException, SQLException{
		out.write("Enter user name: \r\n");
		out.flush();
		usr = in.readLine();
		if(usr!=null && !conn.getServer().isValidUser(usr)){
			out.write("Enter password: \r\n");
			out.flush();
			pass = in.readLine();
			if(pass!=null){
				out.write("Enter age (optional): \r\n ");
				out.flush();
				age = in.readLine();
				if(age==null)age="n/a";
				String[] info = new String[3];
				info[0]=usr;
				info[1]=pass;
				info[2]=age;
				conn.getServer().storeUserInfo(usr, pass);
				(new JDBC_InsertUserInfo(info,jdbc)).execute();
				out.write("User has been stored in the database and properties file would you like to see the database? (yes/no)\r\n");
				out.flush();
				if(in.readLine().equals("yes"))(new JDBC_QueryDB(out,jdbc)).execute();
			}else{
				out.write("Must be a valid password \r\n ");
			}
		}else{
			out.write("User name already taken\r\n ");
		}
		out.flush();
		}
	}
