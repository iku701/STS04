package spring;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Orders {
    private Long id;  
    private Long memberId; 
    private String city; 
    private String street; 
    private String zipcode;  
    private LocalDateTime orderDate; 
    private List<OrderItem> orderItems = new ArrayList<>();  

    public Orders() {}

    public Orders(Long id, Long memberId, String city, String street, String zipcode, LocalDateTime orderDate) {
        this.id = id;
        this.memberId = memberId;
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
        this.orderDate = orderDate != null ? orderDate : LocalDateTime.now();
    }

    public Orders(Long memberId, String city, String street, String zipcode) {
        this.memberId = memberId;
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
        this.orderDate = LocalDateTime.now();  
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
    }

    public int getTotalPrice() {
        return orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)  
                .sum();
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", orderDate=" + orderDate +
                ", totalPrice=" + getTotalPrice() + 
                ", orderItems=" + orderItems +      
                '}';
    }
}
