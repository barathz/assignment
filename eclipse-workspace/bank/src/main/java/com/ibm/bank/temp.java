package com.ibm.bank;

import java.sql.SQLException;
import java.util.Scanner;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class temp {
	public static void main(String[] args) throws SQLException {
//        Load the context
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("config.xml");

		jdbc_pring dao = context.getBean("dao", jdbc_pring.class);

		System.out.println("Students already registered : " + dao.getCountOfLearners());

    	Scanner scan = new Scanner(System.in);
    	
    	System.out.println("Enter id");
    	
    	int id = scan.nextInt();
    	scan.nextLine();
    	
    	Learner learner = dao.getAllLearnerDetailsById(id);
    	
    	System.out.println(learner);
    	
//    	String domain = scan.nextLine();
//    	
//    	System.out.println("Learner name  for given id and domain : " + dao.getLearnerNameByIdAndDomain(id, domain));
    	
    	
    	
    	
//    	Get a reference to the bean
//    	JdbcDao dao = context.getBean("dao", JdbcDao.class);
    	
    	
//    	Call the method
//    	dao.connectToDb();
//    	
//    	System.out.println("Enter id to search");
//    	
//    	int id = new Scanner(System.in).nextInt();
//    	
//    	dao.getNameById(id);
    }
}

