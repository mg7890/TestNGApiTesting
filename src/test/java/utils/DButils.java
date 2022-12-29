package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DButils {
	
	
	private static String query = "Select id, name, email, phone, password from customers where name ='Kylian Mbappe'";//"SELECT id, name, email, phone FROM customers WHERE name = 'John Smith'";
	private static String insertQuery = "INSERT INTO customers VALUES('20','','Kylian Mbappe','10parisien@psg.com','225865841','theGOAt','','','','','PSG Front Desk','Qatar Sports Investments','','1','1','1','1','0000-00-00 00:00:00','0000-00-00 00:00:00')";
	private static String updateQuery = "Update customers Set phone = '7038527496' WHERE id='3'; ";
	private static String dbHostName =  TestDataReader.getProperty("dbhosturl");              //"jdbc:mysql://stack-overflow.cfse9bqqndon.us-east-1.rds.amazonaws.com:3306/crater";
	private static String username =TestDataReader.getProperty("dbusername");
	private static String password=TestDataReader.getProperty("dbpassword");

	private Connection connection;
	private Statement statement;
	private ResultSet resultset;
	private ResultSetMetaData rsmd;
	
	
	// This function accepts an sql select query and gets the record
	public List<String> selectArecord(String query) {
		List<String> list = new ArrayList<>();
		try {
			connection = DriverManager.getConnection(dbHostName, username, password);
			System.out.println("Connection is successful.");
			statement = connection.createStatement();
			resultset = statement.executeQuery(query);
			rsmd = resultset.getMetaData();
			resultset.next(); // moving from the fist column reserved for metadata

			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				list.add(resultset.getString(i));
			}
			for (String str : list) {
				System.out.println(str);
			}
			connection.close();
		} catch (SQLException e) {
			System.out.println("DB connection Not established.");
			e.printStackTrace();
		}
		return list;
	}
	
	
	// This function accepts an insert sql query and inserts a record.
	public void insertRecord(String insertQuery) {
		try {
			connection = DriverManager.getConnection(dbHostName, username, password);
			System.out.println("DB connection established.");
			statement = connection.createStatement();
			statement.executeUpdate(insertQuery);
			connection.close();
		} catch (SQLException e) {
			System.out.println("DB connection Not established.");
			e.printStackTrace();
		}
	}
	
	// This function accepts and update sql query and updates it.
	public void updateRecord(String updateQuery) {
		try {
			connection = DriverManager.getConnection(dbHostName, username, password);
			System.out.println("DB connection established.");
			statement = connection.createStatement();
			statement.executeUpdate(updateQuery);
			connection.close();
		} catch (SQLException e) {
			System.out.println("DB connection Not established.");
			e.printStackTrace();
		}
	}
	
	
	//This function accepts and update sql delete query and deletes it.
	public void deleteRecord(String deleteQuery) {
		try {
			connection = DriverManager.getConnection(dbHostName, username, password);
			System.out.println("DB connection established.");
			statement = connection.createStatement();
			statement.executeUpdate(deleteQuery);
			connection.close();
		} catch (SQLException e) {
			System.out.println("DB connection Not established.");
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	

	
	public static void main(String[] args)  {
		
    DButils utils = new DButils();
    
    utils.selectArecord(query);
    utils.insertRecord(insertQuery);
    utils.selectArecord(query);
    utils.updateRecord(updateQuery);
		
		
		
		
//		String query = "SELECT id, name, email, phone FROM customers WHERE name = 'John Smith'";
//		try {
//			Connection connect = DriverManager.getConnection(dbHostName, username, password);
//			System.out.println("Connection is successful.");
//			Statement statement = connect.createStatement();
//			ResultSet resultset = statement.executeQuery(query);
//			ResultSetMetaData rsmd = resultset.getMetaData();
//			
//			resultset.next(); //moving from the fist column reserved for metadata
//			System.out.println("First index is id: " + resultset.getString(1));
//			System.out.println("Column name for third column is: " + rsmd.getColumnName(3));
//			List <String> johnsmith = new ArrayList<>();
//			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
//				johnsmith.add(resultset.getString(i));	
//			}
//			
//			for(String str : johnsmith) {
//				System.out.println(str);
//			}
//			
//			connect.close();
//		} catch (SQLException e) {
//			System.out.println("DB connection failed to establish.");
//			e.printStackTrace();
//		}
    
	}

}
