package spring;

import java.time.LocalDateTime;

public class Orders {
    private Long id;
    private Long memberId;
    private Long orderItemId;
    private String city;
    private String street;
    private String zipcode;
    private LocalDateTime orderDate;

    public Orders() {}

    public Orders(Long memberId, Long orderItemId, String city, String street, String zipcode) {
        this.memberId = memberId;
        this.orderItemId = orderItemId;
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
        this.orderDate = LocalDateTime.now();  
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

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
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
                ", orderItemId=" + orderItemId +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", orderDate=" + orderDate +
                '}';
    }
}

