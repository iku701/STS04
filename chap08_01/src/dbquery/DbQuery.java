package dbquery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import spring.Member;

public class DbQuery {
	private JdbcTemplate jdbcTemplate;
	
	public DbQuery (DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public Dept selectByDeptno(int deptno) {
		List<Dept> results = jdbcTemplate.query(
				"select * from dept where deptno = ?",
				new RowMapper<Dept>() {
					@Override
					public Dept mapRow(ResultSet rs, int rowNum) throws SQLException {
						Dept dept = new Dept(
								rs.getInt("DEPTNO"),
								rs.getString("DNAME"),
								rs.getString("LOC"));
						dept.setDeptno(rs.getInt("DEPTNO"));
						return dept;
					}
				},
				deptno); 
				
			return results.isEmpty() ? null : results.get(0);
	}
}
