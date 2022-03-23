package com.ibm.springboot;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public class studentRepo {
	
	@Autowired
	JdbcTemplate template;
	
	
	int getCountOfLearners() {
		return template.queryForObject("select count(*) from students", Integer.class);
	}
    
	
	List<student> getallstudents()
	{
		return template.query("select * from students ", new studentMapper());	
	}
	
	void addnewstudent( student student1) 
	{
	  template.update("insert into students values (?, ?, ?)", new Object[] {student1.id(),student1.name(),student1.derpartment() });
	}
	
	void updatestudent( student student1,int id )
	{
		template.update("update students set id=?,name= ?,derpartment= ? where id=? ", new Object[] {student1.id(), student1.name(), student1.derpartment(),id });
	}
	
	void deletestudentsbyid(int id)
	{
		template.update("delete from students where id =?",new Object[] {id});
	}
	
	student getstudentsbyid(int id)
	{
		return template.queryForObject("select * from students where id=? ",new Object[] {id}, new studentMapper());	
	}
	
	
	
	class studentMapper implements RowMapper<student> {                                                                  // ROWMAPPER CLASS

		@Override
		public student mapRow(ResultSet rs, int rowNum) throws SQLException {

			student thestudent = new student(rs.getInt("id"), rs.getString("name"), rs.getString("derpartment"));

			return thestudent;
		}

	}

}
