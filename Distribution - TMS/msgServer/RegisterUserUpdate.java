package msgServer;
import java.io.BufferedReader;
import java.io.BufferedWriter; 
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class RegisterUserUpdate implements Command{
	private BufferedReader in;
	private BufferedWriter out;
	private MsgSvrConnection conn;
	private Connection jdbc;
 
	public RegisterUserUpdate(BufferedReader in, BufferedWriter out, MsgSvrConnection serverConn, Connection jdbc){
		this.in = in;
		this.out = out;
		this.conn = serverConn;
		this.jdbc=jdbc;
		}

	public void execute() throws IOException, SQLException{
		out.write("" + "Enter your old information::: " + "\r\n");
		out.write("" + "Enter old user name: " + "\r\n");
		out.flush();
		String old_username = in.readLine();
		out.write("" + "Enter old password: " + "\r\n");
		out.flush();
		String old_password = in.readLine();
		if(conn.getServer().getUserPassword(old_username).equals(old_password)){
			out.write("" + "Enter your new information::: " + "\r\n");
			out.write("" + "Enter new user name: " + "\r\n");
			out.flush();
			String new_username = in.readLine();
			out.write("" + "Enter new password: " + "\r\n");
			out.flush();
			String new_password = in.readLine();
			out.write("" + "Enter new age (optional): " + "\r\n");
			out.flush();
			String new_age = in.readLine();
			String[] info = new String[4];
			info[0]=new_username;
			info[1]=new_password;
			info[2]=new_age;
			info[3]=old_username;
			conn.getServer().removeUserInfo(old_username);
			conn.getServer().storeUserInfo(new_username, new_password);
			(new JDBC_UpdateUserInfo(info,jdbc)).execute();
			out.write("User has been updated in the database and properties file would you like to see the database? (yes/no)\r\n");
			out.flush();
			if(in.readLine().equals("yes"))(new JDBC_QueryDB(out,jdbc)).execute();
		}else{
			(new ErrorCommand(in, out,conn, "Not a current user and password.")).execute();
		}
	}
}