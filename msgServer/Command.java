package msgServer; 
import java.io.IOException;
import java.sql.SQLException;

public interface Command 
{
  public void execute() throws IOException, SQLException;
}
