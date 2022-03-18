package com.ibm.bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.springframework.stereotype.Repository;

@Repository("bank")
public class menu {

	public static final String URLTOCONNECT = "jdbc:mysql://localhost:3306/ibm_training";

	public static final String USERNAME = "root";

	public static final String PASSWORD = "";

	public static final String DRIVERCLASSNAME = "com.mysql.cj.jdbc.Driver";

	static PreparedStatement preparedStatement;

	static String qry;

	static Scanner scan;
	Connection dbCon;

	public static ResultSet theResultSet;

	void connectdb() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub

		Class.forName(DRIVERCLASSNAME);

		dbCon = DriverManager.getConnection(URLTOCONNECT, USERNAME, PASSWORD);

	}

	void addNewCustomer(int id, String name, float balance) throws SQLException {

		System.out.println("Enter the account id,name and aamount  for the new customer to be registered");

		qry = "insert into bank values(?, ?, ?)";

		preparedStatement = dbCon.prepareStatement(qry);

		preparedStatement.setString(1, name);

		preparedStatement.setInt(2, id);

		preparedStatement.setFloat(3, balance);

		if (preparedStatement.executeUpdate() > 0) {
			System.out.println("New Customer added...");
		}

	}

	void checkbalance(int id, String name) throws SQLException {

		qry = "select balance from bank where id = ? AND name =?";
		preparedStatement = dbCon.prepareStatement(qry);

		preparedStatement.setInt(1, id);

		preparedStatement.setString(2, name);

		theResultSet = preparedStatement.executeQuery();

		while (theResultSet.next()) {
			System.out.println(theResultSet.getFloat("balance"));

		}
	}

	void deposit(int id, float amount) throws SQLException {

		qry = "select balance from bank where id =?";
		preparedStatement = dbCon.prepareStatement(qry);
		preparedStatement.setInt(1, id);
		theResultSet = preparedStatement.executeQuery();
		float bal = 0;
		while (theResultSet.next()) {

			bal = theResultSet.getFloat("balance");
		}
		bal = bal + amount;
		String qry2 = "update bank set balance=? where id = ?";
		PreparedStatement preparedStatement2 = dbCon.prepareStatement(qry2);
		preparedStatement2.setFloat(1, bal);
		preparedStatement2.setInt(2, id);
		String op = "deposit";
		transaction(id, op, amount);

		if (preparedStatement2.executeUpdate() > 0) {
			System.out.println("Amount  Deposited Successfully");
		}
	}

	void withdraw(int id, float amount) throws SQLException {

		qry = "select balance from bank where id =?";
		preparedStatement = dbCon.prepareStatement(qry);
		preparedStatement.setInt(1, id);
		theResultSet = preparedStatement.executeQuery();
		float bal = 0;
		while (theResultSet.next()) {
			bal = theResultSet.getFloat("balance");
		}
		if (bal - amount > 1000) {
			bal = bal - amount;
			String qry2 = "update bank set balance= ? where id = ?";
			PreparedStatement preparedStatement2 = dbCon.prepareStatement(qry2);
			preparedStatement2.setFloat(1, bal);
			preparedStatement2.setInt(2, id);

			if (preparedStatement2.executeUpdate() > 0) {
				System.out.println("Money Withdrawn Successfully");
				String op = "withdraw";
				transaction(id, op, amount);
			}
		} else {
			System.out.println("Due to minimum balance Restriction the Withdrawl is not possible ");
		}
	}

	void transfer(int sid, int rid, float amount) throws SQLException {

		qry = "select balance from bank where id =?";
		preparedStatement = dbCon.prepareStatement(qry);
		preparedStatement.setInt(1, sid);
		theResultSet = preparedStatement.executeQuery();
		float sbal = 0;
		while (theResultSet.next()) {
			sbal = theResultSet.getFloat("balance");
		}

		if (sbal - amount > 1000) {
			sbal = sbal - amount;
			String qry2 = "update bank set balance=? where id = ?";
			PreparedStatement preparedStatement2 = dbCon.prepareStatement(qry2);
			preparedStatement2.setFloat(1, sbal);
			preparedStatement2.setInt(2, sid);

			String qry4 = "select balance from bank where id =?";
			PreparedStatement preparedStatement4 = dbCon.prepareStatement(qry4);
			preparedStatement4.setInt(1, rid);
			ResultSet theResultSet2 = preparedStatement4.executeQuery();
			float rbal = 0;
			while (theResultSet2.next()) {
				rbal = theResultSet2.getFloat("balance");
			}

			rbal = rbal + amount;
			// System.out.println(rbal);
			String qry3 = "update bank set balance=? where id = ?";
			PreparedStatement preparedStatement3 = dbCon.prepareStatement(qry3);
			preparedStatement3.setFloat(1, rbal);
			preparedStatement3.setInt(2, rid);
			int executesend = preparedStatement2.executeUpdate();
			String op = "transfer(sending)";
			transaction(sid, op, amount);
			int executereceive = preparedStatement3.executeUpdate();
			String op2 = "transfer(receving)";
			transaction(rid, op2, amount);

			if (executesend > 0 || executereceive > 0) {
				System.out.println("Money transfered Successfully");
				
			}
			else {
				System.out.println("ID not available");
			}
		} else {
			System.out.println("Due to minimum balance Restriction the transfer is not possible ");
		}
	}

	void transactionview(int id) throws SQLException {

		qry = "select * from transaction where T_id = ?";
		preparedStatement = dbCon.prepareStatement(qry);

		preparedStatement.setInt(1, id);

		theResultSet = preparedStatement.executeQuery();

		while (theResultSet.next()) {
			System.out.print("id " + theResultSet.getInt("T_id"));
			System.out.print(", Operation: " + theResultSet.getString("opertaion"));
			System.out.println(" amount: " + theResultSet.getFloat("amount"));

		}
	}

	void transaction(int t_id, String operation, float amount) throws SQLException {

		qry = "insert into transaction values(?, ?, ?)";

		preparedStatement = dbCon.prepareStatement(qry);

		preparedStatement.setInt(1, t_id);

		preparedStatement.setString(2, operation);

		preparedStatement.setFloat(3, amount);

		if (preparedStatement.executeUpdate() > 0) {
			System.out.println("transaction list  added...");
		}
		
	}
}
