package funcs.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Operation {

	private static Connection con;
	public static Connection createConnection()
	{
		try{
			Class.forName("com.mysql.jdbc.Driver"); 
			
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/blockchain","root","root");
		}
		catch(SQLException | ClassNotFoundException cse)
		{
			System.out.println(cse);
		}
		return con;
	}
	public static void main(String[] args) {
		con=createConnection();
		System.out.println(con);
	}
}
