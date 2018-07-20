package msgServer;
//This is the file I used to structure my embedded Apache derby database when i first made it.
//Columns in table are: username password age
// More details could be added as columns
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class JDBC_CreateDB {
	public static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	public static final String JDBC_URL = "jdbc:derby:s14139866db;create=true";
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Class.forName(DRIVER);
		Connection connection = DriverManager.getConnection(JDBC_URL);
		connection.createStatement().execute("create table userInfo(username varchar(20), password varchar(20), age varchar(20))");
		connection.createStatement().execute("insert into userInfo values "+
											 "('j0', '0', '20'), "+
											 "('j1', '1', '21'), "+
											 "('j2', '2', '22')");
		System.out.println("userInfo table created and records successfully inserted...");
	}
}
