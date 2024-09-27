package main.test.orders;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import config.AppCtx;
import dao.OrdersDao;
import spring.OrderItem;
import spring.Orders;

import java.util.List;

public class TestOrderInsert {
	public static void main(String[] args) {
	    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppCtx.class);
	    OrdersDao ordersDao = ctx.getBean(OrdersDao.class);

	    Orders newOrder = new Orders(1L, "서울시 강남구", "테헤란로", "06236");

	    OrderItem orderItem1 = new OrderItem(1L, null, 3000, 2);

	    newOrder.addOrderItem(orderItem1);

	    Long orderId = ordersDao.insertOrder(newOrder);
	    System.out.println("새로운 주문이 추가되었습니다. 주문 ID: " + orderId);

	    List<Orders> orderList = ordersDao.selectAllOrderDetails();
	    for (Orders order : orderList) {
	        System.out.println(order);
	    }

	    ctx.close();
	}

}

