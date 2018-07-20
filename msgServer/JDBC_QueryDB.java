package msgServer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
public class JDBC_QueryDB{
	private Connection jdbc;
	private final String SQL_STATEMENT = "select * from userInfo";
	private BufferedWriter out;
	public JDBC_QueryDB(BufferedWriter out, Connection jdbc){
		this.jdbc = jdbc;
		this.out = out;
	}
	public void execute() throws SQLException, IOException {
		Statement statement = jdbc.createStatement();
		ResultSet resultSet = statement.executeQuery(SQL_STATEMENT);
		ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
		int columnCount = resultSetMetaData.getColumnCount();
		out.write("Listing all users on the database.\r\n");
		for(int x = 1; x<=columnCount; x++) out.write(resultSetMetaData.getColumnName(x)+" | ");
		while(resultSet.next()){
			out.write("\r\n");
			for(int x = 1; x<=columnCount; x++) out.write(resultSet.getString(x)+ " | ");
		}
		out.write("\r\n");
		out.flush();
		if(statement != null) statement.close();
		//if (jdbc != null) jdbc.close();
	}
}
