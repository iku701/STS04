package main.test.orders;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import config.AppCtx;
import dao.OrdersDao;
import spring.OrderItem;
import spring.Orders;

public class TestOrderDelete {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppCtx.class);
		OrdersDao ordersDao = ctx.getBean(OrdersDao.class);

		Orders newOrder = new Orders(1L, "경기도", "가나길", "03231");
		OrderItem orderItem1 = new OrderItem(1L, null, 3000, 2);
		newOrder.addOrderItem(orderItem1);
		Long orderId = ordersDao.insertOrder(newOrder);
		System.out.println("새로운 주문이 추가되었습니다. 주문 ID: " + orderId);

		List<Orders> orderList = ordersDao.selectAllOrderDetails();
		for (Orders order : orderList) {
			System.out.println(order);
		}

		ordersDao.deleteOrder(orderId);
		System.out.println("주문 ID " + orderId + "이 삭제되었습니다.");

		orderList = ordersDao.selectAllOrderDetails();
		for (Orders order : orderList) {
			System.out.println(order);
		}

		ctx.close();
	}

}
