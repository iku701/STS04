package main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import config.DbConfig;
import dbquery.DbQuery;

public class MainUsingDbQuery {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx 
		     = new AnnotationConfigApplicationContext(DbConfig.class);

		DbQuery dbQuery = ctx.getBean(DbQuery.class);
		System.out.println(dbQuery.selectByDeptno(20));
		ctx.close();
	}
}
