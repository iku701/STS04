package dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import spring.OrderItem;
import spring.Orders;

public class OrdersDao {
    private JdbcTemplate jdbcTemplate;

    public OrdersDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Orders> selectAllOrderDetails() {
        String sql = "SELECT o.id AS order_id, o.member_id, o.city, o.street, o.zipcode, o.order_date, "
                   + "oi.id AS order_item_id, oi.item_id, oi.total_price, oi.count "
                   + "FROM orders o "
                   + "JOIN order_item oi ON o.id = oi.order_id";

        OrdersWithOrderItemRowMapper rowMapper = new OrdersWithOrderItemRowMapper();
        jdbcTemplate.query(sql, rowMapper);
        return rowMapper.getOrders(); 
    }

    public Long insertOrder(Orders order) {
        String orderSql = "INSERT INTO orders (member_id, city, street, zipcode, order_date) VALUES (?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, order.getMemberId());
            ps.setString(2, order.getCity());
            ps.setString(3, order.getStreet());
            ps.setString(4, order.getZipcode());
            ps.setTimestamp(5, Timestamp.valueOf(order.getOrderDate()));
            return ps;
        }, keyHolder);

        Long orderId = keyHolder.getKey().longValue();
        order.setId(orderId);

        String orderItemSql = "INSERT INTO order_item (item_id, order_id, total_price, count) VALUES (?, ?, ?, ?)";

        for (OrderItem orderItem : order.getOrderItems()) {
            jdbcTemplate.update(orderItemSql, orderItem.getItemId(), orderId, orderItem.getTotalPrice(), orderItem.getCount());
        }

        return orderId;
    }

    public void deleteOrder(Long orderId) {
        String deleteOrderItemSql = "DELETE FROM order_item WHERE order_id = ?";
        jdbcTemplate.update(deleteOrderItemSql, orderId);

        String deleteOrderSql = "DELETE FROM orders WHERE id = ?";
        jdbcTemplate.update(deleteOrderSql, orderId);

        System.out.println("주문 ID " + orderId + " 및 관련 주문 항목이 삭제되었습니다.");
    }

    private RowMapper<OrderItem> orderItemRowMapper = (rs, rowNum) -> {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(rs.getLong("id"));
        orderItem.setItemId(rs.getLong("item_id"));
        orderItem.setOrderId(rs.getLong("order_id"));
        orderItem.setTotalPrice(rs.getInt("total_price"));
        orderItem.setCount(rs.getInt("count"));
        return orderItem;
    };

    private static class OrdersWithOrderItemRowMapper implements RowMapper<Orders> {
        private Map<Long, Orders> orderMap = new HashMap<>();

        @Override
        public Orders mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
            Long orderId = rs.getLong("order_id");

            Orders order = orderMap.get(orderId);
            if (order == null) {
                order = new Orders();
                order.setId(orderId);
                order.setMemberId(rs.getLong("member_id"));
                order.setCity(rs.getString("city"));
                order.setStreet(rs.getString("street"));
                order.setZipcode(rs.getString("zipcode"));
                order.setOrderDate(rs.getTimestamp("order_date").toLocalDateTime());

                orderMap.put(orderId, order); 
            }

            Long orderItemId = rs.getLong("order_item_id");
            if (orderItemId != 0) { 
                OrderItem orderItem = new OrderItem();
                orderItem.setId(orderItemId);
                orderItem.setItemId(rs.getLong("item_id"));
                orderItem.setOrderId(orderId);
                orderItem.setTotalPrice(rs.getInt("total_price"));
                orderItem.setCount(rs.getInt("count"));

                order.addOrderItem(orderItem);
            }

            return order;
        }

        public List<Orders> getOrders() {
            return List.copyOf(orderMap.values());
        }
    }
}
