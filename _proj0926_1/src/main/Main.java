package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import config.AppCtx;
import dao.ItemDao;
import dao.MemberDao;
import dao.OrdersDao;
import spring.Item;
import spring.Member;
import spring.OrderItem;
import spring.Orders;

public class Main {

    private static AnnotationConfigApplicationContext ctx = null;
    private static MemberDao memberDao = null;
    private static ItemDao itemDao = null;
    private static OrdersDao ordersDao = null;

    public static void main(String[] args) throws IOException {
        ctx = new AnnotationConfigApplicationContext(AppCtx.class);
        memberDao = ctx.getBean(MemberDao.class);
        itemDao = ctx.getBean(ItemDao.class);
        ordersDao = ctx.getBean(OrdersDao.class);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        printHelp();

        while (true) {
            System.out.println("명령어(번호)를 입력하세요:");
            System.out.println("명령어 확인(-)");
            String command = reader.readLine();
            if (command.equals("0")) {
                System.out.println("종료합니다.");
                break;
            } else if (command.equals("1")) {
                memberListCommand(memberDao.selectAllMember());
            } else if (command.equals("2")) {
            	memberListCommand(memberDao.selectAllMember());
                memberOneCommand(reader);
            } else if (command.equals("3")) {
                newMemberCommand(reader);
            } else if (command.equals("4")) {
                memberListCommand(memberDao.selectAllMember());
                updateMemberCommand(reader);
            } else if (command.equals("5")) {
                itemListCommand(itemDao.selectAllItem());
            } else if (command.equals("6")) {
                newItemCommand(reader);
            } else if (command.equals("7")) {
                itemListCommand(itemDao.selectAllItem());
                updateItemCommand(reader);
            } else if (command.equals("8")) {
                itemListCommand(itemDao.selectAllItem());
                deleteItemCommand(reader);
            } else if (command.equals("9")) {
                orderListCommand(ordersDao.selectAllOrderDetails());
            } else if (command.equals("10")) {
                newOrderCommand(reader);
            } else if (command.equals("11")) {
                orderListCommand(ordersDao.selectAllOrderDetails());
                deleteOrderCommand(reader);
            } else if (command.equals("-")) {
            	printHelp();
            } else {
                printHelp();
            }

        }

        ctx.close();
    }

    private static void memberListCommand(List<Member> members) {
        if (members.isEmpty()) {
            System.out.println("멤버가 없습니다.");
        } else {
            for (Member member : members) {
                System.out.println(member);
            }
        }
    }

    private static void memberOneCommand(BufferedReader reader) throws IOException {
        System.out.println("조회할 멤버의 이름을 입력하세요:");
        String name = reader.readLine();

        memberDao.selectOneMember(name).forEach(member -> {
            System.out.println(member + "\n");
        });
    }

    private static void newMemberCommand(BufferedReader reader) throws IOException {
        System.out.println("이름을 입력하세요:");
        String name = reader.readLine();

        System.out.println("거주지(시/구)를 입력하세요: (예: 서울시 동대문구)");
        String city = reader.readLine();

        System.out.println("도로명주소(로)를 입력하세요: (예: 길음로)");
        String street = reader.readLine();

        System.out.println("우편번호를 입력하세요:");
        String zipcode = reader.readLine();

        memberDao.insertMember(name, city, street, zipcode);
        System.out.println("새로운 멤버를 추가하였습니다.");
    }

    private static void updateMemberCommand(BufferedReader reader) throws IOException {
        System.out.println("수정할 멤버의 이름을 입력하세요:");
        String oldName = reader.readLine();

        System.out.println("새로운 이름을 입력하세요:");
        String newName = reader.readLine();

        System.out.println("새로운 거주지(시/구)를 입력하세요: (예: 서울시 동대문구)");
        String city = reader.readLine();

        System.out.println("새로운 도로명주소(로)를 입력하세요: (예: 길음로)");
        String street = reader.readLine();

        System.out.println("새로운 우편번호를 입력하세요:");
        String zipcode = reader.readLine();

        memberDao.updateMember(oldName, newName, city, street, zipcode);
        System.out.println("멤버 정보가 업데이트되었습니다.");
    }

    private static void itemListCommand(List<Item> items) {
        if (items.isEmpty()) {
            System.out.println("아이템이 없습니다.");
        } else {
            for (Item item : items) {
                System.out.println(item);
            }
        }
    }

    private static void newItemCommand(BufferedReader reader) throws IOException {
        System.out.println("상품이름을 입력하세요:");
        String name = reader.readLine();

        List<Item> existingItems = itemDao.selectOneItem(name);

        if (!existingItems.isEmpty()) {
            System.out.println("이미 존재하는 상품입니다. 상품 정보:");
            existingItems.forEach(item -> System.out.println(item));
            return;
        }

        System.out.println("가격을 입력하세요: (예: 1000)");
        int price = Integer.parseInt(reader.readLine());

        System.out.println("수량을 입력하세요: (예: 200)");
        int stockQuantity = Integer.parseInt(reader.readLine());

        itemDao.insertItem(name, price, stockQuantity);
        System.out.println("상품을 추가하였습니다.");
    }

    private static void updateItemCommand(BufferedReader reader) throws IOException {
        System.out.println("수정할 상품의 이름을 입력하세요:");
        String name = reader.readLine();

        List<Item> existingItems = itemDao.selectOneItem(name);

        if (existingItems.isEmpty()) {
            System.out.println("해당 이름의 상품이 존재하지 않습니다.");
            return;
        }

        System.out.println("새로운 가격을 입력하세요: (예: 1500)");
        int price = Integer.parseInt(reader.readLine());

        System.out.println("새로운 수량을 입력하세요: (예: 100)");
        int stockQuantity = Integer.parseInt(reader.readLine());

        itemDao.updataItem(name, price, stockQuantity);
        System.out.println("상품 정보가 업데이트되었습니다.");
    }

    private static void deleteItemCommand(BufferedReader reader) throws IOException {
        System.out.println("삭제할 상품의 이름을 입력하세요:");
        String name = reader.readLine();

        List<Item> existingItems = itemDao.selectOneItem(name);

        if (existingItems.isEmpty()) {
            System.out.println("해당 이름의 상품이 존재하지 않습니다.");
            return;
        }

        itemDao.deleteItem(name);
        System.out.println("해당 상품이 삭제되었습니다.");
    }

    private static void orderListCommand(List<Orders> orders) {
        if (orders.isEmpty()) {
            System.out.println("주문이 없습니다.");
        } else {
            for (Orders order : orders) {
                System.out.println(order);
            }
        }
    }

    private static void newOrderCommand(BufferedReader reader) throws IOException {
        memberListCommand(memberDao.selectAllMember());
        System.out.println("주문을 진행할 멤버 ID를 입력하세요:");
        Long memberId = Long.parseLong(reader.readLine());

        System.out.println("배송지 도시를 입력하세요: (예: 서울시 성북구)");
        String city = reader.readLine();

        System.out.println("도로명 주소를 입력하세요: (예: 길음로)");
        String street = reader.readLine();

        System.out.println("우편번호를 입력하세요:");
        String zipcode = reader.readLine();

        Orders newOrder = new Orders(memberId, city, street, zipcode);
        
        itemListCommand(itemDao.selectAllItem());
        System.out.println("추가할 상품의 이름과 수량을 입력하세요: (예:연필 3)");
        System.out.println("상품 추가를 종료하려면 'end'를 입력하세요.");

        while (true) {
            System.out.println("상품 이름과 수량을 입력하세요:");
            String input = reader.readLine();
            if (input.equalsIgnoreCase("end")) break;

            String[] parts = input.split(" ");
            if (parts.length != 2) {
                System.out.println("잘못된 입력입니다. 상품 이름과 수량을 정확히 입력하세요. (예:연필 3)");
                continue;
            }

            String itemName = parts[0];
            int count = Integer.parseInt(parts[1]);

            List<Item> items = itemDao.selectOneItem(itemName);
            if (items.isEmpty()) {
                System.out.println("해당 이름의 상품이 존재하지 않습니다. 다시 입력하세요.");
                continue;
            }

            Item item = items.get(0);
            int totalPrice = item.getPrice() * count;

            OrderItem orderItem = new OrderItem(item.getId(), null, totalPrice, count);
            newOrder.addOrderItem(orderItem);
        }

        Long orderId = ordersDao.insertOrder(newOrder);
        System.out.println("주문이 성공적으로 추가되었습니다. 주문 ID: " + orderId);
    }


    private static void deleteOrderCommand(BufferedReader reader) throws IOException {
        System.out.println("삭제할 주문의 ID를 입력하세요:");
        Long orderId = Long.parseLong(reader.readLine());

        ordersDao.deleteOrder(orderId);
        System.out.println("주문이 삭제되었습니다.");
    }

    private static void printHelp() {
        System.out.println("----------------------");
        System.out.println(" ======= 명령어 리스트 ======= ");
        System.out.println("0.exit");
        System.out.println("1.member list (모든 멤버 조회)");
        System.out.println("2.search member (특정 멤버 조회)");
        System.out.println("3.new member (새로운 멤버 추가)");
        System.out.println("4.update member (멤버 정보 수정)");
        System.out.println("5.item list (모든 재고 조회)");
        System.out.println("6.new item (새로운 재고 추가)");
        System.out.println("7.update item (재고 정보 수정)");
        System.out.println("8.delete item (재고 삭제)");
        System.out.println("9.order list (모든 주문 조회)");
        System.out.println("10.new order (새로운 주문 추가)");
        System.out.println("11.delete order (주문 삭제)");
        System.out.println("----------------------");
    }
}
