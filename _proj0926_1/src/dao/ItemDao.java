package dao;

import java.util.List;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import spring.Item;

public class ItemDao {
    private JdbcTemplate jdbcTemplate;

    public ItemDao(DataSource dataSource) {
    	this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    private RowMapper<Item> itemRowMapper = (rs, rowNum) -> {
        Item item = new Item();
        item.setId(rs.getLong("id"));
        item.setName(rs.getString("name"));
        item.setPrice(rs.getInt("price"));
        item.setStockQuantity(rs.getInt("stockquantity"));
        return item;
    };
    
    public List<Item> selectAllItem() {
        String sql = "SELECT * FROM item";
        return jdbcTemplate.query(sql, itemRowMapper);
    }
    
    public List<Item> selectOneItem(String name) {
    	String sql = "SELECT * FROM item where name = ?";
    	return jdbcTemplate.query(sql, itemRowMapper, name);
    }
    
    public void insertItem(String name, int price, int stockQuantity) {
        String sql = "INSERT INTO item (name, price, stockquantity) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, name, price, stockQuantity);
    }
    
    public void deleteItem(String name) {
        String sql = "DELETE FROM item WHERE name = ?";
        jdbcTemplate.update(sql, name);
    }
    
    public void updataItem(String name, int price, int stockQuantity) {
    	String sql = "UPDATE item SET price = ?, stockquantity = ? where name = ?";
    	jdbcTemplate.update(sql, price, stockQuantity, name);
    }
    

}
