package com.ibm.bank;

import java.sql.SQLException;
import java.util.Scanner;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		menu bank = context.getBean("bank", menu.class);

		int choice;

		do {

			System.out.println("MENU ");
			System.out.println(" 1.CREATE AN ACCOUNT");
			System.out.println(" 2.CHECK BALANCE");
			System.out.println(" 3. DEPOSIT ");
			System.out.println(" 4.WITHDRAW");
			System.out.println(" 5.MONEY TRANSFER");
			System.out.println(" 6.TO PRINT TRANSACTION");
			System.out.println(" 7.EXIT");
			System.out.println(" enter your choice");
			choice = scan.nextInt();

			if (choice == 1) {
				System.out.println("Enter the account id,name and amount  for the new customer to be registered");
				int id = scan.nextInt();
				scan.nextLine();
				String name = scan.nextLine();

				float balance = scan.nextFloat();
				bank.addNewCustomer(new customer(id, name, balance));
			} else if (choice == 2) {
				System.out.println("Enter the account id,name to check the balance");
				int id = scan.nextInt();
				scan.nextLine();
				String name = scan.nextLine();
				float bal;
				bal = bank.checkbalance(id, name);
				System.out.println("balance = " + bal);
			} else if (choice == 3) {
				System.out.println("Enter the account id and amount to deposit");
				int id = scan.nextInt();
				float amount = scan.nextFloat();

				bank.deposit(id, amount);
			} else if (choice == 4) {
				System.out.println("Enter the account id and amount to withdraw");
				
				int id = scan.nextInt();
				float amount = scan.nextInt();
				bank.withdraw(id, amount);
			} else if (choice == 5) {
				System.out.println("Enter the sender's account id  ");
				int sid = scan.nextInt();
				System.out.println("Enter the Receiver's account id  ");
				int rid = scan.nextInt();
				System.out.println("Enter the amount to transfer ");
				float amount = scan.nextFloat();
				bank.transfer(sid, rid, amount);
			} else if (choice == 6) {
				System.out.println("Enter the account  id to check the transaction history");
				int id = scan.nextInt();
				bank.transactionview(id);
				

			} else if (choice == 7) {
				System.out.println("bye");
			} else
				System.out.println("invalid choice");
		} while (choice != 7);
	}

}
