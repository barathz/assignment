package com.ibm.bank;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("dao")
public class jdbc_pring {

	JdbcTemplate jdbcTemplate;

	DataSource dataSource;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

//	For getching the count of learners
	int getCountOfLearners() {
		String qry = "select count(*) from students";

		return jdbcTemplate.queryForObject(qry, Integer.class);
	}
	String getLearnerNameById(int learnerId) {

		return jdbcTemplate.queryForObject("select students from learners where id = ?", String.class, new Object[] {learnerId} );
		
	}
	
//	Get learner name by id and domain
	String getLearnerNameByIdAndDomain(int id, String department) {
		
		return jdbcTemplate.queryForObject(
				"select name from students where id = ? and derpartment = ?",
				String.class, 
				new Object[] {id, department});
	}
	
//	Get all learner details by id
	Learner getAllLearnerDetailsById(int id) {
		
		return jdbcTemplate.queryForObject(
				"select * from students where id = ?", new LearnerMapper() , new Object[] {id});
				
	}
	
	
	class LearnerMapper implements RowMapper<Learner>{

		@Override
		public Learner mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			Learner theLearner = new Learner(
					rs.getInt("id"),
					rs.getString("name"),
					rs.getString("derpartment")
					);
			
			return theLearner;
		}
		
	}

}
