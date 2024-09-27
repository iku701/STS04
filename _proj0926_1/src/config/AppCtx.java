package config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dao.ItemDao;
import dao.MemberDao;
import dao.OrderItemDao;
import dao.OrdersDao;

@Configuration
public class AppCtx {
	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		DataSource ds = new DataSource();
		ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3307/spring5db2?characterEncoding=utf8");
		ds.setUsername("root");
		ds.setPassword("mysql");
		ds.setInitialSize(2);
		ds.setMaxActive(10);
		ds.setMaxIdle(8);  
		ds.setMinIdle(2);
		return ds;
	}
	
	@Bean
	public MemberDao memberDao() {
		return new MemberDao(dataSource());
	}
	
	@Bean
	public ItemDao itemDao() {
		return new ItemDao(dataSource());
	}
	
	@Bean
	public OrdersDao ordersDao() {
		return new OrdersDao(dataSource());
	}
}
