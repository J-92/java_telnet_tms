package msgServer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBC_UpdateUserInfo{
	private Connection jdbc;
	private String[] info;
	public JDBC_UpdateUserInfo(String[] info, Connection jdbc){
		this.info = info;
		this.jdbc = jdbc;
	}
	public void execute() throws SQLException, IOException {
		String sql = "UPDATE userInfo SET username=?, password=?, age=? WHERE username=?";
		PreparedStatement statement = jdbc.prepareStatement(sql);
		statement.setString(1, info[0]);
		statement.setString(2, info[1]);
		statement.setString(3, info[2]);
		statement.setString(4, info[3]);
		statement.executeUpdate();
		if(statement != null) statement.close();
	}
}
