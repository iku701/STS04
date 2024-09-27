package spring;

public class OrderItem {
    private Long id;  
    private Long itemId; 
    private Long orderId;  
    private int totalPrice;  
    private int count;  

    public OrderItem() {}

    public OrderItem(Long itemId, Long orderId, int totalPrice, int count) {
        this.itemId = itemId;
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", itemId=" + itemId +
                ", orderId=" + orderId +
                ", totalPrice=" + totalPrice +
                ", count=" + count +
                '}';
    }
}
