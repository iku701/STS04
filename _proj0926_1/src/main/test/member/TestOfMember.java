package main.test.member;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import config.AppCtx;
import dao.MemberDao;
import spring.Member;

import java.util.List;

public class TestOfMember {

    public static void main(String[] args) {
    	
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppCtx.class);
        MemberDao memberDao = ctx.getBean(MemberDao.class);

        System.out.println("초기 멤버 목록");
        printMembers(memberDao.selectAllMember());

        System.out.println("멤버 추가: 강호동");
        memberDao.insertMember("강호동", "부산 해운대구", "나길", "601");
        printMembers(memberDao.selectAllMember());

        System.out.println("특정 멤버 조회: 홍길동");
        printMembers(memberDao.selectOneMember("홍길동"));

        System.out.println("멤버 업데이트: 홍길동 -> 김길동");
        memberDao.updateMember("강호동", "김호동", "서울 강남구", "다길", "501");
        printMembers(memberDao.selectAllMember());

        ctx.close();
    }

    private static void printMembers(List<Member> members) {
        if (members.isEmpty()) {
            System.out.println("멤버가 없습니다.");
        } else {
            for (Member member : members) {
                System.out.println(member);
            }
        }
    }
}

