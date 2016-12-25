package pl.tomihome.ciscowebpowermeter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DbInserter {

	public static void insertIntoDb(String sshResult) throws ClassNotFoundException, SQLException {
		Connection c = null;
		Statement stmt = null;
		
		Class.forName("org.sqlite.JDBC");
		c = DriverManager.getConnection("jdbc:sqlite:" + DbConfig.pathToDbFile);

	      c.setAutoCommit(false);
	      System.out.println("Opened database successfully");

	      stmt = c.createStatement();
	      String sql = "INSERT INTO test (ID,NAME,AGE,ADDRESS,SALARY) " +
	                   "VALUES (1, "
	                   + sshResult.substring(sshResult.indexOf("Tx Bytes"))
	                   + ", 32, 'California', 20000.00 );"; 
	      stmt.executeUpdate(sql);

	      stmt.close();
	      c.commit();
	      c.close();
	}
}
