package dao;

import java.util.List;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import config.AppCtx;
import spring.Member;

public class MemberDao {
    private JdbcTemplate jdbcTemplate;

    public MemberDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<Member> memberRowMapper = (rs, rowNum) -> {
        Member member = new Member();
        member.setId(rs.getLong("id"));
        member.setName(rs.getString("name"));
        member.setCity(rs.getString("city"));
        member.setStreet(rs.getString("street"));
        member.setZipcode(rs.getString("zipcode"));
        return member;
    };

    public List<Member> selectAllMember() {
        String sql = "SELECT * FROM member";
        return jdbcTemplate.query(sql, memberRowMapper);
    }
    
    public List<Member> selectOneMember(String name) {
    	String sql = "SELECT * FROM member where name = ?";
        return jdbcTemplate.query(sql, memberRowMapper, name);
    }

    public void insertMember(String name, String city, String street, String zipcode) {
        String sql = "INSERT INTO member (name, city, street, zipcode) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, name, city, street, zipcode);
    }
    
    public void updateMember(String oldName, String newName, String city, String street, String zipcode) {
        String sql = "UPDATE member SET name = ?, city = ?, street = ?, zipcode = ? WHERE name = ?";
        jdbcTemplate.update(sql, newName, city, street, zipcode, oldName);
    }

}

