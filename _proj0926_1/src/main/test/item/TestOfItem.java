package main.test.item;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import config.AppCtx;
import dao.ItemDao;
import spring.Item;

import java.util.List;

public class TestOfItem {
    public static void main(String[] args) {
    	
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppCtx.class);
        ItemDao itemDao = ctx.getBean(ItemDao.class);

        System.out.println("초기 아이템 목록");
        printItems(itemDao.selectAllItem());

        System.out.println("아이템 추가: 색연필");
        itemDao.insertItem("색연필", 500, 200);
        printItems(itemDao.selectAllItem());

        System.out.println("아이템 업데이트: 색연필의 가격과 재고 수정");
        itemDao.updataItem("색연필", 400, 150);
        printItems(itemDao.selectAllItem());

        System.out.println("아이템 삭제: 색연필");
        itemDao.deleteItem("색연필");
        printItems(itemDao.selectAllItem());

        ctx.close();
    }

    private static void printItems(List<Item> items) {
        if (items.isEmpty()) {
            System.out.println("아이템이 없습니다.");
        } else {
            for (Item item : items) {
                System.out.println(item);
            }
        }
    }
}

