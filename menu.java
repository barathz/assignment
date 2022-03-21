package com.ibm.bank;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import org.springframework.stereotype.Repository;

import com.ibm.bank.jdbc_pring.LearnerMapper;



@Repository("bank")
public class menu {

	JdbcTemplate jdbcTemplate;

	DataSource dataSource;
	static String qry;

	static Scanner scan;
	Connection dbCon;

	public static ResultSet theResultSet;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	void addNewCustomer(customer thecustomer) throws SQLException {                               // METHOD TO ADD NEW CUSTOMER

		jdbcTemplate.update("insert into bank values (?, ?, ?)", new Object[] {thecustomer.name(), thecustomer.id(), thecustomer.balance() });
		System.out.println("Customer Added");

	}

	float checkbalance(int id, String name) throws SQLException {               					// METHOD TO CHECK THE BALANCE OF AN CUSTOMER

		return jdbcTemplate.queryForObject("select balance from bank where id = ?", float.class, new Object[] { id });
	}

	void deposit(int id, float amount) throws SQLException {                                        // METHOD TO DEPOSIT  MONEY 

		float bal;
		bal = jdbcTemplate.queryForObject("select balance from bank where id = ?", float.class, new Object[] { id });
		bal = bal + amount;

		jdbcTemplate.update("update bank set balance =? where id =? ", new Object[] { bal, id });

		System.out.println("Money deposited successfully");
		String operation = "deposit";
		transaction(id, operation, amount);													//METHOD CALL FOR TRANSACTION
	}
  
	void withdraw(int id, float amount) throws SQLException {                                              // METHOD TO WITHDRAW MONEY

		float bal;
		bal = jdbcTemplate.queryForObject("select balance from bank where id = ?", float.class, new Object[] { id });
		if (bal - amount > 1000) {
			bal = bal - amount;

			jdbcTemplate.update("update bank set balance =? where id =? ", new Object[] { bal, id });

			System.out.println("Money withdrawn successfully");
			String operation = "withdraw";
			transaction(id, operation, amount);												//METHOD CALL FOR TRANSACTION

		} else {
			System.out.println("Due to minimum balance Restriction the Withdrawl is not possible ");
		}
	}

	void transfer(int sid, int rid, float amount) throws SQLException {                                               // METHOD FOR MONEY TRANSFER 

		float sbal;
		sbal = jdbcTemplate.queryForObject("select balance from bank where id = ?", float.class, new Object[] { sid });

		if (sbal - amount > 1000) {
			sbal = sbal - amount;
			jdbcTemplate.update("update bank set balance =? where id =? ", new Object[] { sbal, sid });
			String operation = "Transfer(sent)";
			transaction(sid, operation, amount);																//METHOD CALL FOR TRANSACTION				
			float rbal;
			rbal = jdbcTemplate.queryForObject("select balance from bank where id = ?", float.class,
					new Object[] { rid });
			rbal = rbal + amount;
			jdbcTemplate.update("update bank set balance =? where id =? ", new Object[] { sbal, rid });
			String operation2 = "Transfer(received)";
			transaction(rid, operation2, amount);																			//METHOD CALL FOR TRANSACTION
			System.out.println("Money transfered successfully");
		} else {
			System.out.println("Due to minimum balance Restriction the transfer is not possible ");
		}
	}

	void  transactionview(int id) throws SQLException {                                                                // TO GET THE DETAILS OF TRANSACTION ( WHICH IS A SEPARATE TABLE IN THE DTABASE)
		List<Learner> list = new ArrayList<>();
		list=jdbcTemplate.query("select * from transaction where T_id = ?", new LearnerMapper(),
				new Object[] { id });
		list.forEach(System.out::println);
		

	}

	void transaction(int t_id, String operation, float amount) throws SQLException {                                      // INSERTING TRANSACTION DETAILS INTO THE TRANSACTION TABLE

		jdbcTemplate.update("insert into transaction values (?, ?, ?)", new Object[] { t_id, operation, amount });

	}

	class LearnerMapper implements RowMapper<Learner> {                                                                  // ROWMAPPER CLASS

		@Override
		public Learner mapRow(ResultSet rs, int rowNum) throws SQLException {

			Learner theLearner = new Learner(rs.getInt("T_id"), rs.getString("opertaion"), rs.getFloat("amount"));

			return theLearner;
		}

	}
}
