package msgServer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC_InsertUserInfo{
	private Connection jdbc;
	private String[] info;
	public JDBC_InsertUserInfo(String[] info, Connection jdbc){
		this.info = info;
		this.jdbc = jdbc;
	}
	public void execute() throws SQLException, IOException {
		Statement statement = jdbc.createStatement();
		statement.execute("insert into userInfo values "+
				 "('"+info[0]+"', '"+info[1]+"', '"+info[2]+"')");
		if(statement != null) statement.close();
	}
}